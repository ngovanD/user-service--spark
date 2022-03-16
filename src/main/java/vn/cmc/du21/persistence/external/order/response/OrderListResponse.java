package vn.cmc.du21.persistence.external.order.response;

import java.util.List;

public class OrderListResponse {
    private Integer userId;
    private List<Integer> orderList;

    public OrderListResponse(Integer userId, List<Integer> orderList) {
        this.userId = userId;
        this.orderList = orderList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Integer> orderList) {
        this.orderList = orderList;
    }
}
