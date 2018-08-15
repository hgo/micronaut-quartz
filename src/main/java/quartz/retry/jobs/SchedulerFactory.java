package quartz.retry.jobs;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.inject.Singleton;

@Factory
public class SchedulerFactory {


    org.quartz.SchedulerFactory factory = new org.quartz.impl.StdSchedulerFactory();
//    org.quartz.impl.jdbcjobstore.PostgreSQLDelegate.


    @Bean
    @Singleton
    Scheduler scheduler() {
        try {
            return factory.getScheduler();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
