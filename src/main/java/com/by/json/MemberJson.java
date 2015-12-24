package com.by.json;

import com.by.model.Member;
import com.by.typeEnum.ValidEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;

/**
 * Created by yagamai on 15-12-7.
 */
public class MemberJson {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private Long id;
    @NotNull
    @Length(min = 11, max = 11)
    private String mobile;
    @NotNull
    @Length(min = 4, max = 10)
    private String password;
    private int score;
    private ValidEnum valid;
    private String cardName;
    private String createdTime;

    public MemberJson() {
    }

    public MemberJson(Member member) {
    	this.id=member.getId();
        this.mobile = member.getName();
        this.score = member.getScore();
        this.score = member.getScore();
        this.valid = member.getValid();
        this.cardName = member.getCard().getName();
        this.createdTime = format.format(member.getCreatedTime().getTimeInMillis());
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ValidEnum getValid() {
        return valid;
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}
}
