package vn.cmc.du21.userservice.common.restful;

public class PageResponse<T> {
    private StatusResponse status;
    private String message;
    private T data;
    private String page;
    private String totalPage;
    private String totalRecord;

    public PageResponse(StatusResponse status, String message, T data, String page, String totalPage, String totalRecord) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.page = page;
        this.totalPage = totalPage;
        this.totalRecord = totalRecord;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }
}
