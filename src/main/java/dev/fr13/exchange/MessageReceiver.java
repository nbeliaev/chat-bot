package dev.fr13.exchange;

import dev.fr13.configs.Config;
import dev.fr13.exceptions.ConnectionException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import dev.fr13.utils.AuthUtil;

import java.io.IOException;

public class MessageReceiver {
    private final static String baseUrl = Config.getProperty(Config.CONNECTION_1C) + "/hs/bot/";
    private final String messageId;
    private final String resource;
    private final static Logger log = LogManager.getLogger(MessageReceiver.class);

    public MessageReceiver(String resource, String messageId) {
        this.resource = resource;
        this.messageId = messageId;
    }

    public ExchangeMessage getExchangeMessage() throws ConnectionException {
        final Connection.Response response;
        final String url = baseUrl + resource;
        try {
            log.info("Receiving data from " + url);
            response = Jsoup.connect(url)
                    .header("Authorization", AuthUtil.getBasicAuthorization())
                    .header("MessageId", messageId)
                    .timeout(20_000)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();
        } catch (IOException e) {
            String msg = String.format("Couldn't connect to 1C:Enterprise: the reason is %s. ", e.getMessage()) +
                    String.format("The url is %s. ", ((HttpStatusException) e).getUrl()) +
                    String.format("The status code is %s.", ((HttpStatusException) e).getStatusCode());
            throw new ConnectionException(msg);
        }
        final String messageId = response.header("MessageId");
        if (messageId == null) {
            final String message = String.format("Invalid response. The header Message id is not found. The url is %s", url);
            log.error(message);
            throw new IllegalStateException(message);
        }
        return new ExchangeMessage(
                messageId,
                response.body());
    }
}
