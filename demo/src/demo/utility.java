/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;


import static demo.childScheduler.childSched;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Prince
 */
public class utility {

    public static JSONObject getConfig(String filePath) {
        try {
            // read the json file
            FileReader reader = new FileReader(filePath);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            return jsonObject;
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject getAPIData(String apiURL) {
        try {
            URL url = new URL(apiURL);
            //                System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null)
                sb.append(output);

            String res = sb.toString();
            JSONParser parser = new JSONParser();
            JSONObject json = null;
            try {
                json = (JSONObject) parser.parse(res);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            return json;
            
        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }

    public static double getProfit(double price, long buyPrice) {
        return (price - buyPrice) * 10000;
    }
    
    public static boolean isValidTime(){
        Calendar calendar = Calendar.getInstance();
        TimeZone fromTimeZone = calendar.getTimeZone();
        TimeZone toTimeZone = TimeZone.getTimeZone("EST");

        calendar.setTimeZone(fromTimeZone);
        calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
        if (fromTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
        }

        calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        if (toTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
        }
        
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        System.out.println(hour);
        return hour >= 9 && hour <= 24;  
    }
    
    public static void schedulerShutdown(){
        try{
      childScheduler.childSched.deleteJob("Child scheduler", "schedulers");
      } catch (Exception e) {
            e.printStackTrace();
    }
    }

    static void send(long phno, double price, double profit, String domain, String company) {
        HashMap < String, String > domainMap = new HashMap < > ();
        domainMap.put("TMobile", "tmomail.net ");
        domainMap.put("ATT", "txt.att.net");
        domainMap.put("Sprint", "messaging.sprintpcs.com");
        domainMap.put("Verizon", "vtext.com");
        String to = phno + "@" + domainMap.get(domain); //change accordingly

        // Sender's email ID needs to be mentioned
        String from = "uni5prince@gmail.com"; //change accordingly
        final String username = "uni5prince"; //change accordingly
        final String password = "savageph8893"; //change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Prices have gone up!!");

            // Now set the actual message
            message.setText("Hello Jane, Stock prices for " + company + " has reached to $" + price + " with profit of $" + profit);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static HashSet<String> getHolidays(){
        HashSet<String> holidays = new HashSet<>();
        JSONObject holidaysData = getAPIData("http://holidayapi.com/v1/holidays?country=US&year=2016");
        JSONObject dates = (JSONObject)holidaysData.get("holidays");

        for(Iterator iterator = dates.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            holidays.add(key);
        }
        return holidays;
    }
}