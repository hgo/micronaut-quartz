package quartz.retry;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import org.quartz.SchedulerException;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws SchedulerException, IOException {
        ApplicationContext context = Micronaut.run(Application.class);
    }
}