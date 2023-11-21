<?php
// MySQL 서버에 연결합니다.
$servername = "localhost";
$username = "capstone123"; // MySQL 사용자 이름
$password = "qkrrjsdn2001!"; // MySQL 암호
$dbname = "capstone123"; // MySQL 데이터베이스 이름

// 안드로이드 애플리케이션에서 전달한 사용자 ID와 채팅방 정보
$user1_id = $_POST['user1_id'];
$user2_id = $_POST['user2_id'];

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 중복 채팅방 검사
$sql = "SELECT id FROM chats WHERE (user1_id = '$user1_id' AND user2_id = '$user2_id') OR (user1_id = '$user2_id' AND user2_id = '$user1_id')";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // 이미 중복된 채팅방이 존재함
    echo "이미 생성된 채팅방";
} else {
    // 중복된 채팅방이 없음, 새로운 채팅방 생성
    $sql = "INSERT INTO chats (user1_id, user2_id) VALUES ('$user1_id', '$user2_id')";
    if ($conn->query($sql) === TRUE) {
        echo "채팅방 생성";
    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }
}

// 데이터베이스 연결 종료
$conn->close();

?>