<?php
  // 데이터베이스 연결
  $db_name = "capstone123";       // DB 명
  $username = "capstone123";      // DB 아이디
  $password = "qkrrjsdn2001!";       // MySQL 비밀번호
  $servername = "localhost";      // 서버 이름인데 로컬호스트로 ㄱㄱ
 
  $conn = mysqli_connect($servername, $username, $password, $db_name);

  // 폼 데이터 받아오기
  $uid = $_POST['uid'];
  $pw = $_POST['pw'];
  $u_name = $_POST['u_name'];
  $phone = $_POST['phone'];
  $email = $_POST['email'];
  $sex = $_POST['sex'];
  $zipcode = $_POST['zipcode'];
  $address = $_POST['address'];
  


  // 비밀번호 암호화
  $salt = bin2hex(random_bytes(16));
  $password_hash = hash('sha256', $pw . $salt);

  // SQL 쿼리 실행하여 데이터베이스에 회원 정보 저장
  $sql = "INSERT INTO user (uid, pw, u_name, phone, email, sex, zipcode, address,salt)
  VALUES ('$uid', '$password_hash', '$u_name', '$phone', '$email', '$sex','$zipcode', '$address','$salt')";


  if ($conn->query($sql) === TRUE) {
    echo "회원가입이 완료되었습니다.";
  } else {
    echo "Error: " . $sql . "<br>" . $conn->error;
  }

  // 데이터베이스 연결 종료
  $conn->close();


?>