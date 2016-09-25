<?php
	define('HOST', 'localhost');
    define('USER', 'root');
    define('PASS', '');
    define('DB', 'dbaubookcatalog');

    $conn = mysqli_connect(HOST, USER, PASS, DB);
	//or die("Error" . mysqli_error($conn));
?>