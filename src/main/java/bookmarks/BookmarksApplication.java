package bookmarks;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookmarksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmarksApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(AccountRepository accountRepository,
			BookmarkRepository bookmarkRepository) {
		System.out.println("CommandLineRunner!!");
		return (evt) -> Arrays.asList(
				"jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
				.forEach(
						a -> {
							Account account = accountRepository.save(new Account(a,
									"password"));
							System.out.println("account:" + account.toString());
							Bookmark bookmark1 = bookmarkRepository.save(new Bookmark(account,
									"http://bookmark.com/1/" + a, "A description"));
							Bookmark bookmark2 = bookmarkRepository.save(new Bookmark(account,
									"http://bookmark.com/2/" + a, "A description"));
							System.out.println("account:" + bookmark1.toString());
						});
	}
}
