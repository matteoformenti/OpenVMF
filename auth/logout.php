<?php
include_once("../functions.php");
session_start();
if (session_destroy()) // Destroying All Sessions
{
    header('Location: /');
}