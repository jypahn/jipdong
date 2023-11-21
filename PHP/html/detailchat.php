<?php
    $db_name = "capstone123";       // DB 명
    $username = "capstone123";      // DB 아이디
    $password = "qkrrjsdn2001!";    // MySQL 비밀번호
    $servername = "localhost";      // 서버 이름인데 로컬호스트로 ㄱㄱ
 
// uid와 uid2 값 가져오기
$user1_id = $_POST['user1_id'];
$user2_id = $_POST['user2_id'];

// 데이터베이스 연결 생성
$conn = new mysqli($servername, $username, $password, $db_name);
if ($conn->connect_error) {
    die("데이터베이스 연결 실패: " . $conn->connect_error);
}

// SQL 쿼리 작성
$sql = "SELECT id FROM chats WHERE (user1_id = '$user1_id' AND user2_id = '$user2_id') OR (user1_id = '$user2_id' AND user2_id = '$user1_id')";

// 쿼리 실행
$result = $conn->query($sql);

// 결과 반환
if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $roomId = $row["id"];
    echo $roomId;
} else {
    echo "채팅방 ID 없음";
}

// 데이터베이스 연결 종료
$conn->close();
?>
