<?php
    include 'connect.php';
    $conn = db_connect();
    $data_stream_id = $_POST['id'];
    $res = mysqli_query($conn, "select * from customerinterest where id='$data_stream_id'");
    mysqli_query($conn, "set names utf8");
    $result = array();
    
    if($row = mysqli_fetch_array($res)) {
        array_push($result, array('inter1'=>$row[1], 'inter2'=>$row[2], 'inter3'=>$row[3]));
    }
    
    echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
?>