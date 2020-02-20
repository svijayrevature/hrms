/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 5.6.35-log : Database - hrms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `biometric_logs` */

DROP TABLE IF EXISTS `biometric_logs`;

CREATE TABLE `biometric_logs` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(10) NOT NULL,
  `RECORD_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `RECORD_TYPE` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=319957 DEFAULT CHARSET=utf8;

/*Table structure for table `branches` */

DROP TABLE IF EXISTS `branches`;

CREATE TABLE `branches` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `ADDRESS` longtext,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_branches_name` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `departments` */

DROP TABLE IF EXISTS `departments`;

CREATE TABLE `departments` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_departments_name` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Table structure for table `designations` */

DROP TABLE IF EXISTS `designations`;

CREATE TABLE `designations` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_designations_name` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

/*Table structure for table `employees` */

DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees` (
  `CODE` varchar(30) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `REPORTING_TO` varchar(10) DEFAULT NULL,
  `SHIFT_ID` bigint(20) DEFAULT NULL,
  `DEPARTMENT_ID` bigint(20) DEFAULT NULL,
  `BRANCH_ID` bigint(20) DEFAULT NULL,
  `DESIGNATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`CODE`),
  UNIQUE KEY `uk_employees_email` (`EMAIL`),
  KEY `fk_employees_shift_id_shift_timings` (`SHIFT_ID`),
  KEY `fk_employees_department_id_departments` (`DEPARTMENT_ID`),
  KEY `fk_employees_branch_id_branches` (`BRANCH_ID`),
  KEY `fk_employees_designation_id_designations` (`DESIGNATION_ID`),
  CONSTRAINT `fk_employees_branch_id_branches` FOREIGN KEY (`BRANCH_ID`) REFERENCES `branches` (`ID`),
  CONSTRAINT `fk_employees_department_id_departments` FOREIGN KEY (`DEPARTMENT_ID`) REFERENCES `departments` (`ID`),
  CONSTRAINT `fk_employees_designation_id_designations` FOREIGN KEY (`DESIGNATION_ID`) REFERENCES `designations` (`ID`),
  CONSTRAINT `fk_employees_shift_id_shift_timings` FOREIGN KEY (`SHIFT_ID`) REFERENCES `shift_timings` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `shift_timings` */

DROP TABLE IF EXISTS `shift_timings`;

CREATE TABLE `shift_timings` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `START` time NOT NULL,
  `END` time NOT NULL,
  `ZONE` varchar(20) NOT NULL,
  `ZONE_OFFSET` varchar(6) NOT NULL DEFAULT '+05:30',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_shift_timings_start_end` (`START`,`END`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
