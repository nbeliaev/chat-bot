package exchange;

import exceptions.ConnectionException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public abstract class AbstractExternalDataProcessor {
    protected final String resource;
    private final static Logger log = LogManager.getLogger(AbstractExternalDataProcessor.class);

    public AbstractExternalDataProcessor(String resource) {
        this.resource = resource;
    }

    protected ExchangeMessage getExchangeMessage(String resource) throws ConnectionException {
        return getExchangeMessage(resource, "");
    }

    protected ExchangeMessage getExchangeMessage(String resource, String messageId) throws ConnectionException {
        MessageReceiver messageReceiver = new MessageReceiver(resource, messageId);
        return messageReceiver.getExchangeMessage();
    }

    public abstract void receive() throws ConnectionException;
}
