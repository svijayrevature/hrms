#!/bin/bash

#  JDBC Properties 
export HRMS_MSSQL_URL="jdbc:sqlserver://hrms-mssql.cvabfvhtaivi.ap-south-1.rds.amazonaws.com:1433;DatabaseName=hrms;"
export HRMS_MSSQL_USERNAME="<userName>"
export HRMS_MSSQL_PASSWORD="<password>"

#  JDBC Properties
export HRMS_MYSQL_URL="jdbc:mysql://hrms-mysql.cvabfvhtaivi.ap-south-1.rds.amazonaws.com:3306/hrms"
export HRMS_MYSQL_USERNAME="<userName>"
export HRMS_MYSQL_PASSWORD="<password>"

#  Sync Interval cron
export LOG_SYNC_INTERVAL="0 30 2 * * ?"
