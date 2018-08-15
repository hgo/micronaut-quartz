package quartz.retry.jobs;

import io.micronaut.context.annotation.Prototype;
import org.quartz.PersistJobDataAfterExecution;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Prototype
@PersistJobDataAfterExecution
public @interface Job {
}
