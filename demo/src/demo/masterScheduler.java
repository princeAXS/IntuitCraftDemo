package demo;

import org.quartz.*;

/**
 *
 * @author Prince
 */
public class masterScheduler {
 public static void main(String[] args) {

  try {
   System.out.println("Initializing Master Scheduler");
   SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
   Scheduler masterSched = schedFact.getScheduler();
   masterSched.start();
   JobDetail jobDetail = new JobDetail(
    "Master Scheduler",
    "Master schedulers",
    childScheduler.class
   );
   jobDetail.getJobDataMap().put(
    "type",
    "FULL"
   );
   CronTrigger trigger = new CronTrigger(
    "Master Scheduler",
    "Master schedulers"
   );
   trigger.setCronExpression(
    "0 30 6 ? * MON-FRI *"
   );

   masterSched.scheduleJob(jobDetail, trigger);
  } catch (Exception e) {
   e.printStackTrace();
  }
 }


}