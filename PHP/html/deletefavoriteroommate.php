<?php
// 데이터베이스 연결
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";

// POST로 전달된 UID와 Room ID 가져오기
$uid = $_POST['uid'];
$roommateid = $_POST['roommateid'];

// DB 연결 생성
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 쿼리문 실행하여 해당 정보 삭제
$query = "DELETE FROM favoriteroommate WHERE uid = '$uid' AND roommateid = '$roommateid'";
$result = $conn->query($query);

if ($result) {
    // 삭제 성공
    $response['success'] = true;
} else {
    // 삭제 실패
    $response['success'] = false;
}

// JSON 형식으로 응답
echo json_encode($response);

$conn->close();
?>
