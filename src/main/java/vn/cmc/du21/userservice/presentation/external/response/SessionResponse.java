package vn.cmc.du21.userservice.presentation.external.response;

import java.sql.Timestamp;

public class SessionResponse {
    private long sessionId;
    private String token;
    private String expireTime;
    private String status;
    private long deviceId;
    private long userId;

    public SessionResponse() {
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
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

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public SessionResponse(long sessionId, String token, String expireTime, String status, long deviceId, long userId) {
        this.sessionId = sessionId;
        this.token = token;
        this.expireTime = expireTime;
        this.status = status;
        this.deviceId = deviceId;
        this.userId = userId;
    }
}
