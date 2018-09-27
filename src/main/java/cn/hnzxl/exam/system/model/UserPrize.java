package cn.hnzxl.exam.system.model;

import java.util.Date;

import cn.hnzxl.exam.base.model.BaseModel;

public class UserPrize extends BaseModel<String>{
    private String userId;

    private String userName;

    private String validCode;

    private Date createTime;

    private String flag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode == null ? null : validCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

	@Override
	public String getId() {
		return userId;
	}

	@Override
	public void setId(String modelId) {
		this.userId = modelId;
	}
}