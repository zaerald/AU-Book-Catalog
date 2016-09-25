<?php	
	require "conn.php";
	
	$bookId = $_POST['bookId'];
	
    mysqli_set_charset($conn, "utf8");
    $sql = "SELECT b.book_id, b.book_title, b.book_img,
GROUP_CONCAT(a.author_name SEPARATOR '\n') AS 'author',
s.subject, b.book_page, t.type, av.available, av.total, b.description
FROM tblbook b, tblauthor a, tblbook_author ba, tblsubject s, tbltype t, tblavailability av
WHERE b.book_id = ba.book_id AND a.book_author_id = ba.book_author AND b.subject_id = s.subject_id AND b.type_id = t.tbltype_id AND b.book_id = av.book_id 
AND b.book_id=$bookId
GROUP BY b.book_id";

    $res = mysqli_query($conn, $sql) or die("Error Selecting" . mysqli_error($conn));
	$result = array();
	
	while ($row=mysqli_fetch_assoc($res)) {
		$result['result'][] = $row;
	}
	
	echo json_encode($result);
	//json_encode( utf8_encode( $s ) );
    mysqli_close($conn);	
?>