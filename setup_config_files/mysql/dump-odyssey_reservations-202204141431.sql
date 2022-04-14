-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: odyssey_reservations
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.24-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `clientId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `phone` varchar(13) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `confirmation`
--

DROP TABLE IF EXISTS `confirmation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `confirmation` (
  `confirmId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `phone` varchar(13) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`confirmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `head_count`
--

DROP TABLE IF EXISTS `head_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `head_count` (
  `countId` int(11) NOT NULL AUTO_INCREMENT,
  `adults` int(11) DEFAULT 0,
  `infants` int(11) DEFAULT 0,
  `children` int(11) DEFAULT 0,
  `reservations` int(11) DEFAULT 0,
  PRIMARY KEY (`countId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hotel` (
  `hotelId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) COLLATE utf8_bin NOT NULL,
  `branch` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`hotelId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logs` (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `voucherId` int(11) NOT NULL,
  `timestamp` text COLLATE utf8_bin NOT NULL,
  `info` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `timestamp` (`timestamp`) USING HASH,
  KEY `voucherId` (`voucherId`),
  CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`voucherId`) REFERENCES `voucher` (`voucherId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meal_plan`
--

DROP TABLE IF EXISTS `meal_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meal_plan` (
  `planId` int(11) NOT NULL AUTO_INCREMENT,
  `bb` tinyint(1) DEFAULT 0,
  `hb` tinyint(1) DEFAULT 0,
  `fb` tinyint(1) DEFAULT 0,
  `lunch` tinyint(1) DEFAULT 0,
  `dinner` tinyint(1) DEFAULT 0,
  `xtra_direct` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`planId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!50001 DROP VIEW IF EXISTS `reservations`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `reservations` (
  `voucherId` tinyint NOT NULL,
  `hotel_name` tinyint NOT NULL,
  `hotel_branch` tinyint NOT NULL,
  `status` tinyint NOT NULL,
  `client_name` tinyint NOT NULL,
  `client_phone` tinyint NOT NULL,
  `adults` tinyint NOT NULL,
  `children` tinyint NOT NULL,
  `infants` tinyint NOT NULL,
  `reservations` tinyint NOT NULL,
  `non_res` tinyint NOT NULL,
  `singles` tinyint NOT NULL,
  `doubles` tinyint NOT NULL,
  `tripplets` tinyint NOT NULL,
  `arrival` tinyint NOT NULL,
  `departure` tinyint NOT NULL,
  `days` tinyint NOT NULL,
  `nights` tinyint NOT NULL,
  `bb` tinyint NOT NULL,
  `hb` tinyint NOT NULL,
  `fb` tinyint NOT NULL,
  `lunch` tinyint NOT NULL,
  `dinner` tinyint NOT NULL,
  `xtra_direct` tinyint NOT NULL,
  `remarks` tinyint NOT NULL,
  `paid_by` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `room_type`
--

DROP TABLE IF EXISTS `room_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_type` (
  `roomId` int(11) NOT NULL AUTO_INCREMENT,
  `singles` tinyint(1) DEFAULT 0,
  `doubles` tinyint(1) DEFAULT 0,
  `tripplets` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`roomId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `time_line`
--

DROP TABLE IF EXISTS `time_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `time_line` (
  `unitId` int(11) NOT NULL AUTO_INCREMENT,
  `arrival` text COLLATE utf8_bin NOT NULL,
  `departure` text COLLATE utf8_bin NOT NULL,
  `days` int(11) DEFAULT NULL,
  `nights` int(11) DEFAULT NULL,
  PRIMARY KEY (`unitId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voucher` (
  `voucherId` int(11) NOT NULL AUTO_INCREMENT,
  `hotelId` int(11) NOT NULL,
  `clientId` int(11) NOT NULL,
  `STATUS` varchar(20) COLLATE utf8_bin DEFAULT 'Amend',
  `countId` int(11) NOT NULL,
  `roomId` int(11) NOT NULL,
  `unitId` int(11) NOT NULL,
  `planId` int(11) NOT NULL,
  `remarks` text COLLATE utf8_bin DEFAULT NULL,
  `confirmId` int(11) DEFAULT NULL,
  `paid_by` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`voucherId`),
  KEY `voucher_ibfk_1` (`confirmId`),
  KEY `countId` (`countId`),
  KEY `hotelId` (`hotelId`),
  KEY `planId` (`planId`),
  KEY `roomId` (`roomId`),
  KEY `unitId` (`unitId`),
  KEY `clientId` (`clientId`),
  CONSTRAINT `voucher_ibfk_1` FOREIGN KEY (`confirmId`) REFERENCES `confirmation` (`confirmId`) ON UPDATE NO ACTION,
  CONSTRAINT `voucher_ibfk_2` FOREIGN KEY (`countId`) REFERENCES `head_count` (`countId`) ON UPDATE NO ACTION,
  CONSTRAINT `voucher_ibfk_3` FOREIGN KEY (`hotelId`) REFERENCES `hotel` (`hotelId`) ON UPDATE NO ACTION,
  CONSTRAINT `voucher_ibfk_4` FOREIGN KEY (`planId`) REFERENCES `meal_plan` (`planId`) ON UPDATE NO ACTION,
  CONSTRAINT `voucher_ibfk_5` FOREIGN KEY (`roomId`) REFERENCES `room_type` (`roomId`) ON UPDATE NO ACTION,
  CONSTRAINT `voucher_ibfk_6` FOREIGN KEY (`unitId`) REFERENCES `time_line` (`unitId`) ON UPDATE NO ACTION,
  CONSTRAINT `voucher_ibfk_7` FOREIGN KEY (`clientId`) REFERENCES `client` (`clientId`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `voucher_plus`
--

DROP TABLE IF EXISTS `voucher_plus`;
/*!50001 DROP VIEW IF EXISTS `voucher_plus`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `voucher_plus` (
  `voucherId` tinyint NOT NULL,
  `hotelId` tinyint NOT NULL,
  `hotel_name` tinyint NOT NULL,
  `hotel_branch` tinyint NOT NULL,
  `clientId` tinyint NOT NULL,
  `client_name` tinyint NOT NULL,
  `client_phone` tinyint NOT NULL,
  `countId` tinyint NOT NULL,
  `adults` tinyint NOT NULL,
  `children` tinyint NOT NULL,
  `infants` tinyint NOT NULL,
  `reservations` tinyint NOT NULL,
  `roomId` tinyint NOT NULL,
  `singles` tinyint NOT NULL,
  `doubles` tinyint NOT NULL,
  `tripplets` tinyint NOT NULL,
  `unitId` tinyint NOT NULL,
  `arrival` tinyint NOT NULL,
  `departure` tinyint NOT NULL,
  `days` tinyint NOT NULL,
  `nights` tinyint NOT NULL,
  `planId` tinyint NOT NULL,
  `bb` tinyint NOT NULL,
  `hb` tinyint NOT NULL,
  `fb` tinyint NOT NULL,
  `lunch` tinyint NOT NULL,
  `dinner` tinyint NOT NULL,
  `xtra_direct` tinyint NOT NULL,
  `remarks` tinyint NOT NULL,
  `paid_by` tinyint NOT NULL,
  `confirmId` tinyint NOT NULL,
  `confirm_person_name` tinyint NOT NULL,
  `confirm_person_phone` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'odyssey_reservations'
--

--
-- Final view structure for view `reservations`
--

/*!50001 DROP TABLE IF EXISTS `reservations`*/;
/*!50001 DROP VIEW IF EXISTS `reservations`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`developer`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `reservations` AS select `v`.`voucherId` AS `voucherId`,`h`.`name` AS `hotel_name`,`h`.`branch` AS `hotel_branch`,`v`.`STATUS` AS `status`,`c`.`name` AS `client_name`,`c`.`phone` AS `client_phone`,`hc`.`adults` AS `adults`,`hc`.`children` AS `children`,`hc`.`infants` AS `infants`,`hc`.`reservations` AS `reservations`,`hc`.`adults` + `hc`.`children` + `hc`.`infants` - `hc`.`reservations` AS `non_res`,`rt`.`singles` AS `singles`,`rt`.`doubles` AS `doubles`,`rt`.`tripplets` AS `tripplets`,`tl`.`arrival` AS `arrival`,`tl`.`departure` AS `departure`,`tl`.`days` AS `days`,`tl`.`nights` AS `nights`,`mp`.`bb` AS `bb`,`mp`.`hb` AS `hb`,`mp`.`fb` AS `fb`,`mp`.`lunch` AS `lunch`,`mp`.`dinner` AS `dinner`,`mp`.`xtra_direct` AS `xtra_direct`,`v`.`remarks` AS `remarks`,`v`.`paid_by` AS `paid_by` from ((((((`voucher` `v` join `hotel` `h` on(`v`.`hotelId` = `h`.`hotelId`)) join `client` `c` on(`v`.`clientId` = `c`.`clientId`)) join `head_count` `hc` on(`v`.`countId` = `hc`.`countId`)) join `room_type` `rt` on(`v`.`roomId` = `rt`.`roomId`)) join `time_line` `tl` on(`v`.`unitId` = `tl`.`unitId`)) join `meal_plan` `mp` on(`v`.`planId` = `mp`.`planId`)) order by `v`.`voucherId` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `voucher_plus`
--

/*!50001 DROP TABLE IF EXISTS `voucher_plus`*/;
/*!50001 DROP VIEW IF EXISTS `voucher_plus`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`developer`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `voucher_plus` AS select `v`.`voucherId` AS `voucherId`,`h`.`hotelId` AS `hotelId`,`h`.`name` AS `hotel_name`,`h`.`branch` AS `hotel_branch`,`c`.`clientId` AS `clientId`,`c`.`name` AS `client_name`,`c`.`phone` AS `client_phone`,`hc`.`countId` AS `countId`,`hc`.`adults` AS `adults`,`hc`.`children` AS `children`,`hc`.`infants` AS `infants`,`hc`.`reservations` AS `reservations`,`rt`.`roomId` AS `roomId`,`rt`.`singles` AS `singles`,`rt`.`doubles` AS `doubles`,`rt`.`tripplets` AS `tripplets`,`tl`.`unitId` AS `unitId`,`tl`.`arrival` AS `arrival`,`tl`.`departure` AS `departure`,`tl`.`days` AS `days`,`tl`.`nights` AS `nights`,`mp`.`planId` AS `planId`,`mp`.`bb` AS `bb`,`mp`.`hb` AS `hb`,`mp`.`fb` AS `fb`,`mp`.`lunch` AS `lunch`,`mp`.`dinner` AS `dinner`,`mp`.`xtra_direct` AS `xtra_direct`,`v`.`remarks` AS `remarks`,`v`.`paid_by` AS `paid_by`,`conf`.`confirmId` AS `confirmId`,`conf`.`name` AS `confirm_person_name`,`conf`.`phone` AS `confirm_person_phone` from (((((((`voucher` `v` join `hotel` `h` on(`v`.`hotelId` = `h`.`hotelId`)) join `client` `c` on(`v`.`clientId` = `c`.`clientId`)) join `head_count` `hc` on(`v`.`countId` = `hc`.`countId`)) join `room_type` `rt` on(`v`.`roomId` = `rt`.`roomId`)) join `time_line` `tl` on(`v`.`unitId` = `tl`.`unitId`)) join `meal_plan` `mp` on(`v`.`planId` = `mp`.`planId`)) left join `confirmation` `conf` on(`v`.`confirmId` = `conf`.`confirmId`)) order by `v`.`voucherId` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-14 14:31:59
