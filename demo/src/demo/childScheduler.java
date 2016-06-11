/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;


import org.quartz.*;

public class childScheduler implements Job{

        public childScheduler() {

        }

        static Scheduler childSched;
        public void execute(JobExecutionContext context)
        throws JobExecutionException {
         HashSet<String> holidays = utility.getHolidays();
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
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   Date date = new Date();
           
          if(!holidays.contains(dateFormat.format(date)))
            childSched.scheduleJob(jobDetail, trigger);
          else
            System.out.println("Today is holiday. Will check on next buisness day");
         } catch (Exception e) {
          e.printStackTrace();
         }
        }


}