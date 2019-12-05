-- --------------------------------------------------------
-- Host:                         192.168.11.250
-- Server version:               5.6.20-enterprise-commercial-advanced-log - MySQL Enterprise Server - Advanced Edition (Commercial)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for fixit
CREATE DATABASE IF NOT EXISTS `fixit` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fixit`;


-- Dumping structure for table fixit.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_email` varchar(255) DEFAULT '0',
  `admin_password` varchar(255) DEFAULT '0',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table fixit.cat_type
CREATE TABLE IF NOT EXISTS `cat_type` (
  `cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(150) DEFAULT NULL,
  `parent_id` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Category tpe table';

-- Data exporting was unselected.


-- Dumping structure for table fixit.fixer_accounting
CREATE TABLE IF NOT EXISTS `fixer_accounting` (
  `fixer_acc_id` int(11) NOT NULL AUTO_INCREMENT,
  `fixer_id` int(11) DEFAULT NULL,
  `query_id` int(11) DEFAULT NULL,
  `amount_paid` float DEFAULT NULL,
  PRIMARY KEY (`fixer_acc_id`),
  KEY `fixer_id_fk_idx` (`fixer_id`),
  KEY `query_id_fk_idx` (`query_id`),
  CONSTRAINT `acc_fixer_id_fk` FOREIGN KEY (`fixer_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `acc_query_id_fk` FOREIGN KEY (`query_id`) REFERENCES `query` (`query_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table fixit.industry_type
CREATE TABLE IF NOT EXISTS `industry_type` (
  `indst_id` int(11) NOT NULL AUTO_INCREMENT,
  `indst_name` varchar(150) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`indst_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table fixit.query
CREATE TABLE IF NOT EXISTS `query` (
  `query_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `fixer_id` int(11) DEFAULT NULL,
  `query_title` text,
  `query_content` text,
  `current_status` enum('O','W','R','C','D','UN') DEFAULT NULL COMMENT 'O-Open,W-Working,R-Review,C-closed,D-Delete,UN-Unresolved NotFixed',
  `hashcode` text,
  `date_raised` datetime DEFAULT NULL,
  `date_accepted` datetime DEFAULT NULL,
  `last_update_by_user` datetime DEFAULT NULL,
  `last_update_by_fixer` datetime DEFAULT NULL,
  `closure_date` datetime DEFAULT NULL,
  `old_query_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`query_id`),
  KEY `customer_id_fk_idx` (`customer_id`),
  CONSTRAINT `customer_id_fk` FOREIGN KEY (`customer_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--alter 1 : ALTER TABLE `fixit`.`query` CHANGE COLUMN `current_status` `current_status` ENUM('O','W','R','C','D','UN','UI') NULL DEFAULT NULL COMMENT 'O-Open,W-Working,R-Review,C-closed,D-Delete,UN-Unresolved NotFixed' ;

-- Data exporting was unselected.


-- Dumping structure for table fixit.query_audit
CREATE TABLE IF NOT EXISTS `query_audit` (
  `audit_id` int(11) NOT NULL AUTO_INCREMENT,
  `query_id` int(11) DEFAULT '0',
  `customer_id` int(11) DEFAULT '0',
  `fixer_id` int(11) DEFAULT '0',
  `message` text,
  `msg_from` enum('C','F','A') DEFAULT NULL COMMENT 'C-Customer,F-Fixer,A-Admin',
  `status` enum('O','W','R','C','UI','UN','FR') DEFAULT NULL,
  `audit_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`audit_id`),
  KEY `audit_query_id_fk` (`query_id`),
  CONSTRAINT `audit_query_id_fk` FOREIGN KEY (`query_id`) REFERENCES `query` (`query_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

   
  -- Alter 1:-alter table query_audit modify column status enum ('O','W','R','C','UI','UN','FR','WD','WL');
   -- Alter 2:-ALTER TABLE `fixit`.`query_audit` CHANGE COLUMN `status` `status` ENUM('O','W','R','C','UI','UN','FR','WD','WL','IR') NULL DEFAULT NULL ;
-- Data exporting was unselected.


-- Dumping structure for table fixit.query_cat
CREATE TABLE IF NOT EXISTS `query_cat` (
  `query_cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `query_id` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`query_cat_id`),
  KEY `query_id_fk_idx` (`query_id`),
  KEY `cat_id_fk_idx` (`cat_id`),
  CONSTRAINT `cat_id_fk` FOREIGN KEY (`cat_id`) REFERENCES `cat_type` (`cat_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `query_id_fk` FOREIGN KEY (`query_id`) REFERENCES `query` (`query_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='query category table';

-- Data exporting was unselected.


-- Dumping structure for table fixit.query_expire
CREATE TABLE IF NOT EXISTS `query_expire` (
  `query_expire_id` int(11) NOT NULL,
  `query_id` int(11) DEFAULT NULL,
  `member_id` int(11) NOT NULL,
  `fixer_id` int(11) NOT NULL,
  `hashcode` text,
  `expires_on` datetime DEFAULT NULL,
  `internal_url` text,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`query_expire_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Alter 1:-ALTER TABLE `fixit`.`query_expire` 
CHANGE COLUMN `query_expire_id` `query_expire_id` INT(11) NOT NULL AUTO_INCREMENT ;

-- Data exporting was unselected.


-- Dumping structure for table fixit.query_files
CREATE TABLE IF NOT EXISTS `query_files` (
  `query_file_id` int(11) NOT NULL AUTO_INCREMENT,
  `query_id` int(11) DEFAULT NULL,
  `file_name` text,
  `file_type` enum('D','L') DEFAULT NULL COMMENT 'D-document,L-LInk',
  `file_url` text,
  PRIMARY KEY (`query_file_id`),
  KEY `files_query_id_fk_idx` (`query_id`),
  CONSTRAINT `files_query_id_fk` FOREIGN KEY (`query_id`) REFERENCES `query` (`query_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Query files table';

--alter 1 : alter table query_files add created_at datetime ;

-- Data exporting was unselected.


-- Dumping structure for table fixit.testimonial
CREATE TABLE IF NOT EXISTS `testimonial` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`t_id`),
  KEY `user_id_fk_idx` (`user_id`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table fixit.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(150) NOT NULL,
  `user_type` enum('F','C','A') NOT NULL COMMENT 'F stands for fixer and  C stand for customer A stands for Admin',
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `linkedin_profile` varchar(255) DEFAULT NULL,
  `overview` text,
  `company_name` varchar(255) DEFAULT NULL,
  `job_title` varchar(100) DEFAULT NULL,
  `address` text,
  `city` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `zip` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  `alert_type` enum('E','T') DEFAULT NULL COMMENT 'E stands for email T stands for Text',
  `profile_photo` text,
  `fixer_status` enum('A','D','R') DEFAULT NULL COMMENT 'A standsfor activate D stands for deactivate R stands for review',
  `subscription_payment` enum('Y','N') NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`,`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Alter 1:-ALTER TABLE `fixit`.`user` ADD COLUMN `last_login` DATETIME NULL DEFAULT NULL AFTER `subscription_payment`;
-- Data exporting was unselected.
-- Alter 2:- alter table user add profile_icon text;

-- Dumping structure for table fixit.users_accounting
CREATE TABLE IF NOT EXISTS `users_accounting` (
  `user_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `type` enum('S','T') DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_account_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_acc_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table fixit.user_cat
CREATE TABLE IF NOT EXISTS `user_cat` (
  `user_cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_cat_id`),
  KEY `fixer_cat_userid_idx` (`user_id`),
  KEY `fixer_cat_catid_idx` (`cat_id`),
  CONSTRAINT `fixer_cat_catid` FOREIGN KEY (`cat_id`) REFERENCES `cat_type` (`cat_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fixer_cat_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='fixer category id';

-- Data exporting was unselected.


-- Dumping structure for table fixit.user_credit
CREATE TABLE IF NOT EXISTS `user_credit` (
  `user_credit_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `points` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_credit_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table fixit.user_decline
CREATE TABLE IF NOT EXISTS `user_decline` (
  `decline_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `query_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`decline_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table fixit.user_indst
CREATE TABLE IF NOT EXISTS `user_indst` (
  `user_indst_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `indst_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_indst_id`),
  KEY `user_id` (`user_id`),
  KEY `indst_id` (`indst_id`),
  CONSTRAINT `user_indst_indstId` FOREIGN KEY (`indst_id`) REFERENCES `industry_type` (`indst_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_indst_userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

-- new table favourite_fixer
CREATE TABLE `fixit`.`favourite_fixer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT NOT NULL,
  `fixer_id` INT NOT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));
  
  -- new table fixer_rating
  CREATE TABLE `fixit`.`fixer_rating` (
  `fixer_rating_id` INT NOT NULL AUTO_INCREMENT,
  `query_id` INT NOT NULL,
  `query_status` VARCHAR(45) NULL,
  `star_rating` ENUM('1','2','3','4','5') NULL DEFAULT '5',
  `created_at` DATETIME NULL,
  PRIMARY KEY (`fixer_rating_id`));
  
  --alter 1:-ALTER TABLE `fixit`.`fixer_rating` ADD COLUMN `fixer_id` INT NOT NULL AFTER `query_id` 
  
