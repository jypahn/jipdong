<?php
$db_name = "capstone123";       // DB name
$username = "capstone123";      // DB username
$password = "qkrrjsdn2001!";    // MySQL password
$servername = "localhost";      // Server name, in this case, localhost
  
// Create a connection to MySQL
$conn = new mysqli($servername, $username, $password, $db_name);

// Check the connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Prepare and execute the SQL query to fetch the list of posts
$stmt = $conn->prepare("SELECT * FROM posts");
$stmt->execute();

// Get the result
$result = $stmt->get_result();
if ($result->num_rows > 0) {
    // Create an array to store the post data
    $posts = array();
    while ($row = $result->fetch_assoc()) {
        // Add each post to the array
        $posts[] = $row;
    }
    // Convert the post data to JSON or other desired format
    $jsonData = json_encode($posts);
    // Send the response back to the client
    echo $jsonData;
} else {
    echo "No posts found.";
}

// Close the statement and the connection
$stmt->close();
$conn->close();
?>