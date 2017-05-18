/**
 This file is part of OpenVMF.

 OpenVMF is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OpenVMF is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with OpenVMF.  If not, see <http://www.gnu.org/licenses/>.

 OpenVMF is an ITMakers' project. Please see our website <http://www.itmakers.org/>.
 */
let selectedIP = null;

$(document).ready(function () {
    $("#searchModal").modal({
        dismissible: true,
        opacity: .5,
        inDuration: 100,
        outDuration: 100
    });
    $('.collapsible').collapsible();
    $(".button-collapse").sideNav();
});

function init() {
    let vehicleName = $("h5").find("#vehicle-selection").find("input[type=radio]:checked").attr('id').split("-")[1];
    let selectedIP = $("tr").find("td#vehicleIP-" + vehicleName).text();

    window.setInterval(function () {
        selectedIP = getSelectedIP();
        getLIDAR(selectedIP);
        getMap(selectedIP);
    }, 1000);
}
//window.onload = init;

function getLIDAR(IP) {
    $("#lidar").find(".card-content").find("img").attr("src", 'http://' + IP + ':1235/getLIDAR');
}

function getMap(IP) {
    $("#map").find("img").attr("src", 'http://' + IP + ':1235/getMap');
}

function checkConnection() {
    console.log(selectedIP);
    $.ajax({
        url: "http://" + selectedIP + ":1234",
        type: 'post'
    }).done(function (data, statusText, xhr) {
        let status = xhr.status;                //200
        let head = xhr.getAllResponseHeaders(); //Detail header info
        console.log(status + "\n\r" + statusText);
    });
}

function discoverDrones() {
    let pane = $("#discoveredVehicles");
    pane.html("");
    $.ajax({
        url: "http://255.255.255.255:1234",   // Broadcast IP
        type: 'post'
    }).done(function (data, statusText, xhr) {
        let status = xhr.status;                //200
        let head = xhr.getAllResponseHeaders(); //Detail header info
        console.log(status + "\n\r" + statusText);

        pane.html("<h3>Yeah!</h3>");
    });
}

function getSelectedIP() {
    let vehicleName = $("h5").find("#vehicle-selection").find("input[type=radio]:checked").attr('id').split("-")[1];
    return $("tr").find("td#vehicleIP-" + vehicleName).text();
}

function openVehicleModal(vehicleID) {
    let vehicleContentDiv = $("#vehicle-modal").find(".modal-content");
    vehicleContentDiv.html("");
    $.post("post-functions.php", {
        function: 'vehicleInfo',
        vehicleID: vehicleID
    }).done(function (data) {
        let vehicleInfo = data.split(";");
        let vehicleIcon = "";
        switch (vehicleInfo[1]) {
            case "terrain":
                vehicleIcon = "directions_car";
                break;
            case "air":
                vehicleIcon = "flight";
                break;
            case "marine":
                vehicleIcon = "boat";
                break;
            default:
                vehicleIcon = "directions_car";
                break;
        }
        vehicleContentDiv.append("<h3><i class='material-icons right medium blue-text text-accent-3'>" + vehicleIcon + "</i> " + vehicleInfo[0] + " <span style='font-size: 45%' class='uppercase blue-text'>" + vehicleInfo[4] + "</span></h3>");
        vehicleContentDiv.append("<p class='no-margin'>Rotors number: " + vehicleInfo[2] + "</p>");
        vehicleContentDiv.append("<p class='no-margin'>Last IP Addr.: " + vehicleInfo[3] + "</p>");
        vehicleContentDiv.append("<p class='no-margin'>Vehicle added on: " + vehicleInfo[5] + "</p>");
        $("#vehicle-modal").modal('open');
    })
}