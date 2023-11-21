<?php

// 데이터베이스 연결
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";

// POST로 전달된 데이터 가져오기
$id = $_POST['id']; // 현재 로그인된 회원의 uid
$location = $_POST['location'];
$start_date = $_POST['start_date'];
$end_date = $_POST['end_date'];
$pricestarttext= $_POST['pricestarttext'];
$priceendtext= $_POST['priceendtext'];
$content = $_POST['content'];
$writer = $_POST['writer'];

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("데이터베이스 연결 실패: " . $conn->connect_error);
}

// 회원 정보 업데이트 쿼리 실행
$sql = "UPDATE roommate SET location = '$location', start_date = '$start_date', end_date = '$end_date', pricestarttext = '$pricestarttext', priceendtext = '$priceendtext', content = '$content' WHERE id = '$id'";
if ($conn->query($sql) === TRUE) {
    echo "회원 정보가 성공적으로 업데이트되었습니다.";
} else {
    echo "오류: " . $sql . "<br>" . $conn->error;
}

// 데이터베이스 연결 종료
$conn->close();
?>
