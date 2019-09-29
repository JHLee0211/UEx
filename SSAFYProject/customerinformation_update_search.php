<?php
    header("Content-Type: text/html;charset=UTF-8");
    $conn = mysqli_connect("localhost", "root", "", "ssafyproject");
    $data_stream_id = $_POST['id'];
    $res = mysqli_query($conn, "select * from customerinformation where id='$data_stream_id'");
    mysqli_query($conn, "set names utf8");
    $result = array();
    
    if($row = mysqli_fetch_array($res)) {
        array_push($result, array('id'=>$row[0], 'password'=>$row[1], 'name'=>$row[2], 'birth'=>$row[3], 'sex'=>$row[4]));
    }
    
    echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
?>