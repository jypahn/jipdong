<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
// 데이터베이스 연결 설정
$servername= "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$db_name = "capstone123";

// MySQL에 연결
$connection = new mysqli($servername, $username, $password, $db_name);

// 연결 오류가 있는지 확인
if ($connection->connect_error) {
    die("데이터베이스 연결 실패: " . $connection->connect_error);
}

// GET 요청에서 roomid 값 가져오기
$roomId = $_GET['roomid'];

// roomId를 사용하여 방 상세 정보를 조회하는 쿼리 작성
$query = "SELECT * FROM room WHERE roomid = '$roomId'";

// 쿼리 실행
$result = $connection->query($query);

// 쿼리 실행 결과 확인
if ($result->num_rows > 0) {
    // 결과가 있는 경우, JSON 응답 생성
    $response = array();
    while ($row = $result->fetch_assoc()) {
        $response[] = $row;
    }
    echo json_encode($response);
} else {
    // 결과가 없는 경우, 에러 메시지 출력
    echo "방 상세 정보를 가져오는데 실패했습니다.";
}

// 연결 종료
$connection->close();

?>