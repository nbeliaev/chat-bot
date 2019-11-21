package backgroundjob;

import database.dao.Dao;
import database.dao.ProductDao;
import database.dao.StoreDao;
import database.entities.ProductEntity;
import database.entities.StoreEntity;
import database.externaldata.DataReceiver;
import exceptions.ConnectionException;
import exceptions.NotExistDataBaseException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.JsonParser;

import java.util.Arrays;

public class BackgroundJob implements Job {
    private final DataReceiver dataReceiver;

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
        final String json = getJson("stores");
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
        final String json = getJson("products");
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
        final String json = getJson("prices");
        final ProductEntity[] products = JsonParser.read(json, ProductEntity[].class);
        final Dao<ProductEntity> dao = new ProductDao();
        Arrays.stream(products).forEach(
                // TODO: need to optimise
                entity -> {
                    try {
                        //final ProductEntity persistedEntity = dao.findByPattern(ProductEntity.class, "external_id", entity.getExternalId());
                        //persistedEntity.setStores(entity.getStores());
                        // dao.update(persistedEntity);
                    } catch (NotExistDataBaseException e) {
                        throw new NotExistDataBaseException("not exist");
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
