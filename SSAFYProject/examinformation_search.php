<?php
	header("Content-Type: text/html;charset=UTF-8");
	$conn = mysqli_connect("localhost", "root", "", "ssafyproject");
	$data_stream_jmcd = $_POST['jmcd'];
	$query = "select * from examinformation where jmcd = '$data_stream_jmcd'";
	mysqli_query($conn, "set names utf8");
	$res = mysqli_query($conn, $query);
	
	$result = array();
	
	while($row = mysqli_fetch_array($res)) {
		array_push($result, array('round'=>$row[1], 'w_recept_start'=>$row[2], 'w_recept_end'=>$row[3], 'w_exam_start'=>$row[4], 'w_exam_end'=>$row[5], 'w_presentation'=>$row[6], 'p_recept_start'=>$row[7], 'p_recept_end'=>$row[8], 'p_exam_start'=>$row[9], 'p_exam_end'=>$row[10], 'p_presentation'=>$row[11], 'etc'=>$row[12]));
	}
	
	echo json_encode(array("result"=>$result, JSON_UNESCAPED_UNICODE));
	
	mysqli_close($conn);
?>