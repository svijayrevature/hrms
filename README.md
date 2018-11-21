# HRMS
## Description
This application is used to fetch details from mssql biometric server
and store the data on to a mysql server. And regularly update them. It
is also used to generate reports for all the users in revature regarding
their daily attendance.

## Setup

After cloning the repository run the following commands in a shell or prompt.

```
cd hrms 
./gradlew build
```

After running build wee must set up the java environment for that we must
download also set up the local mssql server for tcp for which steps are mentioned
below and also reference links are provided:

Download the following [Microsoft DB driver](https://www.microsoft.com/en-US/download/details.aspx?id=11774) and
install (the application will just unzip the drivers) the driver to a particular location and copy the `{installation_folder}/sqljdbc_6.0/enu/auth/x64/sqljdbc_auth.dll` file to
`{JAVA_HOME}/bin`

For the TCP/IP connection setup please do refer the document link below.  
 
[Connection JAVA settings for mssql](https://github.com/bhochhi/howto-guide/wiki/How-to-connect-SQL-Server-Using-Authentication-in-Spring-based-app.)

[For making mssql a tcp port accessible Document](https://www-01.ibm.com/support/docview.wss?uid=swg21692573&aid=1)