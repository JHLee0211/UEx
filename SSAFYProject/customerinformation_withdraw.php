<?php
    include 'connect.php';
    $conn = db_connect();
    $data_stream_id=$_POST['id'];
    
    $query = "delete from customerinformation where id ='$data_stream_id'";

    mysqli_query($conn, "set names utf8");
    $result = mysqli_query($conn, $query);

    if($result) {
	    $result = mysqli_query($conn, "delete from customerinterest where id='$data_stream_id'");
	    if($result) {
	    	$result = mysqli_query($conn, "delete from autologin where id='$data_stream_id'");
	    	if($result) {
	    		echo "1";
	    	} else {
	    		echo "-1";
	    	}
	    } else {
	    	echo "-1";
	    }
    } else{
	    echo "-1";
    }

    mysqli_close($conn);
?>