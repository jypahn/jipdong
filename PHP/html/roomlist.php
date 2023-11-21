<?php
    $db_name = "capstone123";       // DB 명
    $username = "capstone123";      // DB 아이디
    $password = "qkrrjsdn2001!";       // MySQL 비밀번호
    $servername = "localhost";      // 서버 이름인데 로컬호스트로 ㄱㄱ
    $conn = mysqli_connect($servername, $username, $password, $db_name);
    
    $sql = "SELECT * FROM roommate ORDER BY id DESC";
    $result = mysqli_query($conn, $sql);

// 게시글 목록 출력
$rows = array();
while ($row = mysqli_fetch_assoc($result)) {
    $rows[] = $row;
}

echo json_encode($rows);


// 데이터베이스 연결 종료
mysqli_close($conn);
?>