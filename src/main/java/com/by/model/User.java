package com.by.model;

import java.util.Set;

import javax.persistence.*;

import com.by.typeEnum.ValidEnum;

@Table(name = "by_user")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String password;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "by_user_auth", joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "auth_id") )
	private Set<Authority> authorites;

	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private Shop shop;

	private ValidEnum valid;

	public User() {
	}

	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Authority> getAuthorites() {
		return authorites;
	}

	public void setAuthorites(Set<Authority> authorites) {
		this.authorites = authorites;
	}
	
	public ValidEnum getValid() {
		return valid;
	}

	public void setValid(ValidEnum valid) {
		this.valid = valid;
	}

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
