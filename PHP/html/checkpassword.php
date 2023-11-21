<?php
// 데이터베이스 연결 정보
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";

// 안드로이드 앱에서 전송된 로그인 중인 회원의 uid와 입력된 비밀번호 받기
$uid = $_POST['uid'];
$passwordToCheck = $_POST['pw'];

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 회원 비밀번호 확인 쿼리 실행
$sql = "SELECT uid, pw, salt FROM user WHERE uid = '$uid'";
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

    // 로그인 성공 메시지 전송
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