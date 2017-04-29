<?php include_once('header.php'); ?>
<main>
    <div class="row">
        <div class="col s12 m4">
            <div class="card">
                <div class="card-content">
                    <h4 class="no-margin-top grey-text text-darken-3">Vehicles list</h4>
                    <br>
                    <h5 class="light indigo-text"><i class="material-icons left">directions_car</i> ToniSaraCar</h5>
                    <table>
                        <tbody>
                        <tr>
                            <td width="40%">Status</td>
                            <td><span class="green-text uppercase medium-text">operational</span></td>
                        </tr>
                        <tr>
                            <td>IP</td>
                            <td>10.0.0.1</td>
                        </tr>
                        </tbody>
                    </table>

                    <hr class="divider">

                    <h5 class="light indigo-text"><i class="material-icons left">flight</i> FormeTeoDrone</h5>
                    <table>
                        <tbody>
                        <tr>
                            <td width="40%">Status</td>
                            <td><span class="amber-text uppercase medium-text">offline</span></td>
                        </tr>
                        <tr>
                            <td>IP</td>
                            <td>10.0.0.1</td>
                        </tr>
                        </tbody>
                    </table>
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
                    <div class="card">
                        <canvas onclick="getMap()" class="card-content" id="map" style="height: 400px;">
                        </canvas>
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
