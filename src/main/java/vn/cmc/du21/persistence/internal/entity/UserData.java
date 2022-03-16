package vn.cmc.du21.persistence.internal.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class UserData {
    private long userId;
    private String userName;
    private String password;
    private String passwordSalt;
    private String passwordHashAlgorithm;
    private String fullName;
    private Date dob;
    private String gender;
    private String email;
    private String cellphone;
    private String address;
    private Timestamp createTime;
    private String createBy;
    private Timestamp updateTime;
    private String updateBy;

    public UserData(long userId, String userName, String password, String passwordSalt, String getPasswordHashAlgorithm, String fullName, Date dob, String gender, String email, String cellphone, String address, Timestamp createTime, String createBy, Timestamp updateTime, String updateBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.passwordHashAlgorithm = getPasswordHashAlgorithm;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.cellphone = cellphone;
        this.address = address;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHashAlgorithm() {
        return passwordHashAlgorithm;
    }

    public void setPasswordHashAlgorithm(String passwordHashAlgorithm) {
        this.passwordHashAlgorithm = passwordHashAlgorithm;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
