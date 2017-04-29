<?php include_once('auth/session_auth.php');
$conn = createConnection();

$query_user = mysqli_query($conn, "SELECT username, email FROM users WHERE ID = '" . $_SESSION['userID'] . "';");
$user_obj = $query_user->fetch_object();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>OpenVMF CC</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href="css/icons.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="grey darken-2">
<nav class="indigo">
    <div class="nav-wrapper">
        <a href="#" class="brand-logo light"><i class="material-icons button-collapse"
                                                data-activates="side-menu">menu</i>OpenVMF
            Control Center</a>
    </div>
    <ul id="side-menu" class="side-nav">
        <li>
            <div class="userView">
                <div class="background">
                    <img src="img/background.jpg" style="width: 100%">
                </div>
                <a href="#"><i class="material-icons large white-text" style="font-size: 6rem">person</i></a>
                <a href="#"><span class="white-text name"><?= $user_obj->username ?></span></a>
                <a href="#"><span class="white-text email"><?= $user_obj->email ?></span></a>
            </div>
        </li>
        <li class="vehicle"><a href="#"><i class="material-icons">directions_car</i>ToniSaraCAR</a></li>
        <li>
            <div class="divider"></div>
        </li>
        <li><a class="waves-effect waves-light" href="#searchModal"
               onclick="$('.button-collapse').sideNav('hide');"><i
                        class="material-icons left">location_searching</i> Search Vehicles</a></li>
        <li>
            <div class="divider"></div>
        </li>
        <li><a class="subheader">Settings</a></li>
        <li><a href="#"><i class="material-icons left">account_circle</i> My Account</a></li>
        <li><a href="#about-us"><i class="material-icons left">info</i> About Us</a></li>
    </ul>
</nav>