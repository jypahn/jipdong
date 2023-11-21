<?php
    $db_name = "capstone123";       // DB 명
    $username = "capstone123";      // DB 아이디
    $password = "qkrrjsdn2001!";    // MySQL 비밀번호
    $servername = "localhost";      // 서버 이름인데 로컬호스트로 ㄱㄱ
    $conn = mysqli_connect($servername, $username, $password, $db_name);

    // 연결 오류 확인
    if ($conn->connect_error) {
        die("연결 실패: " . $conn->connect_error);
    }

    // 안드로이드에서 전달한 roomid 값을 받아옵니다.
    $id = $_GET['roommateid'];

    // roomid를 사용하여 해당하는 데이터만 가져오도록 쿼리를 수정합니다.
    $sql = "SELECT * FROM roommate WHERE id = '$id' ORDER BY id DESC";
    $result = mysqli_query($conn, $sql);

    // JSON 형식으로 변환할 배열 생성
    $roomList = array();

    // 결과를 반복해서 가져와서 배열에 추가
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $room = array(
                "id" => $row["id"],
                "location" => $row["location"],
                "start_date" => $row["start_date"],
                "end_date" => $row["end_date"],
                "content" => $row["content"],
                "pricestarttext" => $row["pricestarttext"],
                "priceendtext" => $row["priceendtext"],
                "uid" => $row["uid"],
                "writedate" => $row["writedate"] 
            );
            $roomList[] = $room;
        }
    }

    // 연결 닫기
    $conn->close();

    // JSON 형식으로 출력
    header("Content-Type: application/json; charset=UTF-8");
    echo json_encode($roomList);
?>
