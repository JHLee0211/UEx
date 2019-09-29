<?php
    header ( "Content-Type: text/html;charset=UTF-8" );
    $conn = mysqli_connect ( "localhost", "root", "", "ssafyproject" );
//     $data_stream_id="'".$_POST['id']."'";
//     $data_stream_password="'".$_POST['password']."'";
//     $data_stream_name="'".$_POST['name']."'"; 
    $data_stream_id=$_POST['id'];
    $data_stream_password=$_POST['password'];
    $data_stream_name=$_POST['name'];
    
    
    $query = "delete from customerinformation where id ='$data_stream_id' and password='$data_stream_password' and name='$data_stream_name'";

    mysqli_query ( $conn, "set names utf8" );
    $result = mysqli_query ( $conn, $query );

    if ($result) {
	    mysqli_query ( $conn, "delete from customerinterest where id='$data_stream_id'");
	    echo "1";
    } else{
    	console.log("asd");
	    echo "-1";
    }

    mysqli_close ( $conn );
?>