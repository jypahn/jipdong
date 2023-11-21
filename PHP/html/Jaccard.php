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

// 로그인한 사용자의 uid와 다른 사용자들의 uid 가져오기
$loginUid = "Tom"; // 로그인한 사용자의 uid
$otherUids = [];
$selectUidsQuery = "SELECT DISTINCT uid FROM survey WHERE uid != ?";
$stmt = $pdo->prepare($selectUidsQuery);
$stmt->execute([$loginUid]);
if ($stmt->rowCount() > 0) {
    // 결과 처리
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $uid = $row["uid"];
        $otherUids[] = $uid;
    }
}

// 로그인한 사용자와 다른 사용자들 간의 유사도를 분석
foreach ($otherUids as $otherUid) {
    $loginUserSurvey = getSurveyValue($loginUid);
    $otherUserSurvey = getSurveyValue($otherUid);

    $similarity = calculateJaccardSimilarity($loginUserSurvey, $otherUserSurvey);

    echo "Similarity between $loginUid and $otherUid: " . number_format($similarity, 2) . "\n";
}

// 사용자의 설문조사 결과를 가져오는 함수
function getSurveyValue($uid) {
    global $pdo;

    $selectQuery = "SELECT DISTINCT answer FROM survey WHERE uid = ?";

    $stmt = $pdo->prepare($selectQuery);
    $stmt->execute([$uid]);

    $results = $stmt->fetchAll(PDO::FETCH_COLUMN);

    return $results;
}



// Jaccard 유사도 계산
function calculateJaccardSimilarity($setA, $setB) {
    $intersection = count(array_intersect($setA, $setB));
    $union = count(array_unique(array_merge($setA, $setB)));

    if ($union == 0) {
        return 0.0;
    } else {
        $similarity = $intersection / $union;
        return $similarity;
    }
}



?>