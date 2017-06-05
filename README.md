<h1>Progetto ADP 2017</h1>
<hr>
<h5>Project structure</h5>
<ul>
    <li><b>/src</b> Project source code
        <ul>
            <li><b>/openvmf</b> sources for the client
            <ul>
                <li><b>/com</b> communications services
                    <ul>
                        <li><b>ControlServer</b> UDP server for manual control
                        <li><b>DB</b> DB communication services
                        <li><b>DiscoveryService</b> Discovery service used in the server
                        <li><b>SerialDiscovery</b> Find, identify and connect to all serial devices
                        <li><b>ServerConnection</b> HTTP server used for parseing and executing web based commands
                    </ul>
                </li>
                <li><b>/nav</b> navigation services
                    <ul>
                        <li><b>EngineService</b> Service used to control steer and engine via serial communication
                        <li><b>LidarService</b> Service used to retrieve LIDAR data 
                        <li><b>LocationService</b> Service used to positionthe vehicle in the 2D space
                    </ul>
                </li>
                <li><b>Logger</b> Logging service
                <li><b>Settings</b> Static settings stored in the DBMS
                <li><b>Main</b> Entry point for the application
            </ul>
            </li>
        </ul>
    </li>
    <li><b>/ino</b> Arduino sketches
            <ul>
                <li><b>/engine</b> engine controller
                <li><b>/lidar</b> lidar controller
                <li><b>/location</b> positioning controller
            </ul>
        </li>
</ul>
