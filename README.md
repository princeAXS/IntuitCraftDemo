# IntuitCraftDemo
Java project for routine check for stock price of particular company using a stock API.

# Technical details
#### Functionality of each class
1.)Config.json : It contains the important details such as company name, number of stocks, minimum profit and phone number.

2.) Utility class: it acts as a helper class by providing basic functionalities such as getting API data, sending email, parsing a file and unscheduling/rescheduling the Cron jobs.

3.) HelloJob class : it is the mail application which gets company name, stock price and calculates the profit. If profit reaches the threshold then it sends an sms notification.
It is also responsible for un-scheduling a job if time is not in business hours and making delays if stock prices are constant or going down

> Scheduling has been done with the use of Quartz

4.) ChildScheduler class: it triggers HelloJob class in every 15 mins once ChildScheduler itself gets triggered by MasterSceduler. It is also responsible for checking if the day is a holiday or not,
if yes then it skips triggering of HelloJobclass for that day.

5.) MasterSceduler class : Before anything it gets the holidays data from API and works accordingly. So it triggers childScheduler every week day at 6.30 AM PST  and provides the holidays data. 


