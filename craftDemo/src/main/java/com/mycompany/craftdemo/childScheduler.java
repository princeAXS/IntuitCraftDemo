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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import org.quartz.*;

public class childScheduler implements Job {
    
 static double lastPrice = Double.MIN_VALUE;
 static int waitTime = 0;
 static Scheduler childSched;
 static JobDetail jobDetail;
 static CronTrigger trigger;
 
 public childScheduler() {

 }
 
 public void execute(JobExecutionContext context)
 throws JobExecutionException {
  HashSet < String > holidays = utility.getHolidays();
  try {
   System.out.println("Initializing child scheduler");
   SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
   childSched = schedFact.getScheduler();
   childSched.start();
   // will trigger main application as its job 
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
   
   //checks for holiday, if today is holiday it skips the excecution of application for today
   if (!holidays.contains(dateFormat.format(date)))
    childSched.scheduleJob(jobDetail, trigger);
   else
    System.out.println("Today is holiday. Will check on next buisness day");
  } catch (Exception e) {
   e.printStackTrace();
  }
 }


}
