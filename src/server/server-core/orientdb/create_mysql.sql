-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generato il: Mar 24, 2015 alle 17:05
-- Versione del server: 5.6.14
-- Versione PHP: 5.5.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `workinsocial`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `currency`
--

CREATE TABLE IF NOT EXISTS `currency` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(3) NOT NULL,
  `symbol` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `donation`
--

CREATE TABLE IF NOT EXISTS `donation` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `user_id` int(19) NOT NULL,
  `operation_id` int(19) NOT NULL,
  `amount` int(11) NOT NULL,
  `currency_id` int(19) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`user_id`),
  KEY `fk_operation_id` (`operation_id`),
  KEY `fk_currency_id` (`currency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `feedback`
--

CREATE TABLE IF NOT EXISTS `feedback` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(19) NOT NULL,
  `to_user_id` int(19) NOT NULL,
  `type` varchar(20) NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_from_user_id` (`from_user_id`),
  KEY `fk_to_user_id` (`to_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `job`
--

CREATE TABLE IF NOT EXISTS `job` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `user_id` int(19) NOT NULL,
  `operation_id` int(19) NOT NULL,
  `job_request_id` int(19) DEFAULT NULL,
  `job_response_id` int(19) DEFAULT NULL,
  `position_id` int(19) NOT NULL,
  `state_type` varchar(20) NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_job_user_id` (`user_id`),
  KEY `fk_job_operation_id` (`operation_id`),
  KEY `fk_job_job_request_id` (`job_request_id`),
  KEY `fk_job_job_response_id` (`job_response_id`),
  KEY `fk_job_position_id` (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `job_end`
--

CREATE TABLE IF NOT EXISTS `job_end` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `user_id` int(19) NOT NULL,
  `operation_id` int(19) NOT NULL,
  `job_id` int(19) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_job_end_user_id` (`user_id`),
  KEY `fk_job_end_operation_id` (`operation_id`),
  KEY `fk_job_end_job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `job_image`
--

CREATE TABLE IF NOT EXISTS `job_image` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `job_id` int(19) NOT NULL,
  `file` longblob NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_job_image_job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `job_request`
--

CREATE TABLE IF NOT EXISTS `job_request` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `user_id` int(19) NOT NULL,
  `operation_id` int(19) NOT NULL,
  `job_date` datetime NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_job_request_user_id` (`user_id`),
  KEY `fk_job_request_operation_id` (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `job_response`
--

CREATE TABLE IF NOT EXISTS `job_response` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `user_id` int(19) NOT NULL,
  `operation_id` int(19) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_job_response_operation_id` (`operation_id`),
  KEY `fk_job_response_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `job_start`
--

CREATE TABLE IF NOT EXISTS `job_start` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `user_id` int(19) NOT NULL,
  `operation_id` int(19) NOT NULL,
  `job_id` int(19) NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_job_start_job_id` (`job_id`),
  KEY `fk_job_start_operation_id` (`operation_id`),
  KEY `fk_job_start_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(19) NOT NULL,
  `to_user_id` int(19) NOT NULL,
  `message` text NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_message_to_user_id` (`to_user_id`),
  KEY `fk_message_from_user_id` (`from_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `news`
--

CREATE TABLE IF NOT EXISTS `news` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  `operation_id` int(19) NOT NULL,
  `title` varchar(250) NOT NULL,
  `description` text NOT NULL,
  `position_id` int(19) NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_news_position_id` (`position_id`),
  KEY `fk_news_operation_id` (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(20) NOT NULL,
  `user_id` int(19) DEFAULT NULL,
  `operation_id` int(19) DEFAULT NULL,
  `job_id` int(19) DEFAULT NULL,
  `job_request_id` int(19) DEFAULT NULL,
  `job_response_id` int(19) DEFAULT NULL,
  `feedback_id` int(19) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_notification_feedback_id` (`feedback_id`),
  KEY `fk_notification_job_id` (`job_id`),
  KEY `fk_notification_job_request_id` (`job_request_id`),
  KEY `fk_notification_job_response_id` (`job_response_id`),
  KEY `fk_notification_operation_id` (`operation_id`),
  KEY `fk_notification_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `operation`
--

CREATE TABLE IF NOT EXISTS `operation` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `user_id` int(19) NOT NULL,
  `position_id` int(19) NOT NULL,
  `title` int(100) NOT NULL,
  `description` varchar(250) NOT NULL,
  `expected_date` datetime DEFAULT NULL,
  `expected_duration` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `state_type` varchar(20) NOT NULL,
  `n_jobs_started` int(11) NOT NULL,
  `n_jobs_dropped` int(11) NOT NULL,
  `n_jobs_ended` int(11) NOT NULL,
  `n_jobs_completed` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_operation_position_id` (`position_id`),
  KEY `fk_operation_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `operation_image`
--

CREATE TABLE IF NOT EXISTS `operation_image` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `operation_id` int(19) NOT NULL,
  `file` blob NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_operation_image_operation_id` (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `position`
--

CREATE TABLE IF NOT EXISTS `position` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `session`
--

CREATE TABLE IF NOT EXISTS `session` (
  `id` varchar(64) NOT NULL,
  `user_id` int(19) NOT NULL,
  `expiry` tinyint(1) NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella ``
--

CREATE TABLE IF NOT EXISTS `session_history` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `user_id` int(19) NOT NULL,
  `session_id` varchar(64) NOT NULL,
  `address` varchar(16) NOT NULL,
  `headers` text DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `logout_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) DEFAULT NULL,
  `locked` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `user_account`
--

CREATE TABLE IF NOT EXISTS `user_account` (
  `user_id` int(19) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  `confirm_code` varchar(20) DEFAULT NULL,
  `recovery_id` int(19) DEFAULT NULL,
  `confirmed_date` datetime DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `user_bank`
--

CREATE TABLE IF NOT EXISTS `user_bank` (
  `user_id` int(19) NOT NULL,
  `owner_name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `iban` int(32) NOT NULL,
  `swift_bic` int(11) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `user_bill`
--

CREATE TABLE IF NOT EXISTS `user_bill` (
  `user_id` int(19) NOT NULL,
  `total_balance` int(11) NOT NULL,
  `donation_balance` int(11) NOT NULL,
  `job_balance` int(11) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `n_operations` int(11) NOT NULL,
  `n_donations` int(11) NOT NULL,
  `n_jobs` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `fk_user_bill_currency_id` (`currency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `user_recovery`
--

CREATE TABLE IF NOT EXISTS `user_recovery` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `user_id` int(19) NOT NULL,
  `temp_password` varchar(8) NOT NULL,
  `valid_date` datetime NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `user_settings`
--

CREATE TABLE IF NOT EXISTS `user_settings` (
  `user_id` int(19) NOT NULL,
  `show_position` tinyint(1) NOT NULL DEFAULT '1',
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `user_xaccount`
--

CREATE TABLE IF NOT EXISTS `user_xaccount` (
  `user_id` int(19) NOT NULL,
  `external_id` varchar(20) NOT NULL,
  `service_type` varchar(20) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `username` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `donation`
--
ALTER TABLE `donation`
ADD CONSTRAINT `fk_donation_currency_id` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
ADD CONSTRAINT `fk_donation_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`),
ADD CONSTRAINT `fk_donation_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `feedback`
--
ALTER TABLE `feedback`
ADD CONSTRAINT `fk_feedback_from_user_id` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`),
ADD CONSTRAINT `fk_feedback_to_user_id` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `job`
--
ALTER TABLE `job`
ADD CONSTRAINT `fk_job_job_request_id` FOREIGN KEY (`job_request_id`) REFERENCES `job_request` (`id`),
ADD CONSTRAINT `fk_job_job_response_id` FOREIGN KEY (`job_response_id`) REFERENCES `job_response` (`id`),
ADD CONSTRAINT `fk_job_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`),
ADD CONSTRAINT `fk_job_position_id` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`),
ADD CONSTRAINT `fk_job_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `job_end`
--
ALTER TABLE `job_end`
ADD CONSTRAINT `fk_job_end_job_id` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
ADD CONSTRAINT `fk_job_end_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`),
ADD CONSTRAINT `fk_job_end_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `job_image`
--
ALTER TABLE `job_image`
ADD CONSTRAINT `fk_job_image_job_id` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`);

--
-- Limiti per la tabella `job_request`
--
ALTER TABLE `job_request`
ADD CONSTRAINT `fk_job_request_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`),
ADD CONSTRAINT `fk_job_request_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `job_response`
--
ALTER TABLE `job_response`
ADD CONSTRAINT `fk_job_response_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`),
ADD CONSTRAINT `fk_job_response_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `job_start`
--
ALTER TABLE `job_start`
ADD CONSTRAINT `fk_job_start_job_id` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
ADD CONSTRAINT `fk_job_start_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`),
ADD CONSTRAINT `fk_job_start_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `message`
--
ALTER TABLE `message`
ADD CONSTRAINT `fk_message_from_user_id` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`),
ADD CONSTRAINT `fk_message_to_user_id` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `news`
--
ALTER TABLE `news`
ADD CONSTRAINT `fk_news_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`),
ADD CONSTRAINT `fk_news_position_id` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`);

--
-- Limiti per la tabella `notification`
--
ALTER TABLE `notification`
ADD CONSTRAINT `fk_notification_feedback_id` FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`id`),
ADD CONSTRAINT `fk_notification_job_id` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
ADD CONSTRAINT `fk_notification_job_request_id` FOREIGN KEY (`job_request_id`) REFERENCES `job_request` (`id`),
ADD CONSTRAINT `fk_notification_job_response_id` FOREIGN KEY (`job_response_id`) REFERENCES `job_response` (`id`),
ADD CONSTRAINT `fk_notification_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`),
ADD CONSTRAINT `fk_notification_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `operation`
--
ALTER TABLE `operation`
ADD CONSTRAINT `fk_operation_position_id` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`),
ADD CONSTRAINT `fk_operation_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `operation_image`
--
ALTER TABLE `operation_image`
ADD CONSTRAINT `fk_operation_image_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`);

--
-- Limiti per la tabella `session`
--
ALTER TABLE `session`
ADD CONSTRAINT `fk_user_session_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `user_account`
--
ALTER TABLE `user_account`
ADD CONSTRAINT `fk_account_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `user_bank`
--
ALTER TABLE `user_bank`
ADD CONSTRAINT `fk_bank_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `user_bill`
--
ALTER TABLE `user_bill`
ADD CONSTRAINT `fk_user_bill_currency_id` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
ADD CONSTRAINT `fk_bill_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `user_recovery`
--
ALTER TABLE `user_recovery`
ADD CONSTRAINT `fk_recovery_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `user_settings`
--
ALTER TABLE `user_settings`
ADD CONSTRAINT `fk_settings_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `user_xaccount`
--
ALTER TABLE `user_xaccount`
ADD CONSTRAINT `fk_xaccount_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
