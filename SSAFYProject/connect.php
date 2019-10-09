<?php
    function db_connect() {
        header("Content-Type: text/html;charset=UTF-8");
        $conn = mysqli_connect("70.12.227.10", "root", "", "ssafyproject");
        return $conn;
    }
?>