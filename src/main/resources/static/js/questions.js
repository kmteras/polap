$("#questionsForm").submit(function(event) {
    event.preventDefault();

    $.ajax({
        type: "post",
        url: $(this).attr("action"),
        data: $('#questionsForm').serialize(),
        contentType: "application/x-www-form-urlencoded",
        success: function(responseData, textStatus, jqXHR) {
            window.location.reload(true);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
});
