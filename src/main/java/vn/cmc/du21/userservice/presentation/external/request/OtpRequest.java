package vn.cmc.du21.userservice.presentation.external.request;

public class OtpRequest {
    private String otpPass;
    private String cellphone;

    public OtpRequest() {
    }

    public OtpRequest(String otpPass, String cellphone) {
        this.otpPass = otpPass;
        this.cellphone = cellphone;
    }

    public String getOtpPass() {
        return otpPass;
    }

    public void setOtpPass(String otpPass) {
        this.otpPass = otpPass;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
