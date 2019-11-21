package database.externaldata;

import database.entities.ProductEntity;
import exceptions.ConnectionException;
import org.junit.Test;
import utils.JsonParser;

import static org.junit.Assert.assertFalse;

public class DataReceiverTest {
    private static final DataReceiver dataReceiver = new DataReceiver();
    private static final String STORES_RESOURCE = "stores";
    private static final String PRODUCT_RESOURCE = "products";
    private static final String PRODUCT_BALANCE_RESOURCE = "prices";


    @Test
    public void getStores() throws ConnectionException {
        final String data = dataReceiver.getResourceData(STORES_RESOURCE);
        assertFalse(data.isEmpty());
    }

    @Test
    public void getProducts() throws ConnectionException {
        final String data = dataReceiver.getResourceData(PRODUCT_RESOURCE);
        final ProductEntity[] products = JsonParser.read(data, ProductEntity[].class);
        assertFalse(data.isEmpty());
    }

    @Test
    public void getPrices() throws ConnectionException {
        final String data = dataReceiver.getResourceData(PRODUCT_BALANCE_RESOURCE);
        assertFalse(data.isEmpty());
    }

}