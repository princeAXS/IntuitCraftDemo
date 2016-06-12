# IntuitCraftDemo
Java project for routine check for stock price of particular company using a stock API.

# How to operate it.

# Expected Output
Case 1: if today is holiday

o/p: Master scheduler will be initiated
Console: Today is holiday. Will check on next business day

Case 2: if today is not holiday but market(NYSE at EST time) is closed

o/p: Master scheduler will be initiated
Console: Stock market is closed. exiting... Will Get back tomorrow at 9.30 AM EST

Case 3A: if market is opened and profit has not been achieved

o/p: Master scheduler will be initiated
Console: "Cannot make any profit at this time. Will check again after 15 mins"

Case 3B: if market is opened and profit has been achieved[ USER WILL RECEIVE SMS NOTIFICATION]

o/p: Master scheduler will be initiated
Console: Sent message successfully....
Threshold profit achieved. exiting for now. Service will start next on next business day at 9.30 EST