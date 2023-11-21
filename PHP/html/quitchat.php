<?php
// 데이터베이스 연결
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";

// 선택한 채팅방 ID와 로그인 중인 사용자 ID를 GET 방식으로 전달받음
$chatroomId = $_GET['chatroom_id'] ?? '';
$userId = $_GET['user_id'] ?? '';

// 데이터베이스 연결 생성
$conn = new mysqli($servername, $username, $password, $dbname);

// 연결 오류 확인
if ($conn->connect_error) {
    die("데이터베이스 연결 실패: " . $conn->connect_error);
}

// 사용자 입력값 검증
if (empty($chatroomId) || empty($userId)) {
    die("채팅방 ID와 사용자 ID를 전달받지 못했습니다.");
}

// 사용자를 채팅방에서 제거하는 SQL 쿼리 실행 (Prepared Statements 사용)
$sql = "UPDATE chats SET user1_id = NULL WHERE id = ? AND user1_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ss", $chatroomId, $userId);
$result = $stmt->execute();

if ($result) {
    // 제거 성공
    echo "사용자가 채팅방에서 제거되었습니다.";
} else {
    // 제거 실패
    echo "사용자 제거 중 오류가 발생했습니다.";
}

// 데이터베이스 연결 종료
$stmt->close();
$conn->close();
?>