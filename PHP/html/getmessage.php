<?php
// MySQL 서버에 연결합니다.
$servername = "localhost";
$username = "capstone123"; // MySQL 사용자 이름
$password = "qkrrjsdn2001!"; // MySQL 암호
$dbname = "capstone123"; // MySQL 데이터베이스 이름

// 안드로이드 앱에서 보낸 chat_id 값을 변수에 저장합니다.
$chat_id = $_GET['chat_id'];

// MySQL 데이터베이스에 연결합니다.
$conn = mysqli_connect($servername, $username, $password, $dbname);

// 연결에 실패한 경우 에러를 출력합니다.
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// chat_id에 해당하는 메시지들을 조회합니다.
$sql = "SELECT id, sender_id, message, timestamp FROM chat_messages WHERE chat_id = '$chat_id' ORDER BY timestamp ASC";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    $chat_messages = array();
    while($row = mysqli_fetch_assoc($result)) {
        $chat_message = array(
            'id' => $row['id'],
            'sender_id' => $row['sender_id'],
            'message' => $row['message'],
            'timestamp' => $row['timestamp']
        );
        array_push($chat_messages, $chat_message);
    }
    // JSON 배열 반환
    echo json_encode($chat_messages);
} else {
    echo json_encode(array());
}

// MySQL 연결을 닫습니다.
mysqli_close($conn);
?>
