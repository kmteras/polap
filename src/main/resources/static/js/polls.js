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

    $(".host-button").each(function(i, button) {
        var $button = $(button);
        var id = $button.attr("data-id");
        $button.click(function() {
            hostPoll(id);
        })
    });

    $(".reconnect-session-button").each(function(i, button) {
        var $button = $(button);
        var code = $button.attr("data-id");
        $button.click(function() {
            reconnectPoll(code);
        })
    });

    $(".end-session-button").each(function(i, button) {
        var $button = $(button);
        var code = $button.attr("data-id");
        $button.click(function() {
            endSession(code);
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

function reconnectPoll(code) {
    window.location.href = "/session/" + code;
}

function endSession(code) {
    window.location.href = "/session-end/" + code;
}

function hostPoll(id) {
    window.location.href = "/session-host/" + id;
}
