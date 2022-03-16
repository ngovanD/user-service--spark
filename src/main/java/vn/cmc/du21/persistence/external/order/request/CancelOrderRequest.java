package vn.cmc.du21.persistence.external.order.request;

public class CancelOrderRequest {
    private Integer userId;

    public CancelOrderRequest(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
