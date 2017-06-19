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

    adjustCompassImgs();
});

function init() {
    let vehicleName = $("h5").find("#vehicle-selection").find("input[type=radio]:checked").attr('id').split("-")[1];
    let selectedIP = $("tr").find("td#vehicleIP-" + vehicleName).text();

    window.setInterval(function () {
        selectedIP = getSelectedIP();
        getLIDAR(selectedIP);
        getMap(selectedIP);
    }, 1000);

    window.setInterval(function () {
        selectedIP = getSelectedIP();
        getImage(selectedIP);
    }, 1000);
}
//window.onload = init;

function getLIDAR(IP) {
    $("#lidar").find(".card-content").find("img").attr("src", 'http://' + IP + '/getLIDAR');
}

function getMap(IP) {
    $("#map").find("img").attr("src", 'http://' + IP + '/getMap');
}

function getImage(IP) {
    $("#cam").find("img").attr("src", 'http://' + IP + '/getImage');
}

function adjustCompassDirection(IP) {
    let deg = 0;
    $.get('http://' + IP + '/getCompass').done(function (data) {
        console.log(data);
    });
    $("#compass-arrow-img").css("transform", "rotate(" + deg + "deg)");
}

function checkConnection() {
    let ipList = new Array;

    $.post("post-functions.php", {
        function: "checkConnection",
        IPs: ipList
    }).done(function (data) {
        if (data != "ok")
            console.log(data);
    });
}

function searchVehicle() {
    let vehicleIP = $("#vehicle_ip").val();
    $.get("http://" + vehicleIP + ":1235/getSettings").done(function (data) {
        $.post("post-functions.php", {
            function: "insertNewVehicle",
            data: data
        }).done(function (data) {
            console.log(data);
        })
    });
}

function discoverDrones() {
    let pane = $("#discoveredVehicles");
    pane.html("");
    $.post("post-functions.php", {
        function: 'discovery',
    }).done(function (data) {
        if (data != "") {
            let info = data.split("\n");
            for (let i in info)
                pane.append("<p class='no-margin-bot'>" + info[i] + "</p>");
        }
        else
            pane.html("<p class='no-margin-bot'>No vehicles found.</p>");
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
        let vehicleName = "", vehicleIcon = "", vehicleTable = "";
        for (let index in vehicleInfo) {
            let splitVehicle = vehicleInfo[index].split("§");
            if (splitVehicle[0] === "Name")
                vehicleName = splitVehicle[1];
            else if (splitVehicle[0] === "Type") {
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
            }
            else if (splitVehicle[0] === "Status") {
                vehicleContentDiv.append("<h3 class='no-margin'><i class='material-icons right medium blue-text text-accent-3'>" + vehicleIcon + "</i> " + vehicleName + " <span style='font-size: 45%' class='uppercase blue-text'>" + splitVehicle[1] + "</span></h3>");
            }
            else
                vehicleTable += "<tr><td>" + splitVehicle[0] + "</td><td>" + splitVehicle[1] + "</td></tr>";
        }
        vehicleContentDiv.append("<table class='responsive-table striped'><thead><tr><th>Property</th><th>Value</th></tr></thead><tbody>" + vehicleTable + "</tbody></table>");
        $("#vehicle-modal").modal('open');
    })
}

function adjustCompassImgs() {
    let compassImg = $("#compass-img");
    let compassArrowImg = $("#compass-arrow-img");
    let titleCompass = $("#direction").find("h5");

    compassArrowImg.width(compassImg.height() * 47 / 455);
    let marginTop = compassArrowImg.height() + (compassImg.height() / 2 - compassArrowImg.height() / 2) + 5;
    let marginLeft = compassImg.width() / 2 - compassArrowImg.width() / 2;
    compassArrowImg.css("margin-top", "-" + marginTop + "px");
    compassArrowImg.css("margin-left", marginLeft + "px");
}