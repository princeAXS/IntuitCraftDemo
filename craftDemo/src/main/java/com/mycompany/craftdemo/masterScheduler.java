/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.craftdemo;

/**
 *
 * @author Prince
 */
import org.quartz.*;
/**
 *
 * @author Prince
 */
public class masterScheduler {
 public static void main(String[] args) {
   java.util.Calendar futureDate = java.util.Calendar.getInstance();
  try {
   System.out.println("Initializing Master Scheduler");
   SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
   Scheduler masterSched = schedFact.getScheduler();
   masterSched.start();
   //will trigger childScheduler as its job
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
   // will trigger the job every week day at 6.30 AM
   trigger.setCronExpression(
    "0 30 6 ? * MON-FRI *"
   );

   //if its weekend then job is schedule for business day
   if(java.util.Calendar.DAY_OF_WEEK == 6 || java.util.Calendar.DAY_OF_WEEK == 7)
        System.out.println("Today is holiday. Will check on next buisness day");
   else{
       //it will trigger the job immediately if user starts after the 6.30 pm PST
       if(utility.compareDates("06:30")){
        futureDate.set(java.util.Calendar.HOUR_OF_DAY, 6);
        futureDate.set(java.util.Calendar.MINUTE, 29);
        futureDate.set(java.util.Calendar.SECOND, 30);
        trigger.setStartTime(futureDate.getTime());
       }
       else
           System.out.println("Application has been schedule to run at 6.30 PST");
       }
   
   
   masterSched.scheduleJob(jobDetail, trigger);
  } catch (Exception e) {
   e.printStackTrace();
  }
 }


}
