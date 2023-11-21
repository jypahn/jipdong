<?php

$dbname = "capstone123";       // DB 명
$username = "capstone123";      // DB 아이디
$password = "qkrrjsdn2001!";       // MySQL 비밀번호
$servername = "localhost";      // 서버 이름인데 로컬호스트로 ㄱㄱ

// 안드로이드로부터 uid 받기
$uid = $_POST['uid'];

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("데이터베이스 연결 실패: " . $conn->connect_error);
}

// uid에 해당하는 roomid 불러오기
$sql = "SELECT roomid FROM favoriteroom WHERE uid = '$uid'";
$result = $conn->query($sql);

// 결과 처리
if ($result->num_rows > 0) {
    // row가 존재하는 경우
    $row = $result->fetch_assoc();
    $roomid = $row["roomid"];

    // roomid 출력 또는 안드로이드로 전달
    echo $roomid;
} else {
    // row가 존재하지 않는 경우
    echo "해당하는 데이터가 없습니다.";
}

$conn->close();

?>
