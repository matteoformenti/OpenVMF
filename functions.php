<?php
function createConnection()
{
    return new mysqli("localhost", "root", "meT@416Uly", "openVMF");
}

function queryDB($query)
{
    $db = createConnection();
    if (!$db)
        die("DB connection error");
    $db->set_charset("utf8");
    $res = mysqli_query($db, $query);
    $db->close();
    return $res;
}

function getEncryptionString()
{
    return "UQwQ1MVgxVfjkbwTw0YsiYhz8kKsL1NAzxeM5aBRmwucrzRWbsLHKbzMorsCrTB";
}

function hashPassword($password)
{
    $salt = substr(strtr(base64_encode(getEncryptionString()), '+', '.'), 0, 22);
    return crypt($password, '$2x$12$' . $salt);
}

function generatePassword($length = 12)
{
    $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*+?";
    $password = substr(str_shuffle($chars), 0, $length);
    return $password;
}

function getCopyrightYear()
{
    echo date("Y");
}

function checkExistSession()
{
    if (session_status() == PHP_SESSION_NONE)
        return false;
    else
        return true;
}

function prep($in)
{
    return preg_replace('/"|\'/', "", $in);
}

function checkSQLSyntax($in)
{
    $keywords = array("ADD", "ALL", "ALTER", "DROP", "TABLE", "AND", "OR", "TRUNCATE", "SELECT", "FROM", "1=1", "\" or \"\"=\"");
    $phrase = "";
    foreach (explode(" ", $in) as $word) {
        if (!in_array($word, $keywords))
            $phrase .= $word . " ";
    }
    return rtrim($phrase, " ");
}