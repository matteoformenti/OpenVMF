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

if (isset($_POST['function']))
{
    if ($_POST['function'] === "vehicleInfo")
    {
        $vehicleID = checkSQLSyntax(prep($_POST['vehicleID']));
        $vehicle = getVehicleInfo($vehicleID);

        // $out = "ID§" . $vehicle->ID . ";";
        $out = "Name§" . $vehicle->name . ";";
        $out .= "Type§" . $vehicle->type . ";";
        $out .= "Status§" . $vehicle->status . ";";
        $out .= "IP Address§" . $vehicle->IPAddress . ";";
        $out .= "Server Port§" . $vehicle->serverPort . ";";
        $out .= "Safety Control Timeout§" . $vehicle->safetyControlTimeout . ";";
        $out .= "Serial Baud Rate§" . $vehicle->serialBaudRate . ";";
        $out .= "Position Delay§" . $vehicle->positionDelay . ";";
        $out .= "Stop bits§" . $vehicle->stopBits . ";";
        $out .= "Parity bit§" . $vehicle->parityBit . ";";
        $out .= "Data bits§" . $vehicle->dataBits . ";";
        $out .= "Added date§" . $vehicle->addedDate;
        echo $out;

        die();

    } else if ($_POST['function'] === "discovery")
    {
        $out = shell_exec("echo -n \"discovery\" | socat - udp-datagram:255.255.255.255:1234,broadcast");
        echo $out;
        die();
    } else if ($_POST['function'] === "insertNewVehicle")
    {
        $array = [];
        $data = explode("\n", $_POST['data']);
        foreach ($data as $datum)
        {
            if ($datum !== "Settings:" && $datum !== "")
            {
                $exploded_rows = explode(";", $datum);
                $array[$exploded_rows[0]] = $exploded_rows[1];
            }
        }

        $conn = createConnection();
        $query_check = mysqli_query($conn, "SELECT ID FROM vehicles WHERE name='" . $array['VehicleName'] . "';");
        if ($query_check->num_rows == 0)
        {
            $query = mysqli_query($conn, "INSERT INTO vehicles (name, type, IPAddress, status, safetyControlTimeout, serialBaudRate, positionDelay, stopBits, parityBit, dataBits, discoveryPort, controlPort, cameraPort, serverPort)
                         VALUES('" . $array['VehicleName'] . "', '" . $array['VehicleType'] . "', '" . $array['VehicleType'] . "', '$ip', 'online',
                         '" . $array['SafetyControlTimeout'] . "', '" . $array['SerialBaudRate'] . "', '" . $array['PositionDelay'] . "', '" . $array['StopBits'] . "',
                         '" . $array['Parity'] . "', '" . $array['DataBits'] . "', '" . $array['DiscoveryPort'] . "', '" . $array['ControlPort'] . "', '" . $array['CameraUDPPort'] . "', '" . $array['ServerConnectionPort'] . "');");
            echo $query;
        } else
            echo "already_exist";

        die();

    } else
        echo "function_error";
}