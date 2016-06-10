
package demo;

import org.json.simple.JSONObject;


/**
 *
 * @author Prince
 */
public class Demo {

   
    public static void main(String[] args) {
        try {
        String filePath = "C:\\Users\\uni5p_000\\Desktop\\IntuitCraftDemo\\demo\\src\\demo\\config.json";
        JSONObject config = utility.getConfig(filePath);
        JSONObject apiData = utility.getAPIData((String) config.get("apiURL"),(String)config.get("symbol"));
        double newProfit = utility.getProfit((double)apiData.get("LastPrice"), (long)config.get("buyPrice"));
        if( newProfit >  (long)config.get("minProfit"))
               utility.send((long)config.get("phNo"),(double)apiData.get("LastPrice"), newProfit, (String)config.get("domain"),(String)config.get("companyName"));
             
       } catch (NullPointerException e) {
            throw new RuntimeException(e);
      }
    }
}
