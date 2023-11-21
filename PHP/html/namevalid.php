<?php 
    $con = mysqli_connect("localhost", "capstone123", "qkrrjsdn2001!", "capstone123");

    $u_name = $_POST["u_name"];
   
    $statement = mysqli_prepare($con, "SELECT u_name FROM user WHERE u_name = ?");
    mysqli_stmt_bind_param($statement, "s", $u_name);   // 문자열로 이름 입력
    mysqli_stmt_execute($statement);         // 위의 명령문 실행
    mysqli_stmt_store_result($statement);   // select 실행시 필수 명령
    mysqli_stmt_bind_result($statement, $uid);  // 명령문에 바인드


    $response = array();
    $response["success"] = true;
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"]=false;
        $response["u_name"]=$u_name;
    }
   
    echo json_encode($response);



?>