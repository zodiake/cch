package com.by.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by yagamai on 15-12-18.
 */
@Entity
@DiscriminatorValue(value = "r")
public class MemberRuleHelper extends MemberHelp {
}
