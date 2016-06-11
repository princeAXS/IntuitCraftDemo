/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.text.ParseException;
import java.util.*;
import org.json.simple.JSONObject;
import java.text.SimpleDateFormat;
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
              "	0 0/2 * 1/1 * ? *"
            );
            
            masterSched.scheduleJob(jobDetail, trigger);
          } catch (Exception e) {
            e.printStackTrace();
    }
}
    
    
}
