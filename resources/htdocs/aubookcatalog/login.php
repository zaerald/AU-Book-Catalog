<?php
	
   require "conn.php";
    
    $username = $_POST['username'];
    $password = $_POST['password'];

    $sql = "SELECT student_id FROM tbllogin WHERE username='$username' AND password='$password';";

	$res = mysqli_query($conn, $sql);
	$result = array();

	if($row = mysqli_fetch_array($res)) {
        array_push($result, array('student_id'=>$row[0]));
		echo json_encode(array("result"=>$result));
    } else {
        echo 'fail';
    }
    
    mysqli_close($conn);
?>