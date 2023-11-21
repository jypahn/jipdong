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
$loginUid = $_POST['uid']; // 로그인한 사용자의 uid
$otherUids = [];
$selectUidsQuery = "SELECT DISTINCT uid FROM survey WHERE uid != ?";
$stmt = $pdo->prepare($selectUidsQuery);
$stmt->execute([$loginUid]);
if ($stmt->rowCount() > 0) {
    // 결과 처리
    $similarities = [];
    
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $uid = $row["uid"];
        $otherUids[] = $uid;

        $loginUserSurvey = getSurveyValue($loginUid);
        $otherUserSurvey = getSurveyValue($uid);

        $similarity = calculateJaccardSimilarity($loginUserSurvey, $otherUserSurvey);
        
        $similarities[$uid] = $similarity;
    }
    
    // 유사도 값을 기준으로 내림차순 정렬
    arsort($similarities);
    
    // 유사도가 가장 높은 사람과 그 다음으로 높은 사람을 가져오기
    $highestSimilarityUser = key($similarities);
    $highestSimilarity = $similarities[$highestSimilarityUser];
    next($similarities); // 다음으로 이동
    $secondHighestSimilarityUser = key($similarities);
    $secondHighestSimilarity = $similarities[$secondHighestSimilarityUser];
    
    // 결과를 JSON 형식으로 반환
    $result = [
        'highestSimilarityUser' => $highestSimilarityUser,
        'highestSimilarity' => $highestSimilarity,
        'secondHighestSimilarityUser' => $secondHighestSimilarityUser,
        'secondHighestSimilarity' => $secondHighestSimilarity,
    ];

    header('Content-Type: application/json');
    echo json_encode($result);
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
