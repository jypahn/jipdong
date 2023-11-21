<?php

// 데이터베이스 연결
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";


// POST로 전달된 데이터 가져오기
$uid = $_POST['uid']; // 현재 로그인된 회원의 uid
$newName = $_POST['newName']; // 변경할 이름
$newPhone = $_POST['newPhone']; // 변경할 전화번호
$newEmail = $_POST['newEmail']; // 변경할 이메일

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("데이터베이스 연결 실패: " . $conn->connect_error);
}

// 회원 정보 업데이트 쿼리 실행
$sql = "UPDATE user SET u_name = '$newName', phone = '$newPhone', email = '$newEmail' WHERE uid = '$uid'";
if ($conn->query($sql) === TRUE) {
    echo "회원 정보가 성공적으로 업데이트되었습니다.";
} else {
    echo "오류: " . $sql . "<br>" . $conn->error;
}

// 데이터베이스 연결 종료
$conn->close();
?>
