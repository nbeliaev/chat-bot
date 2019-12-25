package dev.fr13.exchange;

import dev.fr13.database.dao.Dao;
import dev.fr13.database.dao.ProductDao;
import dev.fr13.database.entities.ProductEntity;
import dev.fr13.exceptions.ConnectionException;
import dev.fr13.exceptions.ExistDataBaseException;
import dev.fr13.exceptions.NotExistDataBaseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import dev.fr13.utils.JsonParser;

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
