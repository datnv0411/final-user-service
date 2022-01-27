package vn.cmc.du21.userservice.persistence.internal.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "OTP")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long otpId;
    private String otpPass;
    private Timestamp otpTimeStamp;
    private int otpTry;
    @Column(nullable = false, unique = true)
    private String cellphone;
    private String status;

    public Otp() {
    }

    public Otp(String otpPass, Timestamp otpTimeStamp, int otpTry, String cellphone, String status) {
        this.otpPass = otpPass;
        this.otpTimeStamp = otpTimeStamp;
        this.otpTry = otpTry;
        this.cellphone = cellphone;
        this.status = status;
    }

    public Otp(long otpId, String otpPass, Timestamp otpTimeStamp, int otpTry, String cellphone, String status) {
        this.otpId = otpId;
        this.otpPass = otpPass;
        this.otpTimeStamp = otpTimeStamp;
        this.otpTry = otpTry;
        this.cellphone = cellphone;
        this.status = status;
    }

    public long getOtpId() {
        return otpId;
    }

    public void setOtpId(long otpId) {
        this.otpId = otpId;
    }

    public String getOtpPass() {
        return otpPass;
    }

    public void setOtpPass(String otpPass) {
        this.otpPass = otpPass;
    }

    public Timestamp getOtpTimeStamp() {
        return otpTimeStamp;
    }

    public void setOtpTimeStamp(Timestamp otpTimeStamp) {
        this.otpTimeStamp = otpTimeStamp;
    }

    public int getOtpTry() {
        return otpTry;
    }

    public void setOtpTry(int otpTry) {
        this.otpTry = otpTry;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
