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

// 메시지 전송
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $chat_room_id = $_POST['chat_room_id'];
    $sender_id = $_POST['sender_id'];
    $message = $_POST['message'];

    $sql = "INSERT INTO messages (chat_room_id, sender_id, message) VALUES ('$chat_room_id', '$sender_id', '$message')";
    if (mysqli_query($conn, $sql)) {
        $response = array('result' => 'success');
        echo json_encode($response);
    } else {
        $response = array('result' => 'fail');
        echo json_encode($response);
    }
}

// 메시지 수신
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $chat_room_id = $_GET['chat_room_id'];
    $last_message_id = $_GET['last_message_id'];

    $sql = "SELECT * FROM messages WHERE chat_room_id = '$chat_room_id' AND id > '$last_message_id'";
    $result = mysqli_query($conn, $sql);

    if (mysqli_num_rows($result) > 0) {
        $messages = array();
        while($row = mysqli_fetch_assoc($result)) {
            $message = array(
                'id' => $row['id'],
                'chat_room_id' => $row['chat_room_id'],
                'sender_id' => $row['sender_id'],
                'message' => $row['message'],
                'timestamp' => $row['timestamp']
            );
            array_push($messages, $message);
        }
        // JSON 배열 반환
        echo json_encode($messages);
    } else {
        echo json_encode(array());
    }
}

mysqli_close($conn);
?>
