package quartz.retry;

import com.google.common.eventbus.Subscribe;
import io.micronaut.context.annotation.Context;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quartz.retry.jobs.GetEventJob;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Context
public class ApplicationContext {

    static Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

    @Inject
    EventBus eventBus;

    @Inject
    Scheduler scheduler;

    @Inject
    private JobFactory jobFactory;

    @PostConstruct
    void init() {
        eventBus.registerAsync(this);
        eventBus.register(this);
        try {
            scheduler.setJobFactory(jobFactory);
            scheduler.start();// define the job and tie it to our HelloJob class
//            JobDetail job = newJob(HelloJob.class)
//                    .withIdentity("myJob", "group1") // name "myJob", group "group1"
//                    .build();
//
//            // Trigger the job to run now, and then every 40 seconds
//            Trigger trigger = newTrigger()
//                    .withIdentity("myTrigger", "group1")
//                    .startNow()
//                    .withSchedule(simpleSchedule()
//                            .withIntervalInSeconds(2)
//                            .repeatForever())
//                    .build();
//            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    void destroy() {
        logger.info("application context destroy");
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error("scheduler destroy failed", e);
        }
    }

    @Subscribe
    public void onDateEvent(GetEvent event) throws SchedulerException {
        JobDetail job = newJob(GetEventJob.class)
                .withIdentity("GetEventJob", "EventJobGroup")
                .build();

        Trigger trigger = newTrigger()
                .startNow()
                .build();
        scheduler.scheduleJob(job, trigger);

    }
}
