<?php
    include 'connect.php';
    $conn = db_connect();
    $data_stream_name = $_POST['name'];
    $query = "select jmcd from short_examination where name like '$data_stream_name'";
    mysqli_query($conn, "set names utf8");
    $res = mysqli_query($conn, $query);
    
    $result = array();
    
    while($row = mysqli_fetch_array($res)) {
        array_push($result, array('jmcd'=>$row[0]));
    }
    
    echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
    
    mysqli_close($conn);
?>