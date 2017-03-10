package bookmarks;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {

	@OneToMany(mappedBy = "account")
	private Set<Bookmark> bookmarks = new HashSet<>();
	
	@Id
	@GeneratedValue
	private Long id;

    @JsonIgnore
    public String password;
    public String username;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(Set<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account(String username, String password) {
		this.password = password;
		this.username = username;
	}

	//JPA only
	Account() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Account [ id=" + id + ", password=" + password + ", username=" + username
				+ "]";
	}
	
	
	
}
