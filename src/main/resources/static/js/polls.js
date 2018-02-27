$(document).ready(function () {
    var lastQuestion = 0;

    $(".multible_choice").click(function() {
        this.checked = $(this).is(":checked");
    });

    for(var i = 1; i < 4; i++) {
        $("#answers0").after(
            '<div class="row" id="answers'+i+'">\n' +
            '        <div class="input-field col 2">\n' +
            '            <input type="checkbox" class="filled-in multiple_choice" id="right_answer\'+i+\'"/>\n' +
            '            <label for="right_answer\'+i+\'">Right Answer</label>\n' +
            '        </div>\n' +
            '        <div class="input-field col 3">\n' +
            '            <input id="answer_text\'+i+\'" type="text" class="validate"/>\n' +
            '            <label for="answer_text\'+i+\'">Answer</label>\n' +
            '        </div>\n' +
            '    </div>')
    }
});
