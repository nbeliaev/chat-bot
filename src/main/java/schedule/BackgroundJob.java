package schedule;

import externaldata.DataReceiver;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class BackgroundJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDateTime localTime = LocalDateTime.now();
        System.out.println("Run QuartzJob at " + localTime.toString());

        DataReceiver mytask = new DataReceiver();
        mytask.getResourceData("stores");
    }
}
