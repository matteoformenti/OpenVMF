<div class="modal" id="conn-err">
    <div class="modal-content">
        <h3>Connection error</h3>
        <p>The host is disconnected or internet connection is down.</p>
    </div>
</div>
<div class="modal" id="about-us">
    <div class="modal-content">
        <h3>OpenVMF (Alpha 1)</h3>
        <p>Build: 2017-04-11</p>
        <p>Version: 0.0.10</p>
        <p>ITMakers © 2017 Copyright. openVMF is an ITMakers' project.</p>
    </div>
</div>

<div class="modal" id="vehicle-modal">
    <div class="modal-content">
        <h3 id="vehicle-name"></h3>

    </div>
    <div class="modal-footer">
        <button type="button" class="modal-action modal-close btn red waves-effect waves-light"
                style="margin-left: 10px;">Close
        </button>
        <button type="button" class="modal-action btn yellow accent-3 black-text waves-effect waves-ripple">Edit
        </button>
    </div>
</div>

<footer class="page-footer grey darken-3 no-padding">
    <div class="footer-copyright">
        <div class="container">
            ITMakers © 2017 Copyright. openVMF is an ITMakers' project.
            <a class="grey-text text-lighten-4 right" href="https://www.itmakers.org/">ITMakers Company</a>
        </div>
    </div>
</footer>

<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script src="js/materialize.min.js"></script>
<script>
    $(document).ready(function () {
        // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
        $('.modal').modal();
    });
</script>