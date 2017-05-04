<?php include_once('header.php'); ?>
<main>
    <div class="row">
        <div class="col s12 m4">
            <div class="card">
                <div class="card-content">
                    <h4 class="no-margin-top grey-text text-darken-3">Vehicles list</h4>
                    <br>
                    <?php
                    $queryVehicles = mysqli_query($conn, "SELECT * FROM vehicles;");
                    for ($i = 0; $i < $queryVehicles->num_rows; $i++) {
                        $vehicle = $queryVehicles->fetch_object();
                        ?>
                        <h5 class="light indigo-text">
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
                            </i> <?= $vehicle->name; ?></h5>
                        <table class="light-table">
                        <tbody>
                        <tr>
                            <td width="40%">Status</td>
                            <td>
                                <?php
                                switch ($vehicle->status) {
                                    case "offline":
                                        echo '<span class="amber-text uppercase medium-text">';
                                        break;
                                    case "online":
                                        echo '<span class="green-text uppercase medium-text">';
                                        break;
                                    case "warning":
                                        echo '<span class="red-text uppercase medium-text">';
                                        break;
                                    case "damage":
                                        echo '<span class="deep-orange-text uppercase medium-text">';
                                        break;
                                    default:
                                        echo '<span class="amber-text uppercase medium-text">';
                                        break;
                                }
                                echo $vehicle->status;
                                ?>
                                </span></td>
                        </tr>
                        <tr>
                            <td>IP</td>
                            <td id="vehicleIP"><?= $vehicle->lastIPAddress; ?></td>
                        </tr>
                        </tbody>
                    </table>
                        <?php
                        if ($i !== ($queryVehicles->num_rows - 1))
                            echo '<hr class="divider">';

                    } ?>
                </div>
            </div>
        </div>

        <div class="col s12 m8">
            <div class="row">
                <div class="col s12 m6">
                    <div class="card" style="height: 400px">
                        <div class="card-content">
                            <h6>FRONT_CAM</h6>
                        </div>
                    </div>
                </div>
                <div class="col s12 m6">
                    <div class="card" style="height: 400px" id="lidar">
                        <div class="card-content" onclick="getLIDAR()">
                            <h6>LIDAR</h6>
                        </div>
                    </div>
                </div>
                <div class="col s12">
                    <div class="card-panel">
                        <!-- Sminchia la pagina in mobile -->
                        <!--canvas onclick="getMap()" class="card-content" id="map" style="height: 400px;">
                        </canvas>-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<div class="modal" id="searchModal">
    <div class="modal-content">
        <h5 class="light indigo-text">Search Vehicles</h5>
        <input type="text" placeholder="Vehicle IP" id="vehicle_ip">
        <div class="btn-flat right blue white-text" onclick="checkConnection()">Connetti</div>
        <br>
    </div>
</div>
<?php include_once('footer.php'); ?>
<script src="js/main.js"></script>
