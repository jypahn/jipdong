<?php
session_start(); // 세션 시작

// 세션에 유저 ID 저장
$_SESSION['user_id'] = 123;

// 세션 값 반환
echo json_encode(array('session_id' => session_id()));
?>
