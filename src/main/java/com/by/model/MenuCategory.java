package com.by.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yagamai on 15-12-22.
 */
@Entity
@Table(name="by_menu_category")
public class MenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Menu> menus;

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

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuCategory that = (MenuCategory) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
