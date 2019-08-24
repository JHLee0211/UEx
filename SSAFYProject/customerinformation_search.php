<?php
    $conn = mysqli_connect("localhost", "root", "", "ssafyproject");
    $res = mysqli_query($conn, "select * from customerinformation");
    $result = array();
    
    while($row = mysqli_fetch_array($res)) {
        array_push($result, array('id'=>$row[0], 'password'=>$row[1]));
    }
    
    echo json_encode(array("result"=>$result));
?>