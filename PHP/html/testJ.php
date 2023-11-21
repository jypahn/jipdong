<?php
function calculateJaccardSimilarity($str1, $str2) {
    $set1 = str_split($str1); // 첫 번째 문자열을 문자 배열로 분할
    $set2 = str_split($str2); // 두 번째 문자열을 문자 배열로 분할
    
    $intersection = count(array_intersect($set1, $set2));
    $union = count(array_unique(array_merge($set1, $set2)));
    
    if ($union == 0) {
        return 0; // 예외 처리: 두 문자열이 모두 빈 문자열인 경우
    }
    
    return $intersection / $union;
}

// 예제 사용법
$string1 = "Hello";
$string2 = "Holla";

$similarity = calculateJaccardSimilarity($string1, $string2);
echo "Jaccard 유사도: " . $similarity;
?>