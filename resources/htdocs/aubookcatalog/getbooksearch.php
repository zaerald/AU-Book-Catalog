<?php
	require "conn.php";
	mysqli_set_charset($conn, "utf8");

	$keyword = $_POST['keyword'];
	//$keyword = 'rr';
	
	$sql = "SELECT b.book_id, b.book_img, b.book_title, authorz.author, t.type

FROM tblbook AS b

INNER JOIN tbltype AS t
ON t.tbltype_id = b.type_id

INNER JOIN

(SELECT b.book_id, GROUP_CONCAT(a.author_name SEPARATOR ', ') AS 'author'

FROM tblbook AS b

INNER JOIN tblbook_author AS ba
ON ba.book_id = b.book_id

INNER JOIN tblauthor AS a
ON ba.book_author = a.book_author_id

GROUP BY b.book_id) AS authorz

ON b.book_id = authorz.book_id

WHERE b.book_title LIKE '%$keyword%' OR authorz.author LIKE '%$keyword%'";

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