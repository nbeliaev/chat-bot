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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.JsonParser;

import java.util.Arrays;

@SuppressWarnings("unused")
public class BackgroundJob implements Job {
    private static final String STORES_RESOURCE = "stores";
    private static final String PRODUCT_RESOURCE = "products";
    private static final String PRICES_RESOURCE = "prices";
    private final DataReceiver dataReceiver = new DataReceiver();
    private final static Logger log = LogManager.getLogger(BackgroundJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Receive data from an external source.");
        processStores();
        processProducts();
        processPrices();
        log.info("Receiving data from an external source is ended.");
    }

    private void processStores() throws JobExecutionException {
        log.info("Receive stores from an external source.");
        final String json = getJson(STORES_RESOURCE);
        final StoreEntity[] stores = JsonParser.read(json, StoreEntity[].class);
        log.info(String.format("%s new store objects were received.", stores.length));
        final Dao<StoreEntity> dao = new StoreDao();
        Arrays.stream(stores).forEach(
                // TODO: need to optimise
                entity -> {
                    try {
                        dao.findByUuid(StoreEntity.class, entity.getUuid());
                        dao.update(entity);
                        log.debug(String.format("The store object %s was updated", entity.getUuid()));
                    } catch (NotExistDataBaseException e) {
                        dao.save(entity);
                        log.debug(String.format("The store object %s was saved", entity.getUuid()));
                    }
                });
    }

    private void processProducts() throws JobExecutionException {
        log.info("Receive products from an external source.");
        final String json = getJson(PRODUCT_RESOURCE);
        final ProductEntity[] products = JsonParser.read(json, ProductEntity[].class);
        final Dao<ProductEntity> dao = new ProductDao();
        Arrays.stream(products).forEach(
                // TODO: need to optimise
                entity -> {
                    try {
                        dao.findByUuid(ProductEntity.class, entity.getUuid());
                        dao.update(entity);
                        log.debug(String.format("The product object %s was updated", entity.getUuid()));
                    } catch (NotExistDataBaseException e) {
                        dao.save(entity);
                        log.debug(String.format("The product object %s was saved", entity.getUuid()));
                    }
                });
    }

    // TODO: rework this method
    private void processPrices() throws JobExecutionException {
        log.info("Receive product prices from an external source.");
        final String json = getJson(PRICES_RESOURCE);
        final PriceEntity[] products = JsonParser.read(json, PriceEntity[].class);
        final Dao<PriceEntity> dao = new PriceDao();
        dao.deleteAll();
        Arrays.stream(products).forEach(
                // TODO: need to optimise
                entity -> {
                    try {
                        dao.save(entity);
                        log.debug(String.format("The price product object %s was saved", entity.getUuid()));
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
            log.error(e);
            throw new JobExecutionException(e);
        }
    }
}
