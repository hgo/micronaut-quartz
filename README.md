# micronaut-quartz
micronaut quartz example with retry facility

## vm options

```
-Dorg.quartz.jobStore.dataSource=QDS 
-Dorg.quartz.dataSource.QDS.driver=<jdbc_driver>
-Dorg.quartz.dataSource.QDS.URL=<jdbc_url>
-Dorg.quartz.dataSource.QDS.user=<db_user>
-Dorg.quartz.dataSource.QDS.password=<db_pass>
-Dorg.quartz.dataSource.QDS.maxConnections=30
-Dorg.quartz.scheduler.instanceName=MyScheduler
-Dorg.quartz.threadPool.threadCount=3
-Dorg.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
-Dorg.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.<driver_delegate>
```

## Flow

 - hit the url `http://localhost:1234`
 - an async event [GetEvent] fires..
 - GetEvent subscriber schedules a retryable job [GetEventJob]
 - GetEventJob fails randomly
 - RetryableJob abstraction catch these errors and retry the job with the help of the RetryPolicy
