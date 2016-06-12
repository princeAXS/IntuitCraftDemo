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
import org.json.simple.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Prince
 */
public class HelloJob implements Job {

 public HelloJob() {}

 public void execute(JobExecutionContext context)
 throws JobExecutionException {
  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  //get current date time with Date()
  Date date = new Date();
  System.out.println(dateFormat.format(date));

  try {
      // unschedule the job if market is closed
   if (!utility.isValidTime()) {
    System.out.println("Stock market is closed. exiting... Will Get back tommorow at 9.30 AM EST");
    utility.schedulerShutdown();
   } else {
    System.out.println("Application has been started. Checking current prices");
    
    //getting configurations
    String filePath = utility.getFilePath();
    System.out.println(filePath);
    JSONObject config = utility.getConfig(filePath);
    JSONObject apiData = utility.getAPIData((String) config.get("apiURL"));
    Double currentPrice = (double) apiData.get("LastPrice");
    System.out.println("Current Price " + currentPrice);
    double newProfit = utility.getProfit(currentPrice, (long) config.get("buyPrice"));
    
    //if min profit is acheived then it unschedule the job for the day
    if (newProfit >= (long) config.get("minProfit")) {
     utility.send((long) config.get("phNo"), currentPrice, newProfit, (String) config.get("domain"), (String) config.get("companyName"));
     System.out.println("Threshold profit achieved. exiting for now. Service will start next on next business day at 9.30 EST");
     utility.schedulerShutdown();
    } else {

     //if stock prices are going down or constant , wait time will be incresed to 2 times.statrs with 15 min
     if (childScheduler.lastPrice >= currentPrice) {
      if(childScheduler.waitTime < 60)
        childScheduler.waitTime = childScheduler.waitTime == 0 ? 15 : childScheduler.waitTime * 2;
      System.out.println("Cannot make any profit at this time. Will check again after" + childScheduler.waitTime + " mins");
      utility.rescheduleJob();
     } else {
      childScheduler.waitTime = 0;
        System.out.println("Cannot make any profit at this time. Will check again after 15 mins");
        }
      childScheduler.lastPrice = currentPrice;

    }
   }
  } catch (NullPointerException e) {
   throw new RuntimeException(e);
  }

 }
}
