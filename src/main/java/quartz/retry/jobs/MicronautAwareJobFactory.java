package quartz.retry.jobs;

import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Infrastructure;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import javax.inject.Inject;
import java.util.Optional;

@Infrastructure
public class MicronautAwareJobFactory implements JobFactory {


    @Inject
    BeanContext beanContext;

    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        Class<? extends Job> jobClass = triggerFiredBundle.getJobDetail().getJobClass();
        Optional<? extends Job> bean = beanContext.findBean(jobClass);
        return bean.orElseThrow(JobInitializationException::new);
    }
}
