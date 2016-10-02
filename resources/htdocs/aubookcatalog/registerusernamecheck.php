<?php
	
	require "conn.php";
	
	$username = $_POST['username'];
	//$username = "z";

    $sql = "SELECT username FROM tblstudentinfo WHERE username='$username';";
			
	$result = mysqli_query($conn, $sql);
	
	if ($result) {
		$username_fetch = mysqli_fetch_assoc($result);
		
		if ($username_fetch["username"] == $username) {
			echo 'duplicate';
		} else {
			echo 'success';
		}
	} else {
		echo 'fail';
	}
    
    mysqli_close($conn);
	
?>