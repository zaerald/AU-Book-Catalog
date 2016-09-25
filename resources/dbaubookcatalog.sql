-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 26, 2016 at 12:14 AM
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
-- Table structure for table `tblavailability`
--

CREATE TABLE `tblavailability` (
  `book_id` int(5) NOT NULL,
  `available` int(3) NOT NULL,
  `total` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblavailability`
--

INSERT INTO `tblavailability` (`book_id`, `available`, `total`) VALUES
(1, 5, 5),
(2, 10, 10),
(3, 3, 3),
(4, 4, 4),
(5, 6, 7),
(6, 8, 9),
(7, 2, 4),
(8, 5, 7),
(9, 1, 6),
(10, 4, 4);

-- --------------------------------------------------------

--
-- Table structure for table `tblbook`
--

CREATE TABLE `tblbook` (
  `book_id` int(5) NOT NULL,
  `book_title` varchar(200) NOT NULL,
  `book_page` int(5) NOT NULL,
  `subject_id` int(3) NOT NULL,
  `type_id` int(1) NOT NULL,
  `book_img` mediumtext NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblbook`
--

INSERT INTO `tblbook` (`book_id`, `book_title`, `book_page`, `subject_id`, `type_id`, `book_img`, `description`) VALUES
(1, 'About Faces', 294, 1, 1, 'about_faces.jpg', 'The human face - it tells more about us than any other part of the body. At rest and in motion, in silence and in speech, the face sends a multitude of signals that convey our messages. Our face is our identity, and our means of recognizing others. Each pattern of facial features is truly individual.'),
(2, 'Have a little faith', 260, 2, 1, 'have_a_little_faith.jpg', 'What if our beliefs were not what divided us, but what pulled us together In Have a Little Faith, Mitch Albom offers a beautifully written story of a remarkable eight-year journey between two worlds--two men, two faiths, two communities--that will inspire readers everywhere. Albom''s first nonfiction book since Tuesdays with Morrie, Have a Little Faith begins with an unusual request: an eighty-two-year-old rabbi from Albom''s old hometown asks him to deliver his eulogy. Feeling unworthy, Albom insists on understanding the man better, which throws him back into a world of faith he''d left years ago. Meanwhile, closer to his current home, Albom becomes involved with a Detroit pastor--a reformed drug dealer and convict--who preaches to the poor and homeless in a decaying church with a hole in its roof. Moving between their worlds, Christian and Jewish, African-American and white, impoverished and well-to-do, Albom observes how these very different men employ faith similarly in fighting for survival: the older, suburban rabbi embracing it as death approaches; the younger, inner-city pastor relying on it to keep himself and his church afloat. As America struggles with hard times and people turn more to their beliefs, Albom and the two men of God explore issues that perplex modern man: how to endure when difficult things happen; what heaven is; intermarriage; forgiveness; doubting God; and the importance of faith in trying times. Although the texts, prayers, and histories are different, Albom begins to recognize a striking unity between the two worlds--and indeed, between beliefs everywhere. In the end, as the rabbi nears death and a harsh winter threatens the pastor''s wobbly church, Albom sadly fulfills the rabbi''s last request and writes the eulogy. And he finally understands what both men had been teaching all along: the profound comfort of believing in something bigger than yourself. Have a Little Faith is a book about a life''s purpose; about losing belief and finding it again; about the divine spark inside us all. It is one man''s journey, but it is everyone''s story. Ten percent of the profits from this book will go to charity, including The Hole In The Roof Foundation, which helps refurbish places of worship that aid the homeless.'),
(3, 'Newton''s Gift', 217, 3, 1, 'newtons_gift.jpg', 'Sir Isaac Newton, creator of the first and perhaps most important scientific theory, is a giant of the scientific era. Despite this, he has remained inaccessible to most modern readers, indisputably great but undeniably remote. \r\nIn this witty, engaging, and often moving examination of Newton''s life, David Berlinski recovers the man behind the mathematical breakthroughs. The story carries the reader from Newton''s unremarkable childhood to his awkward undergraduate days at Cambridge through the astonishing year in which, working alone, he laid the foundation for his system of the world, his Principia Mathematica, and to the subsequent monumental feuds that poisoned his soul and wearied his supporters. \r\nAn edifying appreciation of Newton''s greatest accomplishment, Newton''s Gift is also a touching celebration of a transcendent man.'),
(4, 'Corruption and Government', 266, 4, 1, 'corruption_and_government.jpg', 'This book suggests how high levels of corruption limit investment and growth can lead to ineffective government. Developing countries and those making a transition from socialism are particularly at risk, but corruption is a worldwide phenomenon. Corruption creates economic inefficiencies and inequities, but reforms are possible to reduce the material benefits from payoffs. Corruption is not just an economic problem, however; it is also intertwined with politics. Reform may require changes in both constitutional structures and the underlying relationship of the market and the state. Effective reform cannot occur unless both the international community and domestic political leaders support change. No single ''blueprint'' is possible, but the primary goal should be to reduce the gains from paying and receiving bribes, not simply to remove ''bad apples''.'),
(5, 'The Wisdom of Zen', 44, 5, 1, 'the_wisdom_of_zen.jpg', '"If in every mind burns a flame of the Buddha''s Enlightenment," Christmas Humphreys writes in his foreword to The Wisdom of the Zen Masters, "there is nothing to seek and nothing to acquire. We are enlightened, and all the words in the world will not give us what we already have. The man of Zen, therefore, is concerned with one thing only, to become aware of what he already is…" The task of the Japanese Zen master has been to guide his pupils in their awakening. The means used vary––from severe physical discipline to the proposition of enigmatic riddles, or koans––but always to the same end, Enlightenment: experiencing the Great Death of the worldly "I."'),
(6, 'Practical Auditing', 284, 6, 1, 'practical_auditing.jpg', 'All the literature on auditing published heretofore presupposes familiarity with the rules of practice on the part of the student; hence the information thus far obtainable upon this subject is of too indefinite a character to be understood easily by that large class of accountants who are otherwise excellently equipped for auditing, but who are lacking in experience.\r\n\r\nThe purpose of this manual is, therefore, to set forth minutely the details to be pursued in making a commercial audit, and to indicate in proper order the procedure to be followed.\r\n\r\nWith this brief explanation the book is respectfully submitted to ambitious accountants.'),
(7, 'Cost Accounting', 194, 6, 1, 'cost_accounting.jpg', 'This book is the sixth of seven books which introduces the basic principles of accounting. This book introduces managerial accounting, with a primary focus on internal business reporting, decision making, planning, strategy, budgets, and cost control. Cost-volume-profit analysis, variable cost, fixed costs, mixed costs are introduced. Break-even analysis, contributions margin, target income calculations, and sensitivity analysis are all discussed in detail. In addition, product costs, job costing, process costing, and activity-based costing are introduced.'),
(8, 'Elementary Algebra', 462, 7, 1, 'elementary_algebra.jpg', 'It is essential to lay a solid foundation in mathematics if a student is to be competitive in today’s global market. The importance of algebra, in particular, cannot be overstated, as it is the basis of all mathematical modeling used in applications found in all disciplines. Traditionally, the study of algebra is separated into a two parts, elementary algebra and intermediate algebra. This textbook, Elementary Algebra, is the first part, written in a clear and concise manner, making no assumption of prior algebra experience. It carefully guides students from the basics to the more advanced techniques required to be successful in the next course.'),
(9, 'Basic and Clinical Pharmacology', 1046, 8, 1, 'basic_and_clinical_pharmacology.jpg', 'The twelfth edition of Basic & Clinical Pharmacology continues the important changes inaugurated in the eleventh edition, with extensive use of full-color illustrations and expanded coverage of transporters, pharmacogenomics, and new drugs. Case studies have been added to several chapters and answers to questions posed in the case studies now appear at the end of each chapter. As in prior editions, the book is designed to provide a comprehensive, authoritative, and readable pharmacology textbook for students in the health sciences. Frequent revision is necessary to keep pace with the rapid changes in pharmacology and therapeutics; the 2–3 year revision cycle of the printed text is among the shortest in the field and the availability of an online version provides even greater currency. In addition to the full-color illustrations, other new features have been introduced. The Case Study Answer section at the end of chapters will make the learning process even more interesting and efficient. The book also offers special features that make it a useful reference for house officers and practicing clinicians.\r\n\r\nInformation is organized according to the sequence used in many pharmacology courses and in integrated curricula: basic principles; autonomic drugs; cardiovascular-renal drugs; drugs with important actions on smooth muscle; central nervous system drugs; drugs used to treat inflammation, gout, and diseases of the blood; endocrine drugs; chemotherapeutic drugs; toxicology; and special topics. This sequence builds new information on a foundation of information already assimilated. For example, early presentation of autonomic nervous system pharmacology allows students to integrate the physiology and neuroscience they have learned elsewhere with the pharmacology they are learning and prepares them to understand the autonomic effects of other drugs. This is especially important for the cardiovascular and central nervous system drug groups. However, chapters can be used equally well in courses and curricula that present these topics in a different sequence.'),
(10, 'Roots of Revolution', 269, 9, 1, 'roots_of_revolution.jpg', 'A history of Iran focuses on the Shah''s rise and fall and the causes of the Iranian revolution.\r\n\r\nIn this updated edition of Nikki Keddie’s Modern Iran—itself a substantially revised and expanded version of her classic work Roots of Revolution—the  author provides a new preface and a fully annotated and indexed epilogue, reviewing recent developments in Iran since 2003. Keddie provides insightful commentary on Iran’s nuclear and foreign policy, its relations with the United Nations and the United States, increasing conservative and hard-line tendencies in the government, and recent developments in the economy, cultural and intellectual life, and human rights.');

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
-- Table structure for table `tbldashboard`
--

CREATE TABLE `tbldashboard` (
  `dashboard_id` int(3) NOT NULL,
  `dash_img` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbldashboard`
--

INSERT INTO `tbldashboard` (`dashboard_id`, `dash_img`) VALUES
(1, 'dash1.png'),
(2, 'dash2.png'),
(3, 'dash3.png'),
(4, 'dash4.png'),
(5, 'dash5.png'),
(6, 'dash6.png'),
(7, 'dash7.png'),
(8, 'dash8.png'),
(9, 'dash9.png'),
(10, 'dash10.png'),
(11, 'dash11.png'),
(12, 'dash12.png'),
(13, 'dash13.png');

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
('01-1415-00736', 'admin', 'admin');

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
('01-1415-00736', 'Zaerald Denze', 'Lungos');

-- --------------------------------------------------------

--
-- Table structure for table `tblsubject`
--

CREATE TABLE `tblsubject` (
  `subject_id` int(3) NOT NULL,
  `subject` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblsubject`
--

INSERT INTO `tblsubject` (`subject_id`, `subject`) VALUES
(1, 'Physiognomy'),
(2, 'General Books'),
(3, 'Physics'),
(4, 'Political Corruption'),
(5, 'Zen Buddhism'),
(6, 'Accounting'),
(7, 'Algebra'),
(8, 'Pharmacology'),
(9, 'Philosophy');

-- --------------------------------------------------------

--
-- Table structure for table `tbltype`
--

CREATE TABLE `tbltype` (
  `tbltype_id` int(1) NOT NULL,
  `type` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbltype`
--

INSERT INTO `tbltype` (`tbltype_id`, `type`) VALUES
(1, 'Book'),
(2, 'PDF');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblauthor`
--
ALTER TABLE `tblauthor`
  ADD PRIMARY KEY (`book_author_id`);

--
-- Indexes for table `tblavailability`
--
ALTER TABLE `tblavailability`
  ADD UNIQUE KEY `book_id` (`book_id`);

--
-- Indexes for table `tblbook`
--
ALTER TABLE `tblbook`
  ADD PRIMARY KEY (`book_id`);

--
-- Indexes for table `tbldashboard`
--
ALTER TABLE `tbldashboard`
  ADD PRIMARY KEY (`dashboard_id`);

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
-- Indexes for table `tbltype`
--
ALTER TABLE `tbltype`
  ADD PRIMARY KEY (`tbltype_id`);

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
-- AUTO_INCREMENT for table `tbldashboard`
--
ALTER TABLE `tbldashboard`
  MODIFY `dashboard_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `tblpdf`
--
ALTER TABLE `tblpdf`
  MODIFY `pdf_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tblsubject`
--
ALTER TABLE `tblsubject`
  MODIFY `subject_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tbltype`
--
ALTER TABLE `tbltype`
  MODIFY `tbltype_id` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
