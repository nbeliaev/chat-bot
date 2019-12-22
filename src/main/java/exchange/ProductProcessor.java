package exchange;

import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.ProductEntity;
import exceptions.ConnectionException;
import exceptions.ExistDataBaseException;
import exceptions.NotExistDataBaseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.JsonParser;

import java.util.Arrays;

public class ProductProcessor extends AbstractExternalDataProcessor {
    private final static Logger log = LogManager.getLogger(ProductProcessor.class);

    public ProductProcessor(String resource) {
        super(resource);
    }

    @Override
    public void receive() throws ConnectionException {
        String messageId = "";
        do {
            messageId = receiveProductsByMessageId(messageId);
        } while (!messageId.isEmpty());
    }

    private String receiveProductsByMessageId(String messageId) throws ConnectionException {
        log.info("Begin of receiving products." + (messageId.isEmpty() ? "" : " Message id: " + messageId));
        final ExchangeMessage message = getExchangeMessage(resource, messageId);
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
                        log.debug(String.format("The product %s was updated", entity.getUuid()));
                    } catch (NotExistDataBaseException e) {
                        try {
                            dao.save(entity);
                            log.debug(String.format("The product %s was saved", entity.getUuid()));
                        } catch (ExistDataBaseException exist) {
                            log.warn(String.format("The product %s with name %s already exists", entity.getUuid(), entity.getName()));
                        }
                    }
                });
        log.info("End of receiving products.");
        return message.getId();
    }
}
