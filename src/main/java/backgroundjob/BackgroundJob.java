package backgroundjob;

import database.dao.Dao;
import database.dao.ProductDaoImpl;
import database.dao.StoreDaoImpl;
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
    }

    private void processStores() throws JobExecutionException {
        final String json = getJson("stores");
        final StoreEntity[] stores = JsonParser.read(json, StoreEntity[].class);
        final Dao<StoreEntity> dao = new StoreDaoImpl();
        Arrays.stream(stores).forEach(
                // TODO: need to optimise
                storeEntity -> {
                    try {
                        dao.findByUuid(StoreEntity.class, storeEntity.getUuid());
                        dao.update(storeEntity);
                    } catch (NotExistDataBaseException e) {
                        dao.save(storeEntity);
                    }
                });
    }

    private void processProducts() throws JobExecutionException {
        final String json = getJson("products");
        final ProductEntity[] products = JsonParser.read(json, ProductEntity[].class);
        final Dao<ProductEntity> dao = new ProductDaoImpl();
        Arrays.stream(products).forEach(
                // TODO: need to optimise
                productEntity -> {
                    try {
                        dao.findByUuid(ProductEntity.class, productEntity.getUuid());
                        dao.update(productEntity);
                    } catch (NotExistDataBaseException e) {
                        dao.save(productEntity);
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
