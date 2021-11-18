DROP DATABASE  IF EXISTS `coreIt_ukol_db`;

CREATE DATABASE  IF NOT EXISTS `coreIt_ukol_db`;
USE `coreIt_ukol_db`;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addressee` varchar(150) DEFAULT NULL,
  `street` varchar(150) DEFAULT NULL,
  `building_number` int(20) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `locality` varchar(150) NOT NULL,
  `country_name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
CREATE TABLE `companies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ico` int(8) NOT NULL UNIQUE,
  `company_name` varchar(60) NOT NULL,
  `id_address` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (id_address) REFERENCES address(id)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=latin1;

--
-- Table structure for table `people`
--
DROP TABLE IF EXISTS `people`;
CREATE TABLE `people` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_company` int(11) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL UNIQUE,
  `first_name` varchar(60) DEFAULT NULL,
  `last_name` varchar(60) DEFAULT NULL,
  `last_update` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (id_company) REFERENCES companies(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

