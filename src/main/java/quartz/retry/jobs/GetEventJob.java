package quartz.retry.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

@quartz.retry.jobs.Job
public class GetEventJob extends RetryableJob {

    private static final Logger logger = LoggerFactory.getLogger(GetEventJob.class);

    Random random = new Random();

    @Override
    protected void _execute(RetryableJobExecutionContext retryableJobExecutionContext) {
        if (random.nextBoolean()) {
            logger.info("executed");
        } else {
            throw new RuntimeException("random failed");
        }
    }

    @Override
    protected RetryPolicy policy() {
        return RetryPolicy.of(3, 10);
    }


}
