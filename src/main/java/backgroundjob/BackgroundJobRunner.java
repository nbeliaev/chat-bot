package backgroundjob;

import configs.Config;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@SuppressWarnings("unused")
public class BackgroundJobRunner {
    private final Class<? extends Job> clazz;
    private final static Logger log = LogManager.getLogger(BackgroundJob.class);

    public BackgroundJobRunner(Class<? extends Job> clazz) {
        this.clazz = clazz;
    }

    public void run() throws SchedulerException {
        final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        final JobDetail jobDetail = getJobDetail();
        final Trigger trigger = buildSimpleSchedulerTrigger();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            log.error(e);
        }
    }

    private JobDetail getJobDetail() {
        final String jobName = "Retrieve1CData";
        return JobBuilder.newJob(clazz).withIdentity(jobName).build();
    }

    private Trigger buildSimpleSchedulerTrigger() {
        final SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(Integer.parseInt(Config.getProperty(Config.UPDATE_FREQUENCY)))
                .repeatForever();
        final String taskOnceMinute = "TaskOnceMinute";
        return TriggerBuilder.newTrigger()
                .withIdentity(taskOnceMinute)
                .withSchedule(scheduleBuilder)
                .build();
    }
}
