<?php
// �����ͺ��̽� ���� ����
$servername = "localhost";
$username = "capstone123";
$password = "qkrrjsdn2001!";
$dbname = "capstone123";

// �ȵ���̵� �ۿ��� ���۵� �α��� ���� ȸ���� uid�� �Էµ� ��й�ȣ �ޱ�
$uid = $_POST['uid'];
$passwordToCheck = $_POST['pw'];

// �����ͺ��̽� ����
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// ȸ�� ��й�ȣ Ȯ�� ���� ����
$sql = "SELECT uid, pw, salt FROM user WHERE uid = '$uid'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // ����� ������ �����ϴ� ���
  $row = $result->fetch_assoc();
  $stored_password = $row["pw"];
  $salt = $row["salt"];

  // �Է��� ��й�ȣ�� DB�� ����� ��й�ȣ ��
  $hash = hash('sha256', $userpw . $salt);
  if ($stored_password == $hash) {
    // ��й�ȣ�� ��ġ�ϴ� ���

    // �α��� ���� �޽��� ����
    echo "success";
  } else {
    // ��й�ȣ�� ��ġ���� �ʴ� ���
    echo "fail";
  }
} else {
  // ����� ������ �������� �ʴ� ���
  echo "no_user";
}

// ���� ����
$conn->close();
?>