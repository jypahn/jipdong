<?php
// DB 접속 정보
$db_name = "capstone123";     
$username = "capstone123";      
$password = "qkrrjsdn2001!";       
$servername = "localhost"; 
// POST로 받은 데이터 읽어오기
$userid = $_POST["uid"];
$userpw = $_POST["pw"];

// 데이터베이스 연결 생성
$conn = mysqli_connect($servername, $username, $password, $db_name);

// 연결 확인


if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// 사용자 정보 조회
$sql = "SELECT uid, pw, salt FROM user WHERE uid = '$userid'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // 사용자 정보가 존재하는 경우
  $row = $result->fetch_assoc();
  $stored_password = $row["pw"];
  $salt = $row["salt"];

  // 입력한 비밀번호와 DB에 저장된 비밀번호 비교
  $hash = hash('sha256', $userpw . $salt);
  if ($stored_password == $hash) {
      // 비밀번호가 일치하는 경우
      echo "success";
  } else {
      // 비밀번호가 일치하지 않는 경우
      echo "fail";
  }
} else {
  // 사용자 정보가 존재하지 않는 경우
  echo "no_user";
}

// 연결 종료
$conn->close();

?>