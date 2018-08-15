package quartz.retry.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class JobRequire {

    Logger logger = LoggerFactory.getLogger(JobRequire.class);
    UUID id = UUID.randomUUID();

    public void log() {
        logger.info(id.toString());
        logger.info("hi' I req from job");
    }
}
