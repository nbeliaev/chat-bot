package database.externaldata;

import configs.Config;
import exceptions.ConnectionException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import utils.AuthUtil;

import java.io.IOException;

public class DataReceiver {
    private int timeOut = 20_000;
    private boolean ignoreContentType = true;
    private Connection.Method method = Connection.Method.GET;
    private final static Logger log = LogManager.getLogger(DataReceiver.class);

    @SuppressWarnings("unused")
    public DataReceiver(int timeOut,
                        Connection.Method method,
                        boolean ignoreContentType) {
        this.timeOut = timeOut;
        this.method = method;
        this.ignoreContentType = ignoreContentType;
    }

    public DataReceiver() {
    }

    public ExchangeMessage getExchangeMessage(String resource) throws ConnectionException {
        return getExchangeMessage(resource, "");
    }

    public ExchangeMessage getExchangeMessage(String resource, String messageId) throws ConnectionException {
        final Connection.Response response;
        try {
            final String resourcePrefix = "/hs/bot/";
            final String url = Config.getProperty(Config.CONNECTION_1C) + resourcePrefix + resource;
            log.info("Receiving data from " + url);
            response = Jsoup.connect(url)
                    .header("Authorization", AuthUtil.getBasicAuthorization())
                    .header("MessageId", messageId)
                    .timeout(timeOut)
                    .ignoreContentType(ignoreContentType)
                    .method(method)
                    .execute();
        } catch (IOException e) {
            String msg = String.format("Couldn't connect to 1C:Enterprise: the reason was %s. ", e.getMessage()) +
                    String.format("The url was %s. ", ((HttpStatusException) e).getUrl()) +
                    String.format("The status code was %s.", ((HttpStatusException) e).getStatusCode());
            throw new ConnectionException(msg);
        }
        return new ExchangeMessage(response.header("MessageId"), response.body());
    }
}
