$(document).ready(function () {
    console.log("test");
    $("#multible_choice").click(function() {
        this.checked = $(this).is(":checked");
    });
});
