package vn.cmc.du21.userservice.persistence.internal.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SESSION")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sessionId;
    private String token;
    private Timestamp expireTime;
    private String status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Session() {
    }

    public Session(long sessionId, String token, Timestamp expireTime, String status, User user) {
        this.sessionId = sessionId;
        this.token = token;
        this.expireTime = expireTime;
        this.status = status;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
