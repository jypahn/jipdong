<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['file'])) {
    $file = $_FILES['file'];
    $uploadDir = 'uploads/';
    $count = 1;

    // 파일 이름 생성
    $fileName = $count . '_' . basename($file['name']);
    $targetPath = $uploadDir . $fileName;
    
    // 파일 이동
    if (move_uploaded_file($file['tmp_name'], $targetPath)) {
        // 파일 이동에 성공한 경우
        echo 'File uploaded successfully.';
        $count++;
    } else {
        // 파일 이동에 실패한 경우
        echo 'Error uploading file.';
    }
} else {
    // 올바른 요청이 아닌 경우
    echo 'Invalid request.';
}
?>