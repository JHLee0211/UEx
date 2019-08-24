<?php
    header("Content-Type: text/html;charset=UTF-8");
    $conn = mysqli_connect("localhost", "root", "", "ssafyproject");
    $data_stream = "'".$_POST['id']."','".$_POST['password']."','".$_POST['name']."','".$_POST['birth']."','".$_POST['sex']."'";
    $query = "insert into customerinformation values (".$data_stream.")";
    $result = mysqli_query($conn, $query);

    if($result)
        echo "1";
    else
        echo "-1";
   
    mysqli_close($conn);
?>