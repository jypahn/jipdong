<?php
$db_name = "capstone123";       // DB name
$username = "capstone123";      // DB username
$password = "qkrrjsdn2001!";    // MySQL password
$servername = "localhost";      // Server name, in this case, localhost
  
// Create a connection
$conn = mysqli_connect($servername, $username, $password, $db_name);

// Check the connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Fetch the data from the database
$sql = "SELECT * FROM your_table_name";
$result = mysqli_query($conn, $sql);

// Check if any rows are returned
if (mysqli_num_rows($result) > 0) {
    // Loop through the rows and fetch the data
    while ($row = mysqli_fetch_assoc($result)) {
        $id = $row['id'];
        $location = $row['location'];
        $hopeArea = $row['hope_area'];
        $startDate = $row['start_date'];
        $startPeriod = $row['start_period'];
        $endDate = $row['end_date'];
        $endPeriod = $row['end_period'];
        $content = $row['content'];
        $detail = $row['detail'];
        $uid = $row['uid'];
        $writer = $row['Writer'];
        $writeDate = $row['writedate'];

        // Output the data
        echo "ID: $id<br>";
        echo "Location: $location<br>";
        echo "Hope Area: $hopeArea<br>";
        echo "Start Date: $startDate<br>";
        echo "Start Period: $startPeriod<br>";
        echo "End Date: $endDate<br>";
        echo "End Period: $endPeriod<br>";
        echo "Content: $content<br>";
        echo "Detail: $detail<br>";
        echo "UID: $uid<br>";
        echo "Writer: $writer<br>";
        echo "Write Date: $writeDate<br>";
        echo "<br>";
    }
} else {
    echo "No data found.";
}

// Close the connection
mysqli_close($conn);
?>