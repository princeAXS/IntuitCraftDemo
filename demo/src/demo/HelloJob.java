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
      try {
        if(!utility.isValidTime()){
            utility.schedulerShutdown();
            System.exit(0);
        }
        String filePath = "C:\\Users\\uni5p_000\\Desktop\\IntuitCraftDemo\\demo\\src\\demo\\config.json";
        JSONObject config = utility.getConfig(filePath);
        JSONObject apiData = utility.getAPIData((String) config.get("apiURL"),(String)config.get("symbol"));
        System.out.println("Current Price "+(double)apiData.get("LastPrice"));
        double newProfit = utility.getProfit((double)apiData.get("LastPrice"), (long)config.get("buyPrice"));
        if( newProfit >  (long)config.get("minProfit")){
               utility.send((long)config.get("phNo"),(double)apiData.get("LastPrice"), newProfit, (String)config.get("domain"),(String)config.get("companyName"));
               utility.schedulerShutdown();
        }
       } catch (NullPointerException e) {
            throw new RuntimeException(e);
      }
      
    }
  }
