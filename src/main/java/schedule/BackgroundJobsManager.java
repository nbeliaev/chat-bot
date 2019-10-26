package schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class BackgroundJobsManager {
    private static final String TRIGGER_NAME = "MyTriggerName";
    private static final String GROUP = "simple_Group";
    private static final String JOB_NAME = "someJob";
    private static Scheduler scheduler;

    public static void main(String[] args) throws Exception {
        System.out.println(" QuartzSchedulerApp main thread: " + Thread.currentThread().getName());

        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();

        Trigger trigger = buildSimpleSchedulerTrigger();
        // buildCronSchedulerTrigger();// for cron job trigger
        scheduleJob(trigger);

    }

    private static void scheduleJob(Trigger trigger) throws Exception {

        JobDetail someJobDetail = JobBuilder.newJob(BackgroundJob.class).withIdentity(JOB_NAME, GROUP).build();

        scheduler.scheduleJob(someJobDetail, trigger);

    }

    private static Trigger buildSimpleSchedulerTrigger() {

        int INTERVAL_SECONDS = 60;

        return TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, GROUP)
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(INTERVAL_SECONDS).repeatForever())
                .build();
    }

    private static Trigger buildCronSchedulerTrigger() {
        String CRON_EXPRESSION = "0 * * * * ?";

        return TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(CRON_EXPRESSION)).build();
    }
}
