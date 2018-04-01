var token;
var header;

$(document).ready(function () {
    token = $('#_csrf').attr('content');
    header = $('#_csrf_header').attr('content');

    $(".delete-button").each(function(i, button) {
        var $button = $(button);
        var id = $button.attr("data-id");
        $button.click(function() {
            deletePoll(id);
        })
    });
});

function deletePoll(id) {
    var url = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');

    $.ajax({
            url: url + "/api/polls/" + id,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            type: "DELETE",
            success: function (msg) {
                $("#poll_" + id).remove();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest);
            }
        }
    );
}
