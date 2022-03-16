package vn.cmc.du21.persistence.internal.entity;

import java.sql.Timestamp;

public class SessionData {
    private Long userId;
    private Long deviceId;
    private String token;
    private Timestamp expireTime;
    private String status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SessionData(Long userId, Long deviceId, String token, Timestamp expireTime, String status) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.token = token;
        this.expireTime = expireTime;
        this.status = status;
    }
}
