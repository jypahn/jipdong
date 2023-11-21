<?php
$host = "localhost";
$db   = "capstone123";
$user = "capstone123";
$pass = "qkrrjsdn2001!";
$charset = 'utf8mb4';

// 데이터베이스 연결 설정
$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$options = [
    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES => false,
];

try {
    $pdo = new PDO($dsn, $user, $pass, $options);
} catch (\PDOException $e) {
    throw new \PDOException($e->getMessage(), (int)$e->getCode());
}

// uid 값 확인
$uid = $_POST['uid']; // uid 값을 POST 요청에서 받음

$sql = "SELECT COUNT(*) AS count FROM survey WHERE uid = ?";
$stmt = $pdo->prepare($sql);
$stmt->execute([$uid]);

if ($stmt && $stmt->rowCount() > 0) {
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    $count = (int)$row['count'];

    // 값의 여부에 따라 결과 반환
    echo ($count > 0) ? 'true' : 'false';
} else {
    echo 'false';
}
?>
