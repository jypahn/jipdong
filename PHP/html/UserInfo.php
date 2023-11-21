<?php


// 데이터베이스 연결 설정
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";
// 데이터베이스 연결 생성
$conn = new mysqli($servername, $username, $password, $dbname);

// 연결 오류 체크
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 안드로이드로부터 전달된 사용자 ID
$uid = $_GET['uid'];

// 사용자 정보 조회 쿼리
$sql = "SELECT u_name, email, phone FROM user WHERE uid = '$uid'";
$result = $conn->query($sql);

// 쿼리 결과 확인
if ($result->num_rows > 0) {
    // 결과를 배열로 변환
    $row = $result->fetch_assoc();
    
    // 안드로이드로 응답 데이터 전송
    $response = array(
        'u_name' => $row['u_name'],
        'email' => $row['email'],
        'phone' => $row['phone']
    );
    
    echo json_encode($response);
} else {
    // 조회된 결과가 없을 경우
    echo "User not found";
}

// 데이터베이스 연결 종료
$conn->close();
?>