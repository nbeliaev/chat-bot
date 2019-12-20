package database.externaldata;

import exceptions.ConnectionException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class DataReceiverTest {
    private static final DataReceiver dataReceiver = new DataReceiver();
    private static final String STORES_RESOURCE = "stores";
    private static final String PRODUCT_RESOURCE = "products";
    private static final String PRICES_RESOURCE = "prices";

    @Test
    public void getStores() throws ConnectionException {
        final String data = dataReceiver.getExchangeMessage(STORES_RESOURCE).getData();
        assertFalse(data.isEmpty());
    }

    @Test
    public void getProducts() throws ConnectionException {
        final String data = dataReceiver.getExchangeMessage(PRODUCT_RESOURCE).getData();
        assertFalse(data.isEmpty());
    }

    @Test
    public void getPrices() throws ConnectionException {
        final String data = dataReceiver.getExchangeMessage(PRICES_RESOURCE).getData();
        assertFalse(data.isEmpty());
    }

}