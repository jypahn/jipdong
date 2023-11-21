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

    // 안드로이드에서 전달한 uid 값을 받아옵니다.
    $roomid = $_GET['roomid'];

    // uid와 host를 사용하여 해당하는 데이터만 가져오도록 쿼리를 수정합니다.
    $sql = "SELECT * FROM room WHERE roomid = '$roomid'  ORDER BY roomid DESC";
    $result = mysqli_query($conn, $sql);

    // JSON 형식으로 변환할 배열 생성
    $roomList = array();

    // 결과를 반복해서 가져와서 배열에 추가
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $room = array(
                "host" => $row["host"],
		    "roomid" => $row["roomid"],
                "postdate" => $row["postdate"],
                "adress" => $row["adress"],
                "detailAdress" => $row["detailAdress"],
                "housesort" => $row["housesort"],
                "sort_of_floor" => $row["sort_of_floor"],
                "num_of_floor" => $row["num_of_floor"],
                "area" => $row["area"],
                "roomcount" => $row["roomcount"],
                "toiletcount" => $row["toiletcount"],
                "options" => $row["options"],
                "sex" => $row["sex"],
                "age" => $row["age"],
                "smoke" => $row["smoke"],
                "animal" => $row["animal"],
                "enterdate" => $row["enterdate"],
                "leastdate" => $row["leastdate"],
                "gurantee" => $row["gurantee"],
                "monthfee" => $row["monthfee"],
                "manage" => $row["manage"],
                "yorn" => $row["yorn"],
                "roomtitle" => $row["roomtitle"],
                "photo1" => $row["photo1"],
                "photo2" => $row["photo2"],
                "photo3" => $row["photo3"],
                "photo4" => $row["photo4"],
                "photo5" => $row["photo5"],
                "photo6" => $row["photo6"],
                "content" => $row["content"]
            );
    echo json_encode($room);
        }
    }

    // 연결 닫기
    $conn->close();

    // JSON 형식으로 출력
    header("Content-Type: application/json; charset=UTF-8");
    echo json_encode($roomList, JSON_UNESCAPED_UNICODE);
?>
