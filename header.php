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
<body>
<nav class="light-blue accent-4" style="margin-bottom: 10px;">
    <div class="nav-wrapper">
        <a href="#" data-activates="side-menu" class="button-collapse show-on-medium-and-up"><i class="material-icons">menu</i></a>
        <a href="#" class="brand-logo light">OpenVMF Control Center</a>
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
        <li><a class="subheader">Vehicles</a></li>
        <?php
        $queryVehicles = mysqli_query($conn, "SELECT * FROM vehicles JOIN userVehicles ON vehicles.ID = userVehicles.vehicleID WHERE userID = '" . $_SESSION['userID'] . "';");
        for ($i = 0;
        $i < $queryVehicles->num_rows;
        $i++) {
        $vehicle = $queryVehicles->fetch_object();
        ?>
        <li><a href="#" onclick="openVehicleModal(<?= $vehicle->ID; ?>)">
                <i class="material-icons left">
                    <?php
                    switch ($vehicle->type) {
                        case "terrain":
                            echo "directions_car";
                            break;

                        case "air":
                            echo "flight";
                            break;

                        case "marine":
                            echo "boat";
                            break;

                        default:
                            echo "directions_car";
                            break;
                    }
                    ?>
                </i> <?= $vehicle->name; ?></a>
            <?php
            } ?>
            <!--<li><a href="#"><i class="material-icons left">directions_car</i> ToniSaraCAR</a></li>-->
        <li>
            <div class="divider"></div>
        </li>
        <li><a class="subheader">Functions</a></li>
        <li><a class="waves-effect waves-light" href="#searchModal"
               onclick="$('.button-collapse').sideNav('hide');"><i
                        class="material-icons left">location_searching</i> Search Vehicles</a></li>
        <li>
            <div class="divider"></div>
        </li>
        <li><a class="subheader">Settings</a></li>
        <li><a href="#"><i class="material-icons left">account_circle</i> My Account</a></li>
        <li><a href="#about-us"><i class="material-icons left">info</i> About Us</a></li>
        <li>
            <div class="divider"></div>
        </li>
        <li><a href="auth/logout.php" class="btn yellow accent-3 waves-effect waves-light black-text">log out</a></li>
    </ul>
</nav>