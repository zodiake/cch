package com.by.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "by_rule_category")
public class RuleCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "ruleCategory", fetch = FetchType.LAZY)
    private List<CardRule> rules;

    public RuleCategory() {
    }

    public RuleCategory(Long id) {
        this.id = id;
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

    public List<CardRule> getRules() {
        return rules;
    }

    public void setRules(List<CardRule> rules) {
        this.rules = rules;
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
        RuleCategory other = (RuleCategory) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
