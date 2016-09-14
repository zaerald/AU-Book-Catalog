<?php
	
   require "conn.php"
    
    $username = $_POST['username'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM tbllogin WHERE username='$username' AND password='$password';";

    $result = mysqli_query($conn, $sql);
    $check = mysqli_fetch_array($result);

    if(isset($check)) {
        echo 'success';
    } else {
        echo 'failure';
    }
    
    mysqli_close($conn);
	
?>