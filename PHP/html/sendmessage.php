<?php

// MySQL 서버에 연결합니다.
$servername = "localhost";
$username = "capstone123"; // MySQL 사용자 이름
$password = "qkrrjsdn2001!"; // MySQL 암호
$dbname = "capstone123"; // MySQL 데이터베이스 이름

    // POST로 받은 데이터를 변수에 저장합니다.
    $chat_id = $_POST["chat_id"];
    $sender_id = $_POST["sender_id"];
    $message = $_POST["message"];
    
    // MySQL 데이터베이스에 연결합니다.
    $conn = mysqli_connect($servername, $username, $password, $dbname);
    
    // 연결에 실패한 경우 에러를 출력합니다.
    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
    }
    
    // chat_messages 테이블에 새로운 메시지를 추가합니다.
    $sql = "INSERT INTO chat_messages (chat_id, sender_id, message) VALUES ('$chat_id', '$sender_id', '$message')";
    
    // 쿼리를 실행합니다.
    if (mysqli_query($conn, $sql)) {
        echo "Message sent successfully";
    } else {
        echo "Error: " . $sql . "<br>" . mysqli_error($conn);
    }
    
    // MySQL 연결을 닫습니다.
    mysqli_close($conn);
?>