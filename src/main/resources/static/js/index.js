$(document).ready(function () {
    $("#join-button").click(function() {
        joinSession();
    });
});

function joinSession() {
    var sessionCode = $("#session_code")[0].value;
    window.location.href = "/session/" + sessionCode;
}
