<?php
error_reporting(E_ALL);

echo "<h2>TCP/IP Connection</h2><br>";

/* Get the port for the WWW service. */
$service_port = 1234;

/* Get the IP address for the target host. */
$address = "255.255.255.255";

$out = shell_exec("echo -n \"discovery\" | socat - udp-datagram:255.255.255.255:1234");
var_dump($out);