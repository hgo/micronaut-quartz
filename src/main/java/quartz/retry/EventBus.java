package quartz.retry;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.concurrent.Executors;

@Singleton
public class EventBus {

    static Logger logger = LoggerFactory.getLogger(EventBus.class);

    private SubscriberExceptionHandler exceptionHandler = (exception, context) -> {
        logger.error("", exception);
        logger.error("event: {} {}", context.getEvent().getClass().getCanonicalName(), context.getEvent());
        logger.error("subscriber: {} {}", context.getSubscriber().getClass().getCanonicalName(), context.getSubscriber());
        logger.error("method: {}", context.getSubscriberMethod());
    };

    private com.google.common.eventbus.EventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(3), exceptionHandler);
    private com.google.common.eventbus.EventBus eventBus = new com.google.common.eventbus.EventBus();


    public void registerAsync(Object object) {
        asyncEventBus.register(object);
    }

    public void unregisterAsync(Object object) {
        asyncEventBus.unregister(object);
    }

    public void postAsync(Object event) {
        asyncEventBus.post(event);
    }

    public void register(Object object) {
        eventBus.register(object);
    }

    public void unregister(Object object) {
        eventBus.unregister(object);
    }

    public void post(Object event) {
        eventBus.post(event);
    }
}
