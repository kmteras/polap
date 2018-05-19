var poll;
var pollId;
var token;
var header;
var url;
var locale;

$(document).ready(function () {
    pollId = $("#poll_id").attr("content");
    token = $('#_csrf').attr('content');
    header = $('#_csrf_header').attr('content');
    url = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');

    getQuestions();

    locale = $("html").attr("lang");
});

function getQuestions() {
    $.getJSON(url + "/api/polls/" + pollId, function (data) {
        poll = data;
        console.log(data);
        buildQuestions();
    })
}

function buildQuestions() {
    var $questions_wrapper = $("#questions-wrapper");
    $questions_wrapper.html("");

    for (var i = 0; i < poll["questions"].length; i++) {
        addQuestion(poll["questions"][i])
    }
}

var questionCount = 0;

function addQuestion(questionData) {
    var $template = $("#questions-question-template").find("li").first().clone();

    if (questionData.htmlId === undefined) {
        questionData.htmlId = questionCount;
    }

    $template.attr("id", "question_row_" + questionData.htmlId);
    $template.find(".question-text").html(questionData.question);

    $("#questions-wrapper").append($template);
    questionCount++;
}
