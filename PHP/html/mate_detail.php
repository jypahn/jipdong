<?php
$db_name = "capstone123";       // DB name
$username = "capstone123";      // DB username
$password = "qkrrjsdn2001!";    // MySQL password
$servername = "localhost";      // Server name, in this case, localhost
  
$conn = mysqli_connect($servername, $username, $password, $db_name);
  
if ($conn) {
    echo "Login success";
} else {
    echo "Login fail";
}

// Retrieve POST data
$id = $_POST['id'];
$location = $_POST['location'];
$start_date = $_POST['start_date'];
$end_date = $_POST['end_date'];
$content = $_POST['content'];
$writer = $_POST['writer'];

// Create a connection to MySQL
$host = 'localhost';
$dbUsername = 'your_username';
$dbPassword = 'your_password';
$dbName = 'your_database_name';

$conn = new mysqli($host, $dbUsername, $dbPassword, $dbName);

// Check the connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Prepare and execute the SQL query
$stmt = $conn->prepare("INSERT INTO roommate (location, start_date, end_date, content, writer) VALUES ($id,$location,$start_date,$end_date,$content,$writer)");
$stmt->bind_param("sssss", $location, $start_date, $end_date, $content, $writer);
$stmt->execute();

if ($stmt->affected_rows > 0) {
    echo "Data inserted successfully.";
} else {
    echo "Error: " . $stmt->error;
}

// Close the statement and the connection
$stmt->close();
$conn->close();
?>