<?php
    session_start();
    include 'connect.php';
    $conn = db_connect();
    
    $result = array();
   
    $data_stream_phone_id = $_POST["phone_id"];
    
    $query = "select * from autologin where phone_id = '$data_stream_phone_id'";
    //$query = "select * from autologin where phone_id = 'data_stream_phone_id'";
    mysqli_query($conn, "set names utf8");
    $res = mysqli_query($conn, $query);
    
    $row = mysqli_fetch_array($res);
    
    if($row[1] != "") {
        $result = array();
        array_push($result, array('cookies'=>$row[1], 'id'=>$row[2]));
        echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
    } else {
        echo -1;
    }

    mysqli_close($conn);
?>