<?php
	
   require "conn.php";
    
    $studentId = $_POST['studentId'];
    //$studentId = '01-1415-00736';

    $sql = "SELECT username, CONCAT(first_name, ' ', last_name) AS 'name' FROM tblstudentinfo WHERE student_id = '$studentId'";

    $res = mysqli_query($conn, $sql);
	$result = array();

    if($row = mysqli_fetch_array($res)) {
        array_push($result, array(
			'username'=>$row[0],
			'fullname'=>$row[1]
		));
		echo json_encode(array("result"=>$result));
    } else {
        echo 'fail';
    }
    
    mysqli_close($conn);
	
?>