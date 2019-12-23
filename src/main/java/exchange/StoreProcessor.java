package exchange;

import database.dao.Dao;
import database.dao.StoreDao;
import database.entities.StoreEntity;
import exceptions.ConnectionException;
import exceptions.NotExistDataBaseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.JsonParser;

import java.util.Arrays;

public class StoreProcessor extends AbstractExternalDataProcessor {
    private final static Logger log = LogManager.getLogger(StoreProcessor.class);

    public StoreProcessor(String resource) {
        super(resource);
    }

    @Override
    public void receive() throws ConnectionException {
        log.info("Begin of receiving stores.");
        final ExchangeMessage message = getExchangeMessage(resource);
        final StoreEntity[] stores = JsonParser.read(message.getData(), StoreEntity[].class);
        if (message.getData().isEmpty()) {
            log.info("End of receiving stores.");
        }
        final Dao<StoreEntity> dao = new StoreDao();
        Arrays.stream(stores).forEach(
                entity -> {
                    try {
                        final StoreEntity foundedEntity = dao.findByUuid(StoreEntity.class, entity.getUuid());
                        foundedEntity.setName(entity.getName());
                        foundedEntity.setAddress(entity.getAddress());
                        foundedEntity.setPhoneNumber(entity.getPhoneNumber());
                        dao.update(foundedEntity);
                        log.debug(String.format("The store %s was updated", entity.getUuid()));
                    } catch (NotExistDataBaseException e) {
                        dao.save(entity);
                        log.debug(String.format("The store %s was saved", entity.getUuid()));
                    }
                });
        log.info("End of receiving stores.");
    }
}
