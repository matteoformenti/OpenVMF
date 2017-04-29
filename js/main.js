/**
 * Created by matteo on 11/04/17.
 */
$(".button-collapse").sideNav();

function getImage() {

}

function getLIDAR() {
    console.log("ciao");
    $("#lidar").css("background-image", "url(http://10.3.1.163:1235/getLIDAR)");
}

function getMap() {

}

$(document).ready(function () {
    $("#searchModal").modal({
            dismissible: true,
            opacity: .5,
            inDuration: 100,
            outDuration: 100
        }
    );
    $('.collapsible').collapsible();
});

function checkConnection() {
    let ip = $("#searchModal").find("#vehicle_ip").val();
    console.log(ip);
    $.get("http://" + ip, function (data) {
        console.log(data);
        data = data.split("\n");
        if (data[0] === "OK")
            alert("Connection successful");
    })
}