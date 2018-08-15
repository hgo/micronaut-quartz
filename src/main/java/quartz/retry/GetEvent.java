package quartz.retry;

import java.util.Date;

public class GetEvent {

    private final Date date;

    public GetEvent(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "GetEvent{" +
                "date=" + date +
                '}';
    }
}
