<?php
    // 클라이언트로부터 전송된 데이터를 받습니다.
    $text = $_POST['text'];
    
    // 메시지를 기록할 파일 경로를 설정합니다.
    $file_path = 'message.txt';
    
    // 파일에 메시지 내용과 보낸 시간을 기록합니다.
    $fp = fopen($file_path, 'a');
    fwrite($fp, date('Y-m-d H:i:s') . "\t" . $text . "\n");
    fclose($fp);
?>