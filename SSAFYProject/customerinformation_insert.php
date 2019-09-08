<?php
    header("Content-Type: text/html;charset=UTF-8");
    $conn = mysqli_connect("localhost", "root", "", "ssafyproject");
    $data_stream_id = $_POST['id'];
    $data_stream_password = $_POST['password'];
    $data_stream_name = $_POST['name'];
    $data_stream_birth = $_POST['birth'];
    $data_stream_sex = $_POST['sex'];
    
    $query = "insert into customerinformation values ('$data_stream_id', '$data_stream_password', '$data_stream_name', '$data_stream_birth', '$data_stream_sex')";
    mysqli_query($conn, "set names utf8");
    $result = mysqli_query($conn, $query);

    if($result) {
        mysqli_query($conn, "insert into customerinterest values ('$data_stream_id', null, null, null)");
        echo "1";
    }
    else
        echo "-1";
   
    mysqli_close($conn);
?>