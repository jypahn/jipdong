<?php

// 파일이 업로드될 디렉토리 경로
$targetDir = "uploads/";

// 업로드된 파일의 정보를 받아옵니다.
$uploadedFiles = $_FILES['file'];

// 업로드된 파일들의 개수
$fileCount = count($uploadedFiles['name']);

// 업로드 결과를 담을 배열
$result = array();

// 각 파일별로 처리합니다.
for ($i = 0; $i < $fileCount; $i++) {
    $fileName = basename($uploadedFiles['name'][$i]);
    $targetPath = $targetDir . $fileName;

    // 파일을 지정된 경로로 이동합니다.
    if (move_uploaded_file($uploadedFiles['tmp_name'][$i], $targetPath)) {
        // 파일 업로드 성공
        $result[] = array(
            'success' => true,
            'message' => 'File uploaded successfully: ' . $fileName
        );
    } else {
        // 파일 업로드 실패
        $result[] = array(
            'success' => false,
            'message' => 'File upload failed: ' . $fileName
        );
    }
}

// 결과를 JSON 형태로 반환합니다.
header('Content-Type: application/json');
echo json_encode($result);
