package bookmarks;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.security.Principal;
@RestController
@RequestMapping("/bookmarks")
public class BookmarkRestController {
	
	private final BookmarkRepository bookmarkRepository;
	private final AccountRepository accountRepository;

	@Autowired
	BookmarkRestController(BookmarkRepository bookmarkRepository,
						   AccountRepository accountRepository) {
		this.bookmarkRepository = bookmarkRepository;
		this.accountRepository = accountRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	Resources<BookmarkResource> readBookmarks(Principal principal) {
		this.validateUser(principal);
		List<BookmarkResource> bookmarkResourceList = bookmarkRepository
				.findByAccountUsername(principal.getName()).stream().map(BookmarkResource::new)
				.collect(Collectors.toList());
		return new Resources<>(bookmarkResourceList);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(Principal principal, @RequestBody Bookmark input) {
		this.validateUser(principal);

		Optional<Account> op = this.accountRepository.findByUsername(principal.getName());
		if (op.isPresent()) {
			Account ac = op.get();
			System.out.println(ac.toString());
			Bookmark result = bookmarkRepository.save(new Bookmark(ac,
					input.uri, input.description));

			URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri();
			return ResponseEntity.created(location).build();
		} else {
			System.out.println("nothing");
			return ResponseEntity.noContent().build();
		}
/*		return this.accountRepository
				.findByUsername(userId)
				.map(account -> {
					Bookmark result = bookmarkRepository.save(new Bookmark(account,
							input.uri, input.description));

					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getId()).toUri();
					
					return ResponseEntity.created(location).build();
				})
				.orElse(ResponseEntity.noContent().build());*/

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
	BookmarkResource  readBookmark(Principal principal, @PathVariable Long bookmarkId) {
		this.validateUser(principal);
		Bookmark bookmark = bookmarkRepository.findOne(bookmarkId);
		if (bookmark == null) {
			return new BookmarkResource();
		}
		return new BookmarkResource(bookmarkRepository.findOne(bookmarkId));
	}

	private void validateUser(Principal principal) {
		String userId = principal.getName();
		this.accountRepository
			.findByUsername(userId)
			.orElseThrow(
				() -> new UserNotFoundException(userId));
	}
}
