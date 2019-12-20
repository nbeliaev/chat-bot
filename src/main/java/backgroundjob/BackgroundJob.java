package backgroundjob;

import database.dao.Dao;
import database.dao.ProductDao;
import database.dao.StoreDao;
import database.entities.ProductEntity;
import database.entities.StoreEntity;
import database.externaldata.DataReceiver;
import database.externaldata.ExchangeMessage;
import exceptions.ConnectionException;
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
    private static final String PRODUCTS_RESOURCE = "products";
    private final DataReceiver dataReceiver = new DataReceiver();
    private final static Logger log = LogManager.getLogger(BackgroundJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Begin of receiving data from 1C:Enterprise.");
        receiveStores();
        String messageId = "";
        do {
            messageId = receiveProducts(messageId);
        } while (!messageId.isEmpty());
        log.info("End of receiving data from 1C:Enterprise.");
    }

    private void receiveStores() throws JobExecutionException {
        log.info("Begin of receiving stores.");
        final ExchangeMessage message = getExchangeMessage(STORES_RESOURCE);
        final StoreEntity[] stores = JsonParser.read(message.getData(), StoreEntity[].class);
        final Dao<StoreEntity> dao = new StoreDao();
        Arrays.stream(stores).forEach(
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
        log.info("End of receiving stores.");
    }

    private String receiveProducts(String messageId) throws JobExecutionException {
        log.info("Begin of receiving products." + (messageId.isEmpty() ? "" : " Message id: " + messageId));
        final ExchangeMessage message = getExchangeMessage(PRODUCTS_RESOURCE, messageId);
        if (message.getData().isEmpty()) {
            log.info("End of receiving products.");
            return message.getId();
        }
        final ProductEntity[] products = JsonParser.read(message.getData(), ProductEntity[].class);
        final Dao<ProductEntity> dao = new ProductDao();
        Arrays.stream(products).forEach(
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
        log.info("End of receiving products.");
        return message.getId();
    }

    private ExchangeMessage getExchangeMessage(String resource) throws JobExecutionException {
        return getExchangeMessage(resource, "");
    }

    private ExchangeMessage getExchangeMessage(String resource, String messageId) throws JobExecutionException {
        try {
            return dataReceiver.getExchangeMessage(resource, messageId);
        } catch (ConnectionException e) {
            log.error(e);
            throw new JobExecutionException(e);
        }
    }
}
