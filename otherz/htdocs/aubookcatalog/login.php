<?php
	
    define('HOST', 'localhost');
    define('USER', 'root');
    define('PASS', '');
    define('DB', 'dbaubookcatalog');

    $conn = mysqli_connect(HOST, USER, PASS, DB);
    
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