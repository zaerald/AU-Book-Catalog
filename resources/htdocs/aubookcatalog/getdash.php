<?php	
	require "conn.php";
	
    mysqli_set_charset($conn, "utf8");
    $sql = "SELECT dash_img FROM tbldashboard";

    $res = mysqli_query($conn, $sql) or die("Error Selecting" . mysqli_error($conn));
	$result = array();
	
	while ($row=mysqli_fetch_assoc($res)) {
		$result['result'][] = $row;
	}
	
	echo json_encode($result);
    mysqli_close($conn);	
?>