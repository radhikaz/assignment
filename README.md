# report-assignment

Run the following command to execute the sample program through Maven

mvn exec:java -Dexec.mainClass="com.jpm.report.App"

Below is the sample report format

---------------- Report ----------------
<br>
<br>
---------------- Amount in USD settled incoming everyday ----------------<br>
14904.45<br>
---------------- Amount in USD settled outgoing everyday ----------------<br>
10025.00<br>
---------------- Ranking of entities based on incoming amount ----------------<br>
bar(14904.45)<br>
---------------- Ranking of entities based on outgoing amount ----------------<br>
foo(10025.00)<br>


To build and execute the test cases run the following command

mvn package 
