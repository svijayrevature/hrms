::  JDBC Properties for hrms mssql DB
setx HRMS_MSSQL_URL "jdbc:sqlserver://<hostname>\\<instanceName>;DatabaseName=<databaseName>;"
setx HRMS_MSSQL_USERNAME "<userName>"
setx HRMS_MSSQL_PASSWORD "<password>"

::  JDBC Properties for hrms mysql DB
setx HRMS_MYSQL_URL "jdbc:mysql://<hostname>:<portNumber>/<databaseName>"
setx HRMS_MYSQL_USERNAME "<userName>"
setx HRMS_MYSQL_PASSWORD "<password>"

:: Sync Interval cron
setx LOG_SYNC_INTERVAL "0 0 8 * * ?"
