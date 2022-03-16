package vn.cmc.du21.persistence.external.order;

import com.google.gson.reflect.TypeToken;
import org.apache.http.client.utils.URIBuilder;
import vn.cmc.du21.persistence.external.AbstractRpc;
import vn.cmc.du21.persistence.external.order.request.CancelOrderRequest;
import vn.cmc.du21.persistence.external.order.response.OrderListResponse;

public class OrderRpc extends AbstractRpc {

    public OrderListResponse getOrderHistory(Integer userId) throws Exception {
        URIBuilder builder = new URIBuilder("http://order-service/get-order-history");
        builder.setParameter("userId", "userId");

        return this.get(builder.build(), null, new TypeToken<OrderListResponse>(){}).getResult();
    }

    public OrderListResponse cancelAllOrder(CancelOrderRequest cancelOrderRequest) throws Exception {
        URIBuilder builder = new URIBuilder("http://order-service/cancel-all-order");

        return this.post(builder.build(), null, cancelOrderRequest, new TypeToken<OrderListResponse>(){}).getResult();
    }

    public static void main(String[] args) throws Exception {
        OrderRpc orderRpc = new OrderRpc();
        orderRpc.cancelAllOrder(new CancelOrderRequest(1));
    }
}
