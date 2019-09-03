<?php
    header("Content-Type: text/html;charset=UTF-8");
    $conn = mysqli_connect("localhost", "root", "", "ssafyproject");
    $data_stream = "'".$_POST['id']."','".$_POST['password']."','".$_POST['name']."','".$_POST['birth']."','".$_POST['sex']."'";
    $query = "insert into customerinformation values (".$data_stream.")";
    mysqli_query($conn, "set names utf8");
    $result = mysqli_query($conn, $query);

    if($result) {
        mysqli_query($conn, "insert into customerinterest values ('".$_POST['id']."', null, null, null)");
        echo "1";
    }
    else
        echo "-1";
   
    mysqli_close($conn);
?>