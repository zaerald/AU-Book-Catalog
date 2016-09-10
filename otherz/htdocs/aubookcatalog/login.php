<?php
	require "conn.php";
	
	$user_name = $_POST["userName"];
	$user_pass = $_POST["password"];
	//$user_name = "admin";
	//$user_pass = "admin";
	$mysql_qry = "SELECT * FROM tbllogin WHERE username = '$user_name' AND password = '$user_name';";
	$result = mysqli_query($conn, $mysql_qry);
	
	if ($result) {
		echo "true";
	} else {
		echo "false";
	}
	
	mysqli_close($conn);
?>