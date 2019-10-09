<?php
    session_start();
    include 'connect.php';
    $conn = db_connect();
    
    $result = array();
    
    if(isset($_SESSION["IsLogin"]) && $_SESSION["IsLogin"] == 'Y') {
        array_push($result, array('IsLogin'=>$_SESSION["IsLogin"]));
        echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
    }
    
    else {
        $data_stream_id = $_POST['id'];
        $data_stream_pw = $_POST['password'];
        //$data_stream_id = "asd";
        //$data_stream_pw = "asdf";
    
        $query = "select password from customerinformation where id = '$data_stream_id'";
        mysqli_query($conn, "set names utf8");
        $res = mysqli_query($conn, $query);
        
        $row = mysqli_fetch_array($res);
        if($data_stream_pw == $row['password']) {
            $_SESSION["IsLogin"] = 'Y';
            array_push($result, array('IsLogin'=>$_SESSION["IsLogin"]));
            echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
        }
        
        else {
            $_SESSION["IsLogin"] = 'N';
            array_push($result, array('IsLogin'=>$_SESSION["IsLogin"]));
            session_destroy();
            echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
        }
    }
    
    mysqli_close($conn);
?>