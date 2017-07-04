<?php
/**
 * This file is part of OpenVMF.
 *
 * OpenVMF is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenVMF is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenVMF.  If not, see <http://www.gnu.org/licenses/>.
 *
 * OpenVMF is an ITMakers' project. Please see our website <http://www.itmakers.org/>.
 */

function createConnection()
{
//     return new mysqli("10.0.0.4", "openvmf", "Task634Keep", "openvmf");
    return new mysqli("localhost", "root", "Task634Keep", "openVMF");
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

function getVehicleInfo($vehicleID)
{
    $query = queryDB("SELECT V.ID, V.name, V.type, V.status, V.IPAddress, V.serverPort, V.safetyControlTimeout, V.serialBaudRate, V.positionDelay, V.stopBits, V.parityBit, V.dataBits, userVehicles.addedDate FROM vehicles V JOIN userVehicles ON V.ID = userVehicles.vehicleID WHERE userID = '" . $_SESSION['userID'] . "' AND V.ID = '" . $vehicleID . "';");
    if ($query->num_rows > 0)
        return $query->fetch_object();
    else
        return "error";
}