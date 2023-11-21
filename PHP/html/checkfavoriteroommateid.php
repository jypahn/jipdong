<?php

$dbname = "capstone123";       // DB 명
$username = "capstone123";      // DB 아이디
$password = "qkrrjsdn2001!";       // MySQL 비밀번호
$servername = "localhost";      // 서버 이름인데 로컬호스트로 ㄱㄱ

// 안드로이드로부터 uid 받기
$uid = $_POST['uid'];

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("데이터베이스 연결 실패: " . $conn->connect_error);
}

$sql = "SELECT roommateid FROM favoriteroommate WHERE uid = '$uid'";
$result = $conn->query($sql);

// 결과 처리
if ($result->num_rows > 0) {
    // row가 존재하는 경우
    $roommateids = array(); // 모든 roomid를 저장할 배열
    while ($row = $result->fetch_assoc()) {
        $roommateid[] = $row["roommateid"]; // roomid를 배열에 추가
    }

    // JSON 형식으로 변환하여 출력 또는 안드로이드로 전달
    $response = json_encode($roommateid);
    echo $response;
} else {
    // row가 존재하지 않는 경우
    $response = json_encode([]); // 빈 배열을 JSON 형식으로 변환
    echo $response;
}

$conn->close();

?>