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

    $('#statistics').modal({
        dismissible: true, // Modal can be dismissed by clicking outside
        opacity: .5, // Opacity of modal background
        inDuration: 300, // Transition in duration
        outDuration: 200, // Transition out duration
        startingTop: '4%', // Starting top style attribute
        endingTop: '10%', // Ending top style attribute
        ready: function (modal, trigger) {
        }, // Callback for Modal open. Modal and trigger parameters available.
        complete: function () { // Callback for Modal close
        }
    });
});

function getQuestions() {
    $.getJSON(url + "/api/polls/" + pollId, function (data) {
        poll = data;
        //console.log(data);
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
    $template.find("a").first().attr("id", questionData.id);
    $template.find(".question-text").html(questionData.question);

    $template.find("a").first().on("click", onStatisticsButtonClick);

    $("#questions-wrapper").append($template);
    questionCount++;
}

function onStatisticsButtonClick(event) {
    $("#answers-wrapper").html("");

    var id = parseInt($(event.target).parent().attr("id"));

    var stats = [{answer: "oige", correct: true, percent: 73.5},
        {answer: "vale", correct: false, percent: 42}];

    // var stats = getStatsFromApiEndpointThatTerasHopefullyMakes(id)

    $.getJSON(url + "/api/sessions/statistics/" + $("#session_id").text(), function (data) {
        console.log(data);
        buildAnswers(data, id);
    });
}

function buildAnswers(stats, id) {

    for (var i = 0; i < stats.statistics.length; i++) {
        if (stats.statistics[i].id === id) {
            stats = stats.statistics[i].answers;
            break;
        }
    }

    for (var i = 0; i < poll.questions.length; i++) {
        if (poll.questions[i].id === id) {
            $("#modal-question").text(poll.questions[i].question);
        }
    }

    for (var i = 0; i < stats.length; i ++) {

        var $template = $("#statistics-answer-template").find("li").first().clone();

        $template.find(".answer-text").first().text(stats[i].answer);
        $template.find(".percent").first().text(stats[i].percentage.toString() + "%");

        if (stats[i].correct === true) { // as opposed to a string, for example
            $template.find(".percent").first().css("color", "#4caf50");
        }

        $("#answers-wrapper").append($template);
    }
}
