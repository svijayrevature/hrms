::  JDBC Properties for hrms mssql DB
setx HRMS_MSSQL_URL "jdbc:sqlserver://hrms-mssql.cvabfvhtaivi.ap-south-1.rds.amazonaws.com:1433;DatabaseName=hrms;"
setx HRMS_MYSQL_USERNAME "hrms"
setx HRMS_MYSQL_PASSWORD "20181205"

::  JDBC Properties for hrms mysql DB
setx HRMS_MYSQL_URL "jdbc:mysql://hrms-mysql.cvabfvhtaivi.ap-south-1.rds.amazonaws.com:3306/hrms"
setx HRMS_MYSQL_USERNAME "hrms"
setx HRMS_MYSQL_PASSWORD "20181205"

:: Sync Interval cron
setx LOG_SYNC_INTERVAL "0 30 2 * * ?"
