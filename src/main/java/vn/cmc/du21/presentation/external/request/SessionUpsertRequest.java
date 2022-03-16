package vn.cmc.du21.presentation.external.request;


public class SessionUpsertRequest {
    private Long userId;
    private Long deviceId;
    private String token;
    private String expireTime;
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

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SessionUpsertRequest(Long userId, Long deviceId, String token, String expireTime, String status) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.token = token;
        this.expireTime = expireTime;
        this.status = status;
    }
}
