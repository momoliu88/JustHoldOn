package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "userField")
public class UserField implements BaseEntity<Long>{

	public UserField() {
	};
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userId")
	private Long id;
	private String userName = null;
	private String sex = "";
	private Date birthday = null;
	private String email = "";
	private String cellphone ="";
	private String area ="" ;
	private String description = "";
	private Integer money = Integer.valueOf(0);
	private Integer userLevel = Integer.valueOf(0);
	private String sinaKey = "";
	private String weiboKey = "";
	private String renrenKey = "";
	@Column(name = "extends")
	private String extendsInfo ="";
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	private String avatar ="";
	private String password = null;
	private BindType socialBind = BindType.WEIBO;
	private String deviceToken = "";


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

	public Integer getMoney() {
		return money;
	}

	public UserField setMoney(Integer money) {
		this.money = money;
		return this;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public UserField setUserLevel(Integer userLevel) {
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
	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!((null != obj) && (obj.getClass() == this.getClass())))
			return false;
		final UserField other = (UserField) obj;
		return other.getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		int result = 31;
		result += this.getId() == null ? 0 : this.getId().hashCode();
		return result;
	}
	@Override
	@PreUpdate
	public void onUpdate() {
		setModifyTime(new Date());
	}
}
