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

include_once('header.php'); ?>
<main>
    <div class="row">
        <div class="col s12 m4">
            <div class="card">
                <div class="card-content">
                    <h4 class="no-margin-top grey-text text-darken-3">Vehicles list</h4>
                    <br>
                    <?php
                    $queryVehicles = mysqli_query($conn, "SELECT * FROM vehicles JOIN userVehicles ON vehicles.ID = userVehicles.vehicleID WHERE userID = '" . $_SESSION['userID'] . "';");
                    for ($i = 0; $i < $queryVehicles->num_rows; $i++) {
                        $vehicle = $queryVehicles->fetch_object();
                        ?>
                        <h5 class="light-blue-text text-accent-4">
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
                            </i> <?= $vehicle->name; ?>
                            <span class="right" id="vehicle-selection">
                                <input type="radio" name="selected" class="with-gap" id="select-<?= $vehicle->name; ?>"
                                    <?php if ($vehicle->status == "online") echo 'checked'; ?>>
                                <label for="select-<?= $vehicle->name; ?>">&nbsp;</label>
                            </span>
                        </h5>
                        <table class="light-table" id="vehicle-info">
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
                                <td id="vehicleIP-<?= $vehicle->name; ?>"><?= $vehicle->lastIPAddress; ?></td>
                            </tr>
                            </tbody>
                        </table>
                        <?php
                        if ($i !== ($queryVehicles->num_rows - 1))
                            echo '<hr class="divider">';

                    } ?>
                </div>
            </div>

            <!-- Discovery card -->
            <div class="card-panel">
                <h5>Discovery vehicles</h5>
                <button type="button" class="btn yellow accent-3 waves-effect waves-light black-text"
                        onclick="discoverDrones()">Start discovering
                </button>
                <article id="discoveredVehicles">

                </article>
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
                    <div class="card" id="lidar">
                        <div class="card-content no-padding" style="line-height: 0">
                            <img src="img/default.png" width="100%" alt="LIDAR image" title="LIDAR" id="img-lidar">
                        </div>
                    </div>
                </div>
                <div class="col s12">
                    <div class="card-panel no-padding" style="line-height: 0" id="map">
                        <img src="img/default.png" width="100%" alt="Map image" title="Map" id="img-map">
                        <!-- Sminchia la pagina in mobile -->
                        <!--canvas onclick="getMap()" class="card-content" id="map" style="height: 400px;">
                        </canvas>-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<input type="hidden" id="selectedIP">
<div class="modal" id="searchModal">
    <div class="modal-content">
        <h5 class="light-blue-text text-accent-4">Search Vehicles</h5>
        <input type="text" placeholder="Vehicle IP" id="vehicle_ip">
    </div>
    <div class="modal-footer">
        <div class="btn yellow accent-3 waves-effect waves-light black-text" onclick="checkConnection()">Connetti</div>
    </div>
</div>
<?php include_once('footer.php'); ?>
<script src="js/main.js"></script>
