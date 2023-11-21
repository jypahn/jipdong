<?php
    $db_name = "capstone123";       // DB 명
    $username = "capstone123";      // DB 아이디
    $password = "qkrrjsdn2001!";    // MySQL 비밀번호
    $servername = "localhost";      // 서버 이름 (로컬호스트로 설정)
    $conn = mysqli_connect($servername, $username, $password, $db_name);

    $uid = $_POST['uid'];
    $roommateid = $_POST['roommateid'];

    // 중복된 값이 있는지 확인하기 위해 SELECT 쿼리 실행
    $checkQuery = "SELECT * FROM favoriteroommate WHERE uid='$uid' AND roommateid='$roommateid'";
    $checkResult = mysqli_query($conn, $checkQuery);

    if (mysqli_num_rows($checkResult) > 0) {
        echo "이미 해당 방을 즐겨찾기에 추가한 상태입니다.";
    } else {
        $sql = "INSERT INTO favoriteroommate(uid, roommateid) VALUES ('$uid','$roommateid')";
        $result = mysqli_query($conn, $sql);
        
        if ($result) {
            echo "방을 즐겨찾기에 추가했습니다.";
        } else {
            echo "Error: " . $sql . "<br>" . $conn->error;
        }
    }
    
    mysqli_close($conn);
?>