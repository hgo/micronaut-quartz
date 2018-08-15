package quartz.retry.jobs;

public class RetryPolicy {


    private int interval;
    private int maxRetry;

    public static RetryPolicy of(int interval, int maxRetry) {
        return new RetryPolicy(interval, maxRetry);
    }

    public int interval() {
        return this.interval;
    }

    public int maxRetry() {
        return maxRetry;
    }

    private RetryPolicy(int interval, int maxRetry) {
        this.interval = interval;
        this.maxRetry = maxRetry;
    }
}
