<?php
	require "conn.php";
	mysqli_set_charset($conn, "utf8");

	$studId = $_POST['studentId'];
	$bookId = $_POST['bookId'];
	
	//$studId = "01-1415-00736";
	
	
	$sql = "SELECT s.student_id FROM tblstudentinfo s, tblbook b, tblbook_favorites bf WHERE bf.student_id = s.student_id AND bf.book_id = b.book_id AND s.student_id = '$studId' AND b.book_id = $bookId";

	$res = mysqli_query($conn, $sql) or die("Error Selecting" . mysqli_error($conn));

	if ($res) {
		if (mysqli_num_rows($res) > 0) {
			echo "success";
		} else {
			echo "none";
		}
	} else {
		echo "fail";
	}
	mysqli_close($conn);
?>