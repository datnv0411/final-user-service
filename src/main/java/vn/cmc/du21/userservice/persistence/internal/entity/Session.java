package vn.cmc.du21.userservice.persistence.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sessionId;
    private String token;
    private String status;
    private long deviceId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Session() {
    }

    public Session(long deviceId, User user) {
        this.deviceId = deviceId;
        this.user = user;
    }

    public Session(long sessionId, String token, String status, long deviceId, User user) {
        this.sessionId = sessionId;
        this.token = token;
        this.status = status;
        this.deviceId = deviceId;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
