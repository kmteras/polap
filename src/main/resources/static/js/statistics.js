document.addEventListener("DOMContentLoaded", function(event) {
    getRequest("/api/requests/browsers", makePie, 'chart0');
    getRequest("/api/requests/oss", makePie, 'chart1');
    //getRequest("/api/requests/dates", makeHistogram);
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
