package backgroundjob;

import database.dao.Dao;
import database.dao.PriceDao;
import database.dao.ProductDao;
import database.dao.StoreDao;
import database.entities.PriceEntity;
import database.entities.ProductEntity;
import database.entities.StoreEntity;
import database.externaldata.DataReceiver;
import exceptions.ConnectionException;
import exceptions.ExistDataBaseException;
import exceptions.NotExistDataBaseException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.JsonParser;

import java.util.Arrays;

public class BackgroundJob implements Job {
    private final DataReceiver dataReceiver;
    private static final String STORES_RESOURCE = "stores";
    private static final String PRODUCT_RESOURCE = "products";
    private static final String PRICES_RESOURCE = "prices";

    {
        dataReceiver = new DataReceiver();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        processStores();
        processProducts();
        processPrices();
    }

    private void processStores() throws JobExecutionException {
        final String json = getJson(STORES_RESOURCE);
        final StoreEntity[] stores = JsonParser.read(json, StoreEntity[].class);
        final Dao<StoreEntity> dao = new StoreDao();
        Arrays.stream(stores).forEach(
                // TODO: need to optimise
                entity -> {
                    try {
                        dao.findByUuid(StoreEntity.class, entity.getUuid());
                        dao.update(entity);
                    } catch (NotExistDataBaseException e) {
                        dao.save(entity);
                    }
                });
    }

    private void processProducts() throws JobExecutionException {
        final String json = getJson(PRODUCT_RESOURCE);
        final ProductEntity[] products = JsonParser.read(json, ProductEntity[].class);
        final Dao<ProductEntity> dao = new ProductDao();
        Arrays.stream(products).forEach(
                // TODO: need to optimise
                entity -> {
                    try {
                        dao.findByUuid(ProductEntity.class, entity.getUuid());
                        dao.update(entity);
                    } catch (NotExistDataBaseException e) {
                        dao.save(entity);
                    }
                });
    }

    private void processPrices() throws JobExecutionException {
        final String json = getJson(PRICES_RESOURCE);
        final PriceEntity[] products = JsonParser.read(json, PriceEntity[].class);
        final Dao<PriceEntity> dao = new PriceDao();
        dao.deleteAll();
        Arrays.stream(products).forEach(
                // TODO: need to optimise
                entity -> {
                    try {
                        dao.save(entity);
                    } catch (ExistDataBaseException e) {
                        throw new ExistDataBaseException(
                                String.format("Couldn't save price with uuid %s. Because it already exists.", entity.getUuid()));
                    }
                });
    }

    private String getJson(String resource) throws JobExecutionException {
        try {
            return dataReceiver.getResourceData(resource);
        } catch (ConnectionException e) {
            // TODO: to log exception
            throw new JobExecutionException(e);
        }
    }
}
