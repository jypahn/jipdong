<?php
header('Content-Type: application/json');

// 데이터베이스 연결
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// POST 요청에서 데이터 가져오기
$data = json_decode(file_get_contents('php://input'), true);
$chat_id = $data['chat_id'];
$sender_id = $data['sender_id'];
$message = $data['message'];
$timestamp = $data['timestamp'];

// 메시지 삽입
$sql = "INSERT INTO chat_messages (chat_id, sender_id, message, timestamp) VALUES ('$chat_id', '$sender_id', '$message', '$timestamp')";
$result = mysqli_query($conn, $sql);

if ($result) {
    $response = array(
        'status' => 'success',
        'message' => 'Message sent successfully'
    );
} else {
    $response = array(
        'status' => 'error',
        'message' => 'Failed to send message'
    );
}

// JSON 형식으로 응답 반환
echo json_encode($response);

mysqli_close($conn);
?>
