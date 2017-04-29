<?php
include_once($_SERVER['DOCUMENT_ROOT'] . "/openVMF/functions.php");

session_start();
$user_check = $_SESSION['userID'];

$ses_sql = queryDB("SELECT ID FROM users WHERE ID='$user_check'");

$login_session = $ses_sql->fetch_object()->ID;
if (!isset($login_session)) {
    header('Location: ' . "/openVMF/");
}