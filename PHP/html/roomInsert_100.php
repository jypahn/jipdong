<?php

    $db_name = "capstone123"; 		// DB 명
    $username = "capstone123";		// DB 아이디
    $password = "qkrrjsdn2001!"; 		// MySQL 비밀번호
    $servername = "localhost";		// 서버 이름인데 로컬호스트로 ㄱㄱ
    $conn = mysqli_connect($servername, $username, $password, $db_name);


    $host= $_POST['host'];
    $adress= $_POST['adress'];
    $detailAdress= $_POST['detailAdress'];
    $housesort= $_POST['housesort'];
    $sort_of_floor= $_POST['sort_of_floor'];
    $num_of_floor= $_POST['num_of_floor'];
    $area= $_POST['area'];
    $roomcount= $_POST['roomcount'];
    $toiletcount= $_POST['toiletcount'];
    $options= $_POST['options'];
    $sex= $_POST['sex'];
    $age= $_POST['age'];
    $smoke= $_POST['smoke'];
    $animal= $_POST['animal'];
    $enterdate= $_POST['enterdate'];
    $leastdate= $_POST['leastdate'];
    $gurantee= $_POST['gurantee'];
    $monthfee= $_POST['monthfee'];
    $manage= $_POST['manage'];
    $roomtitle= $_POST['roomtitle'];
    $photo1= $_POST['photo1'];
    $photo2= $_POST['photo2'];
    $photo3= $_POST['photo3'];
    $photo4= $_POST['photo4'];
    $photo5= $_POST['photo5'];
    $photo6= $_POST['photo6'];
    $content= $_POST['content'];
  
    $sql = "INSERT INTO room(host,adress,detailAdress,housesort,sort_of_floor,num_of_floor,area,roomcount,toiletcount,options,sex,age,smoke,animal,enterdate,leastdate,gurantee,monthfee,manage,roomtitle,photo1,photo2,photo3,photo4,photo5,photo6,content) 
    VALUES ('$host','$adress','$detailAdress','$housesort','$sort_of_floor','$num_of_floor','$area', '$roomcount', '$toiletcount', '$options', '$sex', '$age', '$smoke', '$animal', '$enterdate', '$leastdate', '$gurantee', '$monthfee', '$manage','$roomtitle','$photo1','$photo2','$photo3','$photo4','$photo5','$photo6','$content' )";
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