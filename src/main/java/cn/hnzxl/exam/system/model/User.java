package cn.hnzxl.exam.system.model;

import cn.hnzxl.exam.base.model.BaseModel;

/**
 * 用户model
 * 
 * @author ZXL
 * @date 2014年10月25日 上午12:52:20
 *
 */
public class User extends BaseModel<Long> {
	private Long userid;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 用户性别 1男，2女
	 */
	private Short gender;
	/**
	 * 用户登录名
	 */
	private String username;
	/**
	 * 用户权限，为空不限制权限<br/>
	 * exam 进入系统的权限;<br/>
	 * exam:user 用户管理菜单的权限<br/>
	 * exam:user:add 用户管理，添加用户的权限
	 */
	private String permission;
	/**
	 * 用户密码,存储方式 MD5(用户名+密码)
	 * 暂时存放 证书的状态 over 表示已经处理了
	 */
	private String password;
	/**
	 * 年龄，暂时存放最高分数
	 */
	private Integer age;
	/**
	 * 身份证号
	 */
	private String idnumber;
	/**
	 * 身份<br/>院校类别
	 * 1:学生，2:老师， 3:工作者
	 */
	private String identity;
	/**
	 * 手机号
	 */
	private String mobilenumber;
	/**
	 * 学号
	 */
	private String area;
	/**
	 * 学校
	 */
	private String school;
	/**
	 * 班级
	 */
	private String classname;
	/**
	 * 找回密码问题
	 */
	private String verifyquestion;
	/**
	 * 找回密码答案
	 */
	private String verifyanswer;
	//微信头像
	private String headimgurl;
	//unionid
	private String unionid;
	//昵称
	private String nickname;
	//状态
	private Integer status;
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Short getGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission == null ? null : permission.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;//这里存放分数
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber == null ? null : idnumber.trim();
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity == null ? null : identity.trim();
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber == null ? null : mobilenumber.trim();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school == null ? null : school.trim();
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname == null ? null : classname.trim();
	}

	public String getVerifyquestion() {
		return verifyquestion;
	}

	public void setVerifyquestion(String verifyquestion) {
		this.verifyquestion = verifyquestion == null ? null : verifyquestion.trim();
	}

	public String getVerifyanswer() {
		return verifyanswer;
	}

	public void setVerifyanswer(String verifyanswer) {
		this.verifyanswer = verifyanswer == null ? null : verifyanswer.trim();
	}

	@Override
	public Long getId() {
		return userid;
	}

	@Override
	public void setId(Long userid) {
		this.userid= userid;
	}
	/**
	 * 班级
	 */
	private Integer grade;
	/**
	 * 专业
	 */
	private String major;
	/**
	 * 微信openId
	 */
	private String wxOpenid;
	
	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getWxOpenid() {
		return wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}