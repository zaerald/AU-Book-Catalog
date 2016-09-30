<?php
	require "conn.php";
	mysqli_set_charset($conn, "utf8");

	$sql = "SELECT b.book_id, b.book_img, b.book_title, t.type FROM tblbook b, tbltype t WHERE t.tbltype_id = b.type_id GROUP BY b.book_id";

	$res = mysqli_query($conn, $sql) or die("Error Selecting" . mysqli_error($conn));
	$result = array();

	if (!$res) {
		echo "MySQL Error: " . mysqli_error();
		exit;
	}

	while ($row=mysqli_fetch_assoc($res)) {
		$result['result'][] = $row;
	}
	echo json_encode($result);
	mysqli_close($conn);
	
	/*
    while($row = mysqli_fetch_array($res)) {
        array_push($result, array(
			'bookImage'=>$row[0],
			'title'=>$row[1],
			'type'=>$row[2]
		));
    }
	*/
?>