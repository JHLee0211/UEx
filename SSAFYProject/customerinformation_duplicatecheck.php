<?php
    include 'connect.php';
    $conn = db_connect();
    $data_stream_id = $_POST['id'];
    mysqli_query($conn, "set names utf8");
    $res = mysqli_query($conn, "select id from customerinformation where id = '$data_stream_id'");
    $result = array();

    while($row = mysqli_fetch_array($res)) {
        array_push($result, array('id'=>$row[0]));
    }

    echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
?>