package database.externaldata;

import exceptions.ConnectionException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class DataReceiverTest {
    private static final DataReceiver dataReceiver = new DataReceiver();
    private static final String STORES_RESOURCE = "stores";
    private static final String PRODUCT_RESOURCE = "products";

    @Test
    public void getStores() throws ConnectionException {
        final String data = dataReceiver.getResourceData(STORES_RESOURCE);
        assertFalse(data.isEmpty());
    }

    @Test
    public void getProducts() throws ConnectionException {
        final String data = dataReceiver.getResourceData(PRODUCT_RESOURCE);
        assertFalse(data.isEmpty());
    }

}