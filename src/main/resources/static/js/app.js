var cookieString = "PolapCookieConsent=True";

$(document).ready(function() {
    $("#language_dropdown_button").dropdown();
    $("#user_dropdown_button").dropdown();

    var cookies = document.cookie;
    if (cookies.indexOf(cookieString) === -1) {
        // cookie does not exist
        $("#cookies-button").click(saveConsentCookie);
        $("#cookies-bar").css("display", "block");
    }
});

function saveConsentCookie() {
    document.cookie = cookieString;
    $("#cookies-bar").css("display", "none");
}
