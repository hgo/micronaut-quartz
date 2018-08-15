package quartz.retry;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Maybe;

import javax.inject.Inject;
import java.util.Date;

@Controller(value = "/")
public class Api {

    @Inject
    EventBus eventBus;

    @Get(value = "/")
    public Maybe<String> get() {
        eventBus.postAsync(new GetEvent(new Date()));
        return Maybe.empty();
    }
}
