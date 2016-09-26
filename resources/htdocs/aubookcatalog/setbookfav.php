<?php
	require "conn.php";
	mysqli_set_charset($conn, "utf8");

	$studId = $_POST['studentId'];
	$bookId = $_POST['bookId'];
	$isFavorite = $_POST['fav'];
	
	//$studId = "01-1415-00736";
	//$bookId = 1;
	//$isFavorite = True;
	
	$sql;
	if ($isFavorite == "true") {
		$sql = "DELETE FROM tblbook_favorites WHERE student_id='$studId' AND book_id=$bookId";
	} else {
		$sql = "INSERT INTO tblbook_favorites VALUES('$studId', $bookId)";
	}

	$res = mysqli_query($conn, $sql) or die("Error Selecting" . mysqli_error($conn));

	if ($res) {
		echo "success";
	} else {
		echo "fail";
	}
	mysqli_close($conn);
?>