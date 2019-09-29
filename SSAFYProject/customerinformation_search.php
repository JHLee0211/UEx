<?php
	header("Content-Type: text/html;charset=UTF-8");
    $conn = mysqli_connect("localhost", "root", "", "ssafyproject");
    $res = mysqli_query($conn, "select * from customerinformation");
    mysqli_query($conn, "set names utf8");
    $result = array();
    
    while($row = mysqli_fetch_array($res)) {
        array_push($result, array('id'=>$row[0], 'password'=>$row[1]));
    }
    
    echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
?>