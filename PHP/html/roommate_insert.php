<?php

    $db_name = "capstone123"; 		// DB 명
    $username = "capstone123";		// DB 아이디
    $password = "qkrrjsdn2001!"; 		// MySQL 비밀번호
    $servername = "localhost";		// 서버 이름인데 로컬호스트로 ㄱㄱ
    $conn = mysqli_connect($servername, $username, $password, $db_name);


    $location= $_POST['location'];
    $start_date= $_POST['start_date'];
    $end_date= $_POST['end_date'];
    $pricestarttext= $_POST['pricestarttext'];
    $priceendtext= $_POST['priceendtext'];
    $content= $_POST['content'];
    $writer= $_POST['writer'];

  
    $sql = "INSERT INTO roommate(location, start_date, end_date, pricestarttext, priceendtext, content, uid) 
    VALUES ('$location','$start_date','$end_date', '$pricestarttext', '$priceendtext','$content','$writer')";
  

  if ($conn->query($sql) === TRUE) {
    echo "등록 완료.";
  } else {
    echo "Error: " . $sql . "<br>" . $conn->error;
  }

  // 데이터베이스 연결 종료
  $conn->close();

?>