<?php
    $db_name = "capstone123"; 		// DB 명
    $username = "capstone123";		// DB 아이디
    $password = "qkrrjsdn2001!"; 		// MySQL 비밀번호
    $servername = "localhost";		// 서버 이름인데 로컬호스트로 ㄱㄱ
    $conn = mysqli_connect($servername, $username, $password, $db_name);

    $roomtitle= $_POST['roomtitle'];
    $photo1= $_POST['photo1'];
    $photo2= $_POST['photo2'];
    $photo3= $_POST['photo3'];
    $photo4= $_POST['photo4'];
    $photo5= $_POST['photo5'];
    $photo6= $_POST['photo6'];
    $content= $_POST['content'];
    $postdate= date("Y-m-d H:i:s");
    $yorn="N";
    
    $sql = "INSERT INTO room(postdate,roomtitle,content,yorn,photo1,photo2,photo3,photo4,photo5,photo6) 
    VALUES ('$postdate','$roomtitle','$content','$yorn','$photo1','$photo2','$photo3','$photo4','$photo5','$photo6')";
    $result = mysqli_query($conn, $sql);
     
    if($result)
      echo "1";
    else
      echo "-1";
     
    mysqli_close($conn);
?>