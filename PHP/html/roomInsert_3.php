<?php
$db_name = "capstone123";      // DB 명
$username = "capstone123";    // DB 아이디
$password = "qkrrjsdn2001!";  // MySQL 비밀번호
$servername = "localhost";   // 서버 이름인데 로컬호스트로 ㄱㄱ
$conn = mysqli_connect($servername, $username, $password, $db_name);

$sex = isset($_POST['sex']) ? $_POST['sex'] : '';
$age = isset($_POST['age']) ? $_POST['age'] : '';
$smoke = isset($_POST['smoke']) ? $_POST['smoke'] : '';
$animal = isset($_POST['animal']) ? $_POST['animal'] : '';

$sql_insert = "INSERT INTO room(sex, age, smoke, animal) VALUES ('$sex', '$age', '$smoke', '$animal')";
$result_insert = mysqli_query($conn, $sql_insert);

if ($result_insert) {
    $roomid = mysqli_insert_id($conn); // 새로 생성된 roomid 가져오기

    echo $roomid; // 생성된 roomid 반환
} else {
    echo "Error: " . mysqli_error($conn);
}

mysqli_close($conn);
?>
