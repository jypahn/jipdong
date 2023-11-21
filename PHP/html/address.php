<?php
    $db_name = "capstone123"; 		// DB 명
    $username = "capstone123";		// DB 아이디
    $password = "qkrrjsdn2001!"; 		// MySQL 비밀번호
    $servername = "localhost";		// 서버 이름인데 로컬호스트로 ㄱㄱ
    $conn = mysqli_connect($servername, $username, $password, $db_name);

    $adress= $_POST['adress'];
    $detailAdress= $_POST['detailAdress'];
  
    $sql = "INSERT INTO room(adress,detailAdress) VALUES ('$adress','$detailAdress')";
    $result = mysqli_query($conn, $sql);
     
    if($result)
      echo "1";
    else
      echo "-1";
     
    mysqli_close($conn);
?>

