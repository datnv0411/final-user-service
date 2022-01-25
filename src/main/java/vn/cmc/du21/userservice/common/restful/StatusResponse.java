package vn.cmc.du21.userservice.common.restful;

public enum StatusResponse {
    SUCCESSFUL("Successful", 200), CREATED("Created", 201), ACCEPTED("Accepted", 202)
    , NOCONTENT("No Content", 204), BADREQUEST("Bad Request", 400), UNAUTHORIZED("UnAuthorized", 401)
    , FORBIDDEN("Forbidden", 403), NOTFOUND("Not Found", 404), INTERNALSERVERERROR("Internal Server Error", 500);

    final private String status;

    StatusResponse(String status, Integer statusInt) {
        this.status = status;
        this.statusInt = statusInt;
    }

    public String getStatus() {
        return status;
    }

    final private Integer statusInt;

    public Integer getStatusInt() {
        return statusInt;
    }
}
