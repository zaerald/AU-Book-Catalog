<?php
   require "conn.php";

    $sql = "SELECT book_img, book_name, type FROM tblbook;";

    $res = mysqli_query($conn, $sql);
	$result = array();
	
	
    while($row = mysqli_fetch_array($res)) {
        array_push($result, array(
			'bookImage'=>$row[0],
			'title'=>$row[1],
			'type'=>$row[2]
		));
    }
	echo json_encode(array("result"=>$result));
    mysqli_close($conn);
?>