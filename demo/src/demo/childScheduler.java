package demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import org.quartz.*;

public class childScheduler implements Job {

 public childScheduler() {

 }
 
 static double lastPrice = Double.MIN_VALUE;
 static int waitTime = 0;
 static Scheduler childSched;
 static JobDetail jobDetail;
 static CronTrigger trigger;
 public void execute(JobExecutionContext context)
 throws JobExecutionException {
  HashSet < String > holidays = utility.getHolidays();
  try {
   System.out.println("Initializing child scheduler");
   SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
   childSched = schedFact.getScheduler();
   childSched.start();
   // will trigger main application 
   jobDetail = new JobDetail(
    "Child scheduler",
    "schedulers",
    HelloJob.class
   );
   jobDetail.getJobDataMap().put(
    "type",
    "FULL"
   );
   trigger = new CronTrigger(
    "Child scheduler",
    "schedulers"
   );
   // initially it will trigger the job after 15 mins
   trigger.setCronExpression(
    "0 0/15 * 1/1 * ? *"
   );
   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   Date date = new Date();

   if (!holidays.contains(dateFormat.format(date)))
    childSched.scheduleJob(jobDetail, trigger);
   else
    System.out.println("Today is holiday. Will check on next buisness day");
  } catch (Exception e) {
   e.printStackTrace();
  }
 }


}