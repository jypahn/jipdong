<?php

$db_name = "capstone123";      // DB 명
$username = "capstone123";    // DB 아이디
$password = "qkrrjsdn2001!";  // MySQL 비밀번호
$servername = "localhost";   // 서버 이름인데 로컬호스트로 ㄱㄱ
$conn = mysqli_connect($servername, $username, $password, $db_name);

$roomid = isset($_GET['roomid']) ? $_GET['roomid'] : '';

if ($roomid != '') {
  $enterdate = isset($_GET['enterdate']) ? $_GET['enterdate'] : '';
  $leastdate = isset($_GET['leastdate']) ? $_GET['leastdate'] : '';
  $gurantee = isset($_GET['gurantee']) ? $_GET['gurantee'] : '';
  $monthfee = isset($_GET['monthfee']) ? $_GET['monthfee'] : '';
  $manage = isset($_GET['manage']) ? $_GET['manage'] : '';

  $sql = "UPDATE room SET enterdate='$enterdate', leastdate='$leastdate', gurantee='$gurantee', monthfee='$monthfee', manage='$manage' WHERE roomid = '$roomid'";
  $result = mysqli_query($conn, $sql);

  if($result) {
      echo "1";
  } else {
      echo "-1";
  }
} else {
  echo "Error: roomid is not set.";
}

?>
