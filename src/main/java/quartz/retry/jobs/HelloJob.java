package quartz.retry.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.UUID;

@quartz.retry.jobs.Job
public class HelloJob implements Job {

    Logger logger = LoggerFactory.getLogger(HelloJob.class);

    @Inject
    JobRequire jobRequire;

    UUID id = UUID.randomUUID();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info(id.toString());
        jobRequire.log();
    }
}
