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

include_once('auth/session_auth.php');
include_once('functions.php');

if (isset($_POST['function'])) {
    if ($_POST['function'] === "vehicleInfo") {
        $vehicleID = checkSQLSyntax(prep($_POST['vehicleID']));
        $vehicle = getVehicleInfo($vehicleID);

        foreach ($vehicle as $info) {
            echo $info . ";";
        }

    } else
        echo "function_error";
}