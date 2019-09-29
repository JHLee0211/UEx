<?php
    header("Content-Type: text/html;charset=UTF-8");
    $conn = mysqli_connect("localhost", "root", "", "ssafyproject");
    $data_stream_jmcd = $_POST['jmcd'];
    $query = "select * from examinformation_sub where jmcd = '$data_stream_jmcd'";
    mysqli_query($conn, "set names utf8");
    $res = mysqli_query($conn, $query);
    
    $result = array();
    
    while($row = mysqli_fetch_array($res)) {
        array_push($result, array('caution'=>$row[1], 'price'=>$row[2]));
    }
    
    echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
    
    mysqli_close($conn);
?>