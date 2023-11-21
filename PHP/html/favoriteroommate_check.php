<?php
$uid = $_POST['uid'];
$roommateid = $_POST['roommateid'];

// 데이터베이스 연결
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";

// favoriteroom 테이블에서 uid와 roomid를 조회하여 결과를 반환
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$stmt = $conn->prepare("SELECT * FROM favoriteroommate WHERE uid = ? AND roommateid = ?");
$stmt->bind_param("ss", $uid, $roommateid);
$stmt->execute();
$result = $stmt->get_result();

// 결과에 따라 "favorite" 또는 "exit"을 출력
if ($result->num_rows > 0) {
    echo "favorite";
} else {
    echo "exit";
}

$stmt->close();
$conn->close();
?>
