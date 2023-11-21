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

// 채팅방 목록 조회
$uid = $_GET['uid'];

// 변경된 쿼리: LEFT JOIN과 GROUP BY 절 변경
$sql = "SELECT chats.id, chats.user1_id, chats.user2_id, chat_messages.message, chat_messages.timestamp
        FROM chats
        LEFT JOIN (
            SELECT chat_id, MAX(timestamp) AS max_timestamp
            FROM chat_messages
            GROUP BY chat_id
        ) AS latest_message ON chats.id = latest_message.chat_id
        LEFT JOIN chat_messages ON latest_message.chat_id = chat_messages.chat_id AND latest_message.max_timestamp = chat_messages.timestamp
        WHERE chats.user1_id = '$uid' OR chats.user2_id = '$uid'
        ORDER BY latest_message.max_timestamp DESC";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    $chat_rooms = array();
    while($row = mysqli_fetch_assoc($result)) {
        $chat_room = array(
            'id' => $row['id'],
            'user1' => $row['user1_id'],
            'user2' => $row['user2_id'],
            'last_message' => $row['message'],
            'timestamp' => $row['timestamp']
        );
        array_push($chat_rooms, $chat_room);
    }
    // JSON 배열 반환
    echo json_encode($chat_rooms);
} else {
    echo json_encode(array());
}

mysqli_close($conn);
?>
