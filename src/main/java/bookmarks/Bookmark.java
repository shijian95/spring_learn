package bookmarks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Bookmark {

	@JsonIgnore
	@ManyToOne
	private Account account;

	@Id
	@GeneratedValue
	private Long id;

	public String uri;
	public String description;

	Bookmark() {
	}

	public Bookmark(Account account, String uri, String description) {
		super();
		this.account = account;
		this.uri = uri;
		this.description = description;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Bookmark [account=" + account + ", id=" + id + ", uri=" + uri + ", description=" + description + "]";
	}

}
