package backgroundjob;

import database.dao.Dao;
import database.dao.StoreDaoImpl;
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
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DataReceiver dataReceiver = new DataReceiver();
        final String json;
        try {
            json = dataReceiver.getResourceData("stores");
        } catch (ConnectionException e) {
            // TODO: to log exception
            throw new JobExecutionException(e);
        }
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
}
