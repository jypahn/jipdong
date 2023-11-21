<?php

    $db_name = "capstone123"; 		// DB 명
    $username = "capstone123";		// DB 아이디
    $password = "qkrrjsdn2001!"; 		// MySQL 비밀번호
    $servername = "localhost";		// 서버 이름인데 로컬호스트로 ㄱㄱ
    $conn = mysqli_connect($servername, $username, $password, $db_name);

    $housesort= $_POST['housesort'];
    $sort_of_floor= $_POST['sort_of_floor'];
    $num_of_floor= $_POST['num_of_floor'];
    $area= $_POST['area'];
    $roomcount= $_POST['roomcount'];
    $toiletcount= $_POST['toiletcount'];
    $options= $_POST['options'];
  
    $sql = "INSERT INTO room(housesort,sort_of_floor,num_of_floor,area,roomcount,toiletcount,options) 
    VALUES ('$housesort','$sort_of_floor','$num_of_floor','$area', '$roomcount', '$toiletcount', '$options')";
    $result = mysqli_query($conn, $sql);
     
    if($result)
      echo "1";
    else
      echo "-1";
     
    mysqli_close($conn);
?>

<?php
  echo "$sql";
?>