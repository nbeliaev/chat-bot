package dev.fr13.backgroundjob;

import dev.fr13.exceptions.ConnectionException;
import dev.fr13.exchange.ProductProcessor;
import dev.fr13.exchange.StoreProcessor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@SuppressWarnings("unused")
public class BackgroundJob implements Job {
    private final static String STORES_RESOURCE = "stores";
    private final static String PRODUCTS_RESOURCE = "products";
    private final static Logger log = LogManager.getLogger(BackgroundJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Begin of receiving data from 1C:Enterprise.");
        final StoreProcessor storeProcessor = new StoreProcessor(STORES_RESOURCE);
        final ProductProcessor productProcessor = new ProductProcessor(PRODUCTS_RESOURCE);
        try {
            storeProcessor.receive();
            productProcessor.receive();
        } catch (ConnectionException e) {
            log.error(e);
            throw new JobExecutionException(e);
        }
        log.info("End of receiving data from 1C:Enterprise.");
    }
}