<?php
// 데이터베이스 연결
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";

$conn = mysqli_connect($servername, $username, $password, $dbname);

    $roomid= $_POST['roomid'];
    $host= $_POST['host'];
    $adress= $_POST['adress'];
    $detailAdress= $_POST['detailAdress'];
    $housesort= $_POST['housesort'];
    $sort_of_floor= $_POST['sort_of_floor'];
    $num_of_floor= $_POST['num_of_floor'];
    $area= $_POST['area'];
    $roomcount= $_POST['roomcount'];
    $toiletcount= $_POST['toiletcount'];
    $options= $_POST['options'];
    $sex= $_POST['sex'];
    $age= $_POST['age'];
    $smoke= $_POST['smoke'];
    $animal= $_POST['animal'];
    $enterdate= $_POST['enterdate'];
    $leastdate= $_POST['leastdate'];
    $gurantee= $_POST['gurantee'];
    $monthfee= $_POST['monthfee'];
    $manage= $_POST['manage'];
    $roomtitle= $_POST['roomtitle'];
    $photo1= $_POST['photo1'];
    $photo2= $_POST['photo2'];
    $photo3= $_POST['photo3'];
    $photo4= $_POST['photo4'];
    $photo5= $_POST['photo5'];
    $photo6= $_POST['photo6'];
    $content= $_POST['content'];

// UPDATE 쿼리
$sql = "UPDATE room SET host = '$host', adress = '$adress', detailAdress = '$detailAdress', housesort = '$housesort', sort_of_floor = '$sort_of_floor', num_of_floor = '$num_of_floor', area = '$area', roomcount = '$roomcount', toiletcount = '$toiletcount', options = '$options', sex = '$sex', age = '$age', smoke = '$smoke', animal = '$animal', enterdate = '$enterdate', leastdate = '$leastdate', gurantee = '$gurantee', monthfee = '$monthfee', manage = '$manage', roomtitle = '$roomtitle', photo1 = '$photo1', photo2 = '$photo2', photo3 = '$photo3', photo4 = '$photo4', photo5 = '$photo5', photo6 = '$photo6', content = '$content' WHERE roomid = '$roomid'";

if ($conn->query($sql) === TRUE) {
    echo "방 정보가 성공적으로 업데이트되었습니다.";
} else {
    echo "오류: " . $sql . "<br>" . $conn->error;
}

// 데이터베이스 연결 종료
$conn->close();
?>
