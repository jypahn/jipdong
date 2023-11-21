<?php 
    $con = mysqli_connect("localhost", "capstone123", "qkrrjsdn2001!", "capstone123");

    $uid = $_POST["uid"];
   
    $statement = mysqli_prepare($con, "SELECT uid FROM user WHERE uid = ?");
    mysqli_stmt_bind_param($statement, "s", $uid);   // 문자열로 아이디 입력
    mysqli_stmt_execute($statement);         // 위의 명령문 실행
    mysqli_stmt_store_result($statement);   // select 실행시 필수 명령
    mysqli_stmt_bind_result($statement, $uid);  // 명령문에 바인드


    $response = array();
    $response["success"] = true;
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"]=false;
        $response["uid"]=$uid;
    }
   
    echo json_encode($response);



?>