package com.by.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "by_rule_category")
public class RuleCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "ruleCategory", fetch = FetchType.LAZY)
    private List<CardRule> rules;

    public RuleCategory() {
    }

    public RuleCategory(int id) {
        this.id = id;
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

    public List<CardRule> getRules() {
        return rules;
    }

    public void setRules(List<CardRule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleCategory that = (RuleCategory) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
