
document.addEventListener("DOMContentLoaded", function(event) {
    document.getElementById('greedName').addEventListener('keyup', function (event) {
        if(event.key === 'Enter'){
            greed();
        }
    });
});

function greed() {
    var greedName = document.getElementById('greedName').value;
    var gotoUrl = '/welcome/';
    gotoUrl += '?name=' + greedName;
    window.location.href = gotoUrl;
}
