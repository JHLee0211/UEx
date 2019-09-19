<?php
    header("Content-Type: text/html;charset=UTF-8");
    $conn = mysqli_connect("localhost", "root", "", "ssafyproject");
    $data_stream_phone_id = $_POST['phone_id'];
    
    $query = "delete from autologin where phone_id = '$data_stream_phone_id'";
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