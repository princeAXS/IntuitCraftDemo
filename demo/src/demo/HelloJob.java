/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;
import org.json.simple.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 *
 * @author Prince
 */
public class HelloJob implements Job {

    public HelloJob() {
    }

    public void execute(JobExecutionContext context)
      throws JobExecutionException
    {
        System.out.println("Application has been started. Checking current prices");
      try {
        if(!utility.isValidTime()){
            System.out.println("Stock market is closed. exiting... Will Get back tommorow at 9.30 AM EST");
            utility.schedulerShutdown();
        }
        else{
        String filePath = "C:\\Users\\uni5p_000\\Desktop\\IntuitCraftDemo\\demo\\src\\demo\\config.json";
        JSONObject config = utility.getConfig(filePath);
        JSONObject apiData = utility.getAPIData((String) config.get("apiURL"));
        System.out.println("Current Price "+(double)apiData.get("LastPrice"));
        double newProfit = utility.getProfit((double)apiData.get("LastPrice"), (long)config.get("buyPrice"));
        if( newProfit >  (long)config.get("minProfit")){
               utility.send((long)config.get("phNo"),(double)apiData.get("LastPrice"), newProfit, (String)config.get("domain"),(String)config.get("companyName"));
               System.out.println("Threshold profit acheived. exiting now....");
               utility.schedulerShutdown();
        }
        else
        {
            System.out.println("Cannot make any profit at this time. Will check again after 30 mins");
        }
        }
       } catch (NullPointerException e) {
            throw new RuntimeException(e);
      }
      
    }
  }
