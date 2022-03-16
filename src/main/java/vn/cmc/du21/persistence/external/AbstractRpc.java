package vn.cmc.du21.persistence.external;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import vn.cmc.du21.common.Setting;
import vn.cmc.du21.common.Util;
import vn.cmc.du21.common.restful.StandardResponse;
import vn.cmc.du21.common.restful.StatusResponse;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractRpc {
    private static final int TIMEOUT = 500;
    private static final int RETRY = 3;
    private static final int TIME_WAIT = 500;

    protected CloseableHttpClient httpClient;

    protected AbstractRpc() {
        this.initHttpClient();
    }

    private void initHttpClient() {
        ConnectionKeepAliveStrategy keepAliveStrategy = (response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000;
                }
            }
            return Duration.ofSeconds(30).toMillis();
        };

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(Setting.N_HTTP_CONN_POOL);
        connManager.setDefaultMaxPerRoute(Setting.N_THREAD);

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT).build();

        HttpRequestRetryHandler retryHandler = (exception, executionCount, httpContext) -> executionCount < RETRY;

        ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy = new ServiceUnavailableRetryStrategy(){

            @Override
            public boolean retryRequest(HttpResponse httpResponse, int executionCount, HttpContext httpContext) {
                return executionCount <= RETRY &&
                        httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE;
            }

            @Override
            public long getRetryInterval() {
                return TIME_WAIT;
            }
        };

        this.httpClient = HttpClients.custom().setKeepAliveStrategy(keepAliveStrategy)
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(config)
                .setRetryHandler(retryHandler)
                .setServiceUnavailableRetryStrategy(serviceUnavailableRetryStrategy)
                .build();
    }

    protected <T> StandardResponse<T> get(URI url, Map<String, String> header, TypeToken<T> typeToken) throws Exception {
        HttpGet request = new HttpGet(url);
        this.setHeader(request, header);

        return this.callMethod(request, typeToken);
    }

    protected <T> StandardResponse<T> post(URI url, Map<String, String> header, Object body, TypeToken<T> typeToken) throws Exception {
        HttpPost request = new HttpPost(url);
        this.setHeader(request, header);
        this.setBody(request, body);

        return this.callMethod(request, typeToken);
    }

    protected <T> StandardResponse<T> put(URI url, Map<String, String> header, Object body, TypeToken<T> typeToken) throws Exception {
        HttpPut request = new HttpPut(url);
        this.setHeader(request, header);
        this.setBody(request, body);

        return this.callMethod(request, typeToken);
    }

    protected <T> StandardResponse<T> delete(URI url, Map<String, String> header, TypeToken<T> typeToken) throws Exception {
        HttpDelete request = new HttpDelete(url);
        this.setHeader(request, header);

        return this.callMethod(request, typeToken);
    }

    protected <T> StandardResponse<T> options(URI url, Map<String, String> header, TypeToken<T> typeToken) throws Exception {
        HttpOptions request = new HttpOptions(url);
        this.setHeader(request, header);

        return this.callMethod(request, typeToken);
    }

    private void setHeader(HttpRequestBase request, Map<String, String> header) {
        if (header != null && !header.isEmpty()) {
            for(Map.Entry<String, String> entry : header.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private void setBody(HttpEntityEnclosingRequestBase request, Object body) throws UnsupportedEncodingException {
        if (Objects.nonNull(body)) {
            request.addHeader("content-type", "application/json");
            StringEntity postingString = new StringEntity(Util.getObjectMapper().toJson(body));
            request.setEntity(postingString);
        }
    }

    private <T> StandardResponse<T> callMethod(HttpRequestBase request, TypeToken<T> typeToken) throws Exception {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            switch (response.getStatusLine().getStatusCode()) {
                case HttpStatus.SC_OK:
                    break;
                default:
                    throw new Exception("Status: " + response.getStatusLine().getStatusCode());
            }
            String jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
            return new StandardResponse<>(StatusResponse.SUCCESSFUL.getStatus(), StatusResponse.SUCCESSFUL.getStatusInt(), Util.getObjectMapper().fromJson(jsonString, typeToken.getType()));
        }
    }
}
