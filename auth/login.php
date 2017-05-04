<?php
include_once("../functions.php");

session_start(); // Starting Session
if (empty($_POST['username']) || empty($_POST['password'])) {
    echo "empty";
} else {
    // Define $username and $password
    $username = checkSQLSyntax(prep($_POST['username']));
    $password = checkSQLSyntax(prep($_POST['password']));

    // Establishing Connection with Server by passing server_name, user_id and password as a parameter
    $query = queryDB("SELECT ID FROM users WHERE password='" . hashPassword($password) . "' AND username='$username'");

    $rows = mysqli_num_rows($query);
    if ($rows == 1) {
        $_SESSION['userID'] = $query->fetch_assoc()["ID"]; // Initializing Session
        echo "ok";
    } else
        echo "error";
}
return;


