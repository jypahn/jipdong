<?php
    $db_name = "capstone123"; 		// DB 명
    $username = "capstone123";		// DB 아이디
    $password = "qkrrjsdn2001!"; 		// MySQL 비밀번호
    $servername = "localhost";		// 서버 이름인데 로컬호스트로 ㄱㄱ
    $conn = mysqli_connect($servername, $username, $password, $db_name);

    $sql = "SELECT * FROM room";
    $result = mysqli_query($conn, $sql);

    if (mysqli_num_rows($result) > 0) {
        // 데이터가 존재하는 경우
        while($row = mysqli_fetch_assoc($result)) {
            echo "이름: " . $row["roomid"]. " - 게시날짜: " . $row["postdate"]. " - 성별: " . $row["sex"]. "<br>";
        }
    } else {
        // 데이터가 존재하지 않는 경우
        echo "0 results";
    }

    
     
    if($result)
      echo "1";
    else
      echo "-1";
     
    mysqli_close($conn);
?>