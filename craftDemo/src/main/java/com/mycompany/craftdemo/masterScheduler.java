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
//import java.util.Calender;
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
   //will trigger childScheduler
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
   if(java.util.Calendar.DAY_OF_WEEK == 6 || java.util.Calendar.DAY_OF_WEEK == 7)
        System.out.println("Today is holiday. Will check on next buisness day");
   else{
       if(java.util.Calendar.HOUR>6 && java.util.Calendar.MINUTE >30){
        futureDate.set(java.util.Calendar.HOUR_OF_DAY, 6);
        futureDate.set(java.util.Calendar.MINUTE, 29);
        futureDate.set(java.util.Calendar.SECOND, 30);
        trigger.setStartTime(futureDate.getTime());
       }
       }
   
   
   masterSched.scheduleJob(jobDetail, trigger);
  } catch (Exception e) {
   e.printStackTrace();
  }
 }


}