/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;


import java.text.ParseException;
import java.util.Date;


import org.quartz.*;

public class childScheduler {

	/**
	 * @param args
	 */
         static SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
         static Scheduler sched;
	public static void main(String[] args) {
		try {
            sched = schedFact.getScheduler();
            sched.start();
            JobDetail jobDetail = new JobDetail(
                "Income Report",
                "Report Generation",
                HelloJob.class
              );
            jobDetail.getJobDataMap().put(
                                      "type",
                                      "FULL"
                                     );
            CronTrigger trigger = new CronTrigger(
              "Income Report",
              "Report Generation"
            );
            trigger.setCronExpression(
              "0 0/1 * 1/1 * ? *"
            );
            sched.scheduleJob(jobDetail, trigger);
          } catch (Exception e) {
            e.printStackTrace();
    }
	}

	
}
