var map;
var locale;

document.addEventListener("DOMContentLoaded", function(event) {
    getRequest("/api/requests/browsers", makePie, 'chart0');
    getRequest("/api/requests/oss", makePie, 'chart1');
    //getRequest("/api/requests/dates", makeHistogram);

    locale = $("html").attr("lang");

    initializeMap();
});

function getRequest(path, graphFunction, div) {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                graphFunction(JSON.parse(xmlhttp.responseText), div);
            }
        }
    };

    xmlhttp.open("GET", path, true);
    xmlhttp.send();
}

function initializeMap() {
    map = L.map("mapid").setView([49.069, 54.411], 2);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png' +
        '?access_token={accessToken}', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap' +
        '</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">' +
        'CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
        maxZoom: 7,
        minZoom: 1,
        id: "mapbox.streets",
        accessToken: "pk.eyJ1IjoiYW5kcmVhc3ZpamEiLCJhIjoiY2pnNDUyOGlwMDR0ejMzcXN0a2dmdHN2OCJ9.k0qwePCv-CJgfwFIzK3tug"
    }).addTo(map);

    var netherlands = L.marker([52.271, 5.756]).addTo(map);
    var estonia = L.marker([58.850, 25.333]).addTo(map);
    var russia = L.marker([62.291, 86.047]).addTo(map);
    var china = L.marker([33.786, 103.391]).addTo(map);
    var romania = L.marker([45.859, 24.848]).addTo(map);

    if (locale === "et") {
        netherlands.bindPopup("Holland");
        estonia.bindPopup("Eesti");
        russia.bindPopup("Venemaa");
        china.bindPopup("Hiina");
        romania.bindPopup("Rumeenia");
    }
    else {
        netherlands.bindPopup("Netherlands");
        estonia.bindPopup("Estonia");
        russia.bindPopup("Russia");
        china.bindPopup("China");
        romania.bindPopup("Romania");
    }
}
