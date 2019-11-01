package database.externaldata;

import configs.Config;
import exceptions.ConnectionException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import utils.AuthUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DataReceiver {
    private int timeOut = 10_000;
    private boolean ignoreContentType = true;
    private Connection.Method method = Connection.Method.GET;

    public DataReceiver(int timeOut,
                        Connection.Method method,
                        boolean ignoreContentType) {
        this.timeOut = timeOut;
        this.method = method;
        this.ignoreContentType = ignoreContentType;
    }

    public DataReceiver() {
    }

    public String getResourceData(String resource) throws ConnectionException {
        final Connection.Response response;
        try {
            final String resourcePrefix = "/hs/bot/";
            response = Jsoup.connect(Config.getProperty(Config.CONNECTION_1C) + resourcePrefix + resource)
                    .header("Authorization", AuthUtil.getBasicAuthorization())
                    .timeout(timeOut)
                    .ignoreContentType(ignoreContentType)
                    .method(method)
                    .execute();
        } catch (IOException e) {
            throw new ConnectionException("Couldn't connect to 1C:Enterprise");
        }
        if (response.statusCode() != HttpServletResponse.SC_OK) {
            throw new ConnectionException(String.format("Something was wrong. The response status is %s", response.statusCode()));
        }
        return response.body();
    }
}
