var questions = [
    {
        "id": 0,
        "question": "First Question?",
        "answers": [
            {"text": "First Text", "correct": false},
            {"text": "Second Text", "correct": true},
            {"text": "Third Text", "correct": false}]
    },
    {
        "id": 1,
        "question": "Second Question?",
        "answers": [
            {"text": "Only Choice", "correct": true}]
    }
];

var pollId;
var token;
var header;

$(document).ready(function () {
    pollId = $("#poll_id").attr("content");
    token = $('#_csrf').attr('content');
    header = $('#_csrf_header').attr('content');

    $('#edit-modal').modal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5, // Opacity of modal background
        inDuration: 300, // Transition in duration
        outDuration: 200, // Transition out duration
        startingTop: '4%', // Starting top style attribute
        endingTop: '10%', // Ending top style attribute
        ready: function (modal, trigger) {
        }, // Callback for Modal open. Modal and trigger parameters available.
        complete: function () { // Callback for Modal close
            resetModal();
        }
    });

    var add_button = document.getElementById("add-question-button");
    add_button.onclick = function () {
        $('#edit-modal').modal('open');
    };

    $(document).keyup(function (event) {
        if ($("#poll_title_input").is(":focus") && event.key === "Enter") {
            finishEditTitle()
        }
    });

    resetModal();
    resetQuestions();
});

var resetQuestions = function () {
    var questions_wrapper = $("#questions-wrapper");
    questions_wrapper.html("");

    for (var i = 0; i < questions.length; i++) {
        addQuestion(i)
    }
};

var questionCount = 0;
var answerCount = 0;

var addAnswer = function () {
    var $template = $("#question-edit-answer-template").clone();

    $template.attr("id", "answer_row_" + answerCount);
    $template.find(".remove-button").attr("answer_id", answerCount);
    $template.find(".remove-button").on('click', removeAnswer);

    $("#answers-wrapper").append($template);
    answerCount++;
};

function removeAnswer(event) {
    var $target = $(event.target);
    var id = $target.attr("answer_id");
    $("#answer_row_" + id).remove();
}

var resetModal = function () {
    $("#question").val("");

    $("#answers-wrapper").html("");
    addAnswer();
    addAnswer();
};

var addQuestion = function (index) {

    var questionData = questions[index];

    var $template = $("#questions-question-template > li").first().clone();
    //$template.replace("{question}", questionData.question).trim().replace("\n","").replace("  "," ");

    $template.attr("id", "question_row_" + questionData.id);
    $template.find(".question-text").html(questionData.question);
    $template.find(".remove-button").attr("question_id", questionData.id);
    $template.find(".remove-button").on('click', removeQuestion);

    $("#questions-wrapper").append($template);
    questionCount++;
};

var removeQuestion = function(event) {
    console.log("click");
    var $target = $(event.target);
    var id = $target.attr("question_id");
    $("#question_row_" + id).remove();
};

function toggleEditTitle() {
    $("#title_edit_div").show();
    $("#title_div").hide();

    $("#poll_title_input").val($("#poll_title").text());
}

function finishEditTitle() {
    cancelEditTitle();
    var $poll_title = $("#poll_title");
    var oldVal = $poll_title.text();
    var poll_title = $("#poll_title_input").val();
    $poll_title.text(poll_title);

    var url = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');

    console.log(url + "/api/polls/" + pollId + "/title");

    $.ajax({
            url: url + "/api/polls/" + pollId + "/title",
            data:
                {
                    title: poll_title
                }
            ,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            type: "POST",
            success: function (msg) {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $poll_title.text(oldVal);
                console.log(XMLHttpRequest);
            }
        }
    );
}

function cancelEditTitle() {
    $("#title_edit_div").hide();
    $("#title_div").show();
}

