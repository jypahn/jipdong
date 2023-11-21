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
    $roomid = $_GET['roomid'];

    // roomid를 사용하여 해당하는 데이터만 가져오도록 쿼리를 수정합니다.
    $sql = "SELECT * FROM room WHERE roomid = '$roomid' ORDER BY roomid DESC";
    $result = mysqli_query($conn, $sql);

    // JSON 형식으로 변환할 배열 생성
    $roomList = array();

    // 결과를 반복해서 가져와서 배열에 추가
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $room = array(
                "roomid"=> $row["roomid"],
                "roomtitle" => $row["roomtitle"],
                "adress" => $row["adress"],
                "housesort" => $row["housesort"],
                "sort_of_floor" => $row["sort_of_floor"],
                "gurantee" => $row["gurantee"],
                "monthfee" => $row["monthfee"],
	        "host" => $row["host"]
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
