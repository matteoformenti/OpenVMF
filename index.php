<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - OpenVMF CC</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href="css/icons.css" rel="stylesheet" type="text/css">
    <link href="css/materialize.min.css" type="text/css" rel="stylesheet" media="screen,projection"/>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<main class="grey lighten-3 valign-wrapper">
    <div class="row container">
        <div class="col s12 m6 push-m3">
            <div class="card-panel center center-block">
                <h1 class="center-align">OpenVMF Login</h1>
                <p class="red-text medium-text" id="errorMessage"></p>
                <div class="input-field">
                    <input type="text" placeholder="Username" id="username">
                </div>
                <div class="input-field">
                    <input type="password" placeholder="Password" id="password">
                </div>
                <button type="submit" class="btn waves-effect blue" id="submit">login</button>
            </div>
        </div>
    </div>
</main>

<?php include_once('footer.php'); ?>
<script>
    $(document).ready(function () {
        $("main").on("keypress", function (e) {
            if (e.keyCode === 13)
                login();
        });

        $("#submit").on("click", function () {
            login();
        });
    });

    function login() {
        let a = $("#username"), b = $("#password");
        "" !== a.val() && "" !== a.val() ? $.post("auth/login.php", {
            username: a.val(),
            password: b.val()
        }, function (a) {
            "ok" === a ? location.href = "dashboard.php" : ($("#errorMessage").text("Wrong username and/or password!"), $("#errorParent").show())
        }) : ($("#errorMessage").text("Username and/or password fields are empty!"), $("#errorParent").show())
    }
</script>
