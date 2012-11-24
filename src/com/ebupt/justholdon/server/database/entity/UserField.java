package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userField")
public class UserField {

	public UserField() {
	};
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userId")
	private Long id;
	private String userName;
	private String sex;
	private Date birthday;
	private String email;
	private String cellphone;
	private String area;
	private String description;
	private int money;
	private int userLevel;
	private String sinaKey;
	private String weiboKey;
	private String renrenKey;
	@Column(name = "extends")
	private String extendsInfo;
	private Date createTime = new Date();
	private Date modifyTime ;
	private String avatar;
	private String password;
	private BindType socialBind = BindType.WEIBO;
	private String deviceToken;


	public String getDeviceToken() {
		return deviceToken;
	}

	public UserField setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
		return this;
	}

	public Long getId() {
		return id;
	}

	public UserField setId(Long id) {
		this.id = id;
		return this;
	}

	public String getUserName() {
		return userName;
	}


	public UserField setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public UserField setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public Date getBirthday() {
		return birthday;
	}

	public UserField setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserField setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getCellphone() {
		return cellphone;
	}

	public UserField setCellphone(String cellphone) {
		this.cellphone = cellphone;
		return this;
	}

	public String getArea() {
		return area;
	}

	public UserField setArea(String area) {
		this.area = area;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public UserField setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getMoney() {
		return money;
	}

	public UserField setMoney(int money) {
		this.money = money;
		return this;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public UserField setUserLevel(int userLevel) {
		this.userLevel = userLevel;
		return this;
	}

	public String getSinaKey() {
		return sinaKey;
	}

	public UserField setSinaKey(String sinaKey) {
		this.sinaKey = sinaKey;
		return this;
	}

	public String getWeiboKey() {
		return weiboKey;
	}

	public UserField setWeiboKey(String weiboKey) {
		this.weiboKey = weiboKey;
		return this;
	}

	public String getRenrenKey() {
		return renrenKey;
	}

	public UserField setRenrenKey(String renrenKey) {
		this.renrenKey = renrenKey;
		return this;
	}

	public String getExtendsInfo() {
		return extendsInfo;
	}

	public UserField setExtendsInfo(String extendsInfo) {
		this.extendsInfo = extendsInfo;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public UserField setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public UserField setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
		return this;
	}

	public String getAvatar() {
		return avatar;
	}

	public UserField setAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserField setPassword(String password) {
		this.password = password;
		return this;
	}

	public BindType getSocialBind() {
		return socialBind;
	}

	public UserField setSocialBind(BindType socialBind) {
		this.socialBind = socialBind;
		return this;
	}
	
}
