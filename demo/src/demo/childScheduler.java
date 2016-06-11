/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;


import java.text.ParseException;
import java.util.Date;


import org.quartz.*;

public class childScheduler implements Job{

        public childScheduler() {

        }

        static Scheduler childSched;
        public void execute(JobExecutionContext context)
        throws JobExecutionException {
         try {
          System.out.println("Initializing child scheduler");
          SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
          childSched = schedFact.getScheduler();
          childSched.start();
          JobDetail jobDetail = new JobDetail(
           "Child scheduler",
           "schedulers",
           HelloJob.class
          );
          jobDetail.getJobDataMap().put(
           "type",
           "FULL"
          );
          CronTrigger trigger = new CronTrigger(
           "Child scheduler",
           "schedulers"
          );
          
          trigger.setCronExpression(
           "0 0/1 * 1/1 * ? *"
          );
          childSched.scheduleJob(jobDetail, trigger);
         } catch (Exception e) {
          e.printStackTrace();
         }
        }


}