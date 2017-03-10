/**
 * 
 */
package bookmarks;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Justin.Shi
 *
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	
	Collection<Bookmark> findByAccountUsername(String username);

}
