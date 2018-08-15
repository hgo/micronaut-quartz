package quartz.retry.jobs;

import org.quartz.*;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@quartz.retry.jobs.Job
public abstract class RetryableJob implements Job {

    @Inject
    Scheduler scheduler;

    private static final Logger logger = LoggerFactory.getLogger(RetryableJob.class);


    protected abstract void _execute(RetryableJobExecutionContext retryableJobExecutionContext);

    @Override
    public final void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        Integer retry = (Integer) jobDetail.getJobDataMap()
                .get("retry");
        retry = retry == null ? 0 : retry;
        try {
            jobDetail.getJobDataMap().put("retry", ++retry);
            _execute(new RetryableJobExecutionContext(jobExecutionContext));
            logger.info("success in retry : {}", retry);
        } catch (Throwable e) {
            logger.error("errors in retr : {}", retry);
            RetryPolicy policy = policy();
            if (retry > policy.maxRetry()) {
                logger.error("max retry count [{}] reached for job {}", retry, jobDetail);
                return;
            }
            JobDetail job = newJob(this.getClass())
                    .usingJobData(jobDetail.getJobDataMap())
                    .withIdentity(jobDetail.getKey().getName() + "_Retry_" + retry, "Retry")
                    .build();

            Trigger trigger = newTrigger()
                    .startAt(Date.from(Instant.now().plusSeconds(retry * policy.interval())))
                    .build();
            try {
                scheduler.scheduleJob(job, trigger);
            } catch (SchedulerException e1) {
                e1.printStackTrace();
            }
            throw new JobExecutionException(e);
        }
    }

    protected abstract RetryPolicy policy();

    static class RetryableJobExecutionContext {

        private final JobExecutionContext jobExecutionContext;

        RetryableJobExecutionContext(JobExecutionContext jobExecutionContext) {
            this.jobExecutionContext = jobExecutionContext;
        }


        public Scheduler getScheduler() {
            return jobExecutionContext.getScheduler();
        }

        public Trigger getTrigger() {
            return jobExecutionContext.getTrigger();
        }

        public Calendar getCalendar() {
            return jobExecutionContext.getCalendar();
        }

        public boolean isRecovering() {
            return jobExecutionContext.isRecovering();
        }

        public TriggerKey getRecoveringTriggerKey() throws IllegalStateException {
            return jobExecutionContext.getRecoveringTriggerKey();
        }

        public int getRefireCount() {
            return jobExecutionContext.getRefireCount();
        }

        public JobDataMap getMergedJobDataMap() {
            return jobExecutionContext.getMergedJobDataMap();
        }

        public JobDetail getJobDetail() {
            return jobExecutionContext.getJobDetail();
        }

        public Job getJobInstance() {
            return jobExecutionContext.getJobInstance();
        }

        public Date getFireTime() {
            return jobExecutionContext.getFireTime();
        }

        public Date getScheduledFireTime() {
            return jobExecutionContext.getScheduledFireTime();
        }

        public Date getPreviousFireTime() {
            return jobExecutionContext.getPreviousFireTime();
        }

        public Date getNextFireTime() {
            return jobExecutionContext.getNextFireTime();
        }

        public String getFireInstanceId() {
            return jobExecutionContext.getFireInstanceId();
        }

        public Object getResult() {
            return jobExecutionContext.getResult();
        }

        public void setResult(Object o) {
            jobExecutionContext.setResult(o);
        }

        public long getJobRunTime() {
            return jobExecutionContext.getJobRunTime();
        }

        public void put(Object o, Object o1) {
            jobExecutionContext.put(o, o1);
        }

        public Object get(Object o) {
            return jobExecutionContext.get(o);
        }
    }
}
