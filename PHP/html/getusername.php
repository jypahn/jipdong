<?php

// MySQL 서버에 연결합니다.
$servername = "localhost";
$username = "capstone123"; // MySQL 사용자 이름
$password = "qkrrjsdn2001!"; // MySQL 암호
$dbname = "capstone123"; // MySQL 데이터베이스 이름

// 안드로이드 앱으로부터 전달받은 uid 값 가져오기
$uid = $_GET['uid'];

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 쿼리문 실행하여 u_name 값 가져오기
$sql = "SELECT u_name FROM user WHERE uid = '$uid'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // 결과가 있을 경우 u_name 값을 가져와서 출력
    $row = $result->fetch_assoc();
    $u_name = $row['u_name'];
    echo $u_name;
} else {
    // 결과가 없을 경우 에러 메시지 출력 또는 다른 처리
    echo "존재하지 않는 계정";
}

// 연결 종료
$conn->close();
?>