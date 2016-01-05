package com.by.model;

import java.util.Set;

import javax.persistence.*;

import com.by.typeEnum.ValidEnum;

@Table(name = "by_user")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String password;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "by_user_auth", joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "auth_id") )
	private Set<Authority> userAuthorities;

	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private Shop shop;

	private ValidEnum valid;
	
	@Column(name="user_authority")
	private String userAuthority;

	public User() {
	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Set<Authority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<Authority> userAuthorities) {
		this.userAuthorities = userAuthorities;
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
    
	public String getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(String userAuthority) {
		this.userAuthority = userAuthority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		return id == user.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
