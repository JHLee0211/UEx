<?php
    session_start();
    $result = array();
    
    if(isset($_SESSION["IsLogin"]) && $_SESSION["IsLogin"] == 'Y') {
        array_push($result, array('IsLogin'=>$_SESSION["IsLogin"]));
        echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
    } else {
        $_SESSION["IsLogin"] = 'N';
        array_push($result, array('IsLogin'=>$_SESSION["IsLogin"]));
        echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
    }
?>