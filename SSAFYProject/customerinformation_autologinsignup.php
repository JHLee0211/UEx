<?php
    include 'connect.php';
    $conn = db_connect();
    $data_stream_phone_id = $_POST['phone_id'];
    $data_stream_cookies = $_POST['cookies'];
    $data_stream_id = $_POST['id'];
    
    $query = "insert into autologin values ('$data_stream_phone_id', '$data_stream_cookies', '$data_stream_id')";
    mysqli_query($conn, "set names utf8");
    $result = mysqli_query($conn, $query);
    
    if($result) {
        echo "1";
    }
    else {
        echo "-1";
    }
        
    mysqli_close($conn);
?>