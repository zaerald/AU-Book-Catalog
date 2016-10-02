<?php
	
	require "conn.php";
    
	$firstName = $_POST['firstName'];
	$lastName = $_POST['lastName'];
	$studentId = $_POST['studentId'];
	$username = $_POST['username'];
    $password = $_POST['password'];
	
	/*
	$firstName = "First";
	$lastName = "Last";
	$studentId = "01-2345-67890";
	$username = "user";
    $password = "password";
	*/
    $sql = "INSERT INTO tblstudentinfo VALUES('$studentId', '$username', '$password', '$firstName', '$lastName');";
			
	$result = mysqli_multi_query($conn, $sql);
    // $result = mysqli_query($conn, $sql);
    //$check = mysqli_fetch_array($result);

    //if(isset($check)) {
    //    echo 'success';
    //} else {
    //    echo 'fail';
    //}
	
	if ($result) {
		echo 'success';
	} else {
		echo 'fail';
	}
    
    mysqli_close($conn);
	
?>