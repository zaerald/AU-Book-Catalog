-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 24, 2016 at 05:01 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbaubookcatalog`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblauthor`
--

CREATE TABLE `tblauthor` (
  `book_author_id` int(5) NOT NULL,
  `author_name` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblauthor`
--

INSERT INTO `tblauthor` (`book_author_id`, `author_name`) VALUES
(1, 'Terry Landau'),
(2, 'David Berlinski'),
(3, 'Mitch Albom'),
(4, 'Susan Rose-Ackerman'),
(5, 'Marc de Smedt'),
(7, 'Patricia Empleo'),
(8, 'Pedro P. Guerrero'),
(9, 'Lucia Herrera'),
(10, 'Isabelita O''dell'),
(11, 'Ma. Luisa V. Tesorio'),
(12, 'Bertram G. Katzung'),
(13, 'Sheldon B. Liss');

-- --------------------------------------------------------

--
-- Table structure for table `tblbook`
--

CREATE TABLE `tblbook` (
  `book_id` int(5) NOT NULL,
  `book_name` varchar(200) NOT NULL,
  `book_page` int(5) NOT NULL,
  `book_category_id` int(3) NOT NULL,
  `type` varchar(5) NOT NULL,
  `book_img` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblbook`
--

INSERT INTO `tblbook` (`book_id`, `book_name`, `book_page`, `book_category_id`, `type`, `book_img`) VALUES
(1, 'About Faces', 294, 1, 'Book', 'about_faces.jpg'),
(2, 'Have a little faith', 260, 3, 'Book', 'have_a_little_faith.jpg'),
(3, 'Newton''s Gift', 217, 2, 'Book', 'newtons_gift.jpg'),
(4, 'Corruption and Government', 266, 4, 'Book', 'corruption_and_government.jpg'),
(5, 'The Wisdom of Zen', 44, 5, 'Book', 'the_wisdom_of_zen.jpg'),
(6, 'Practical Auditing', 284, 6, 'Book', 'practical_auditing.jpg'),
(7, 'Cost Accounting', 194, 6, 'Book', 'cost_accounting.jpg'),
(8, 'Elementary Algebra', 462, 7, 'Book', 'elementary_algebra.jpg'),
(9, 'Basic and Clinical Pharmacology', 1046, 8, 'Book', 'basic_and_clinical_pharmacology.jpg'),
(10, 'Roots of Revolution', 269, 9, 'Book', 'roots_of_revolution.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `tblbook_author`
--

CREATE TABLE `tblbook_author` (
  `book_id` int(5) NOT NULL,
  `book_author` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblbook_author`
--

INSERT INTO `tblbook_author` (`book_id`, `book_author`) VALUES
(1, 1),
(2, 3),
(3, 2),
(4, 4),
(5, 5),
(6, 7),
(7, 8),
(8, 9),
(8, 10),
(8, 11),
(9, 12),
(10, 13);

-- --------------------------------------------------------

--
-- Table structure for table `tblbook_favorites`
--

CREATE TABLE `tblbook_favorites` (
  `book_id` int(5) NOT NULL,
  `student_id` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tblbook_subject`
--

CREATE TABLE `tblbook_subject` (
  `book_id` int(5) NOT NULL,
  `subject_id` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblbook_subject`
--

INSERT INTO `tblbook_subject` (`book_id`, `subject_id`) VALUES
(1, 1),
(1, 2),
(3, 7),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(4, 8),
(4, 19),
(5, 9),
(7, 11),
(6, 10),
(8, 12),
(9, 13),
(10, 14),
(10, 15),
(10, 16),
(10, 17),
(10, 18);

-- --------------------------------------------------------

--
-- Table structure for table `tblcategory`
--

CREATE TABLE `tblcategory` (
  `book_category_id` int(3) NOT NULL,
  `category` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblcategory`
--

INSERT INTO `tblcategory` (`book_category_id`, `category`) VALUES
(1, 'Psychology'),
(2, 'Physics'),
(3, 'Non Fiction'),
(4, 'Politics and Government'),
(5, 'Religion'),
(6, 'Accounting'),
(7, 'Mathematics'),
(8, 'Pharmacology'),
(9, 'History');

-- --------------------------------------------------------

--
-- Table structure for table `tbllogin`
--

CREATE TABLE `tbllogin` (
  `student_id` varchar(15) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbllogin`
--

INSERT INTO `tbllogin` (`student_id`, `username`, `password`) VALUES
('01-1415-00736', 'admin', 'admin'),
('01-1415-01141', 'Kuuderex', 'password'),
('01-1415-03422', 'velascoaaron', 'vvvvv'),
('16-4643-95420', 'kimat', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `tblpdf`
--

CREATE TABLE `tblpdf` (
  `pdf_id` int(5) NOT NULL,
  `pdf` longblob NOT NULL,
  `book_id` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblpdf`
--

INSERT INTO `tblpdf` (`pdf_id`, `pdf`, `book_id`) VALUES
(1, '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tblstudentinfo`
--

CREATE TABLE `tblstudentinfo` (
  `student_id` varchar(15) NOT NULL,
  `first_name` mediumtext NOT NULL,
  `last_name` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblstudentinfo`
--

INSERT INTO `tblstudentinfo` (`student_id`, `first_name`, `last_name`) VALUES
('01-1415-00736', 'Zaerald Denze', 'Lungos'),
('01-1415-01141', 'Kyouma', 'Hououin'),
('01-1415-03422', 'Aaron', 'Velasco'),
('16-4643-95420', 'Kim', 'atienza');

-- --------------------------------------------------------

--
-- Table structure for table `tblsubject`
--

CREATE TABLE `tblsubject` (
  `subject_id` int(3) NOT NULL,
  `subject` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblsubject`
--

INSERT INTO `tblsubject` (`subject_id`, `subject`) VALUES
(1, 'Face-Miscellanea'),
(2, 'Physiognomy'),
(3, 'Newton'),
(4, 'Isaac'),
(5, 'Physics'),
(6, 'History'),
(7, 'General Books'),
(8, 'Political Corruption'),
(9, 'Zen Buddhism'),
(10, 'Accounting Problems'),
(11, 'Cost Accounting'),
(12, 'Algebra'),
(13, 'Pharmacology'),
(14, 'Cuba'),
(15, 'Politics and government'),
(16, 'Philosophy'),
(17, 'Radicalism'),
(18, 'Intellectual life'),
(19, 'Economic aspects');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblauthor`
--
ALTER TABLE `tblauthor`
  ADD PRIMARY KEY (`book_author_id`);

--
-- Indexes for table `tblbook`
--
ALTER TABLE `tblbook`
  ADD PRIMARY KEY (`book_id`);

--
-- Indexes for table `tblcategory`
--
ALTER TABLE `tblcategory`
  ADD PRIMARY KEY (`book_category_id`);

--
-- Indexes for table `tbllogin`
--
ALTER TABLE `tbllogin`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `tblpdf`
--
ALTER TABLE `tblpdf`
  ADD PRIMARY KEY (`pdf_id`);

--
-- Indexes for table `tblstudentinfo`
--
ALTER TABLE `tblstudentinfo`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `id` (`student_id`);

--
-- Indexes for table `tblsubject`
--
ALTER TABLE `tblsubject`
  ADD PRIMARY KEY (`subject_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblauthor`
--
ALTER TABLE `tblauthor`
  MODIFY `book_author_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `tblbook`
--
ALTER TABLE `tblbook`
  MODIFY `book_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `tblcategory`
--
ALTER TABLE `tblcategory`
  MODIFY `book_category_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tblpdf`
--
ALTER TABLE `tblpdf`
  MODIFY `pdf_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tblsubject`
--
ALTER TABLE `tblsubject`
  MODIFY `subject_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
