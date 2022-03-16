package vn.cmc.du21.common.restful;

public class StandardResponse<T> {
    //private StatusResponse status;
    private String message;
    private Integer messageCode;
    private T result;
    private String page;
    private String totalPage;
    private String totalRecord;

    public StandardResponse(String message, Integer messageCode, T data) {
        this.messageCode=messageCode;
        this.message = message;
        this.result = data;
    }

    public StandardResponse(String message, Integer messageCode){
        this.messageCode=messageCode;
        this.message = message;
    }

    public StandardResponse(String message, Integer messageCode, T result, String page, String totalPage, String totalRecord) {
        this.message = message;
        this.messageCode = messageCode;
        this.result = result;
        this.page = page;
        this.totalPage = totalPage;
        this.totalRecord = totalRecord;
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

    public Integer getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(Integer messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
