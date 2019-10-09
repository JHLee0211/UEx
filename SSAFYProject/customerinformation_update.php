<?php
    include 'connect.php';
    $conn = db_connect();
    $data_stream_id = $_POST['id'];
    $data_stream_password = $_POST['password'];
    $data_stream_name = $_POST['name'];
    $data_stream_birth = $_POST['birth'];
    $data_stream_sex = $_POST['sex'];
    
    $query = "update customerinformation set password='$data_stream_password', name='$data_stream_name', birth='$data_stream_birth', sex='$data_stream_sex' where id='$data_stream_id'";
    mysqli_query($conn, "set names utf8");
    $result = mysqli_query($conn, $query);
    
    if($result) {
        echo "1";
    }
    else
        echo "-1";
    
    mysqli_close($conn);
?>