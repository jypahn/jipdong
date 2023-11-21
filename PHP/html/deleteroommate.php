<?php
// 데이터베이스 연결 설정
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";

$id = $_GET['id'];

// 데이터베이스 연결 생성
$conn = new mysqli($servername, $username, $password, $dbname);

// 연결 확인
if ($conn->connect_error) {
    die("데이터베이스 연결 실패: " . $conn->connect_error);
}

// 방 정보 삭제 쿼리 실행
$sql = "DELETE FROM roommate WHERE id = $id";

if ($conn->query($sql) === TRUE) {
    // 삭제 성공 시 응답 메시지 전송
    echo "룸메이트 정보가 성공적으로 삭제되었습니다.";
} else {
    // 삭제 실패 시 에러 메시지 전송
    echo "룸메이트 정보 삭제 중 오류가 발생했습니다: " . $conn->error;
}

// 데이터베이스 연결 닫기
$conn->close();

?>
