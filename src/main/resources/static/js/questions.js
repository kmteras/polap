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

var currentlyEditingQuestionId = null;

$(document).ready(function () {
    pollId = $("#poll_id").attr("content");
    token = $('#_csrf').attr('content');
    header = $('#_csrf_header').attr('content');

    $('#edit-modal').modal({
        dismissible: true, // Modal can be dismissed by clicking outside
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
    add_button.onclick = openAddModal;

    $(document).keyup(function (event) {
        if ($("#poll_title_input").is(":focus") && event.key === "Enter") {
            finishEditTitle()
        }
    });

    resetModal();
    buildQuestions();
});

var buildQuestions = function () {
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
    $('#modal-close-button').prop('onclick',null).off('click');
    $("#answers-wrapper").html("");
    answerCount = 0;
    //addAnswer();
    //addAnswer();
};

var createQuestion = function () {
    var id = questionCount;
    var question = $("#question").val();
    var questionData = {
        "id": id,
        "question": question,
        "answers": []
    };

    $("#answers-wrapper").children().each(function () {
        var correct = $(this).find('input[type="checkbox"]').first().prop("checked");
        var answer = $(this).find('input[type="text"]').first().val();
        questionData.answers.push({
            "text": answer,
            "correct": correct
        });
    });

    questions.push(questionData);
    addQuestion(questions.length - 1);
};

var addQuestion = function (index) {

    var questionData = questions[index];

    var $template = $("#questions-question-template > li").first().clone();

    $template.attr("id", "question_row_" + questionData.id);
    $template.find(".question-text").html(questionData.question);

    $template.find(".remove-button").attr("question_id", questionData.id);
    $template.find(".remove-button").on('click', removeQuestion);
    $template.find(".edit-button").attr("question_id", questionData.id);
    $template.find(".edit-button").on('click', openEditModal);

    $("#questions-wrapper").append($template);
    questionCount++;
};

var removeQuestion = function (event) {
    var $target = $(event.target);
    var id = $target.attr("question_id");
    $("#question_row_" + id).remove();
};

var openAddModal = function () {
    resetModal();
    addAnswer();
    addAnswer();
    $("#modal-close-button").click(createQuestion);
    $('#edit-modal').modal('open');
};

var openEditModal = function(event) {
    /*$("#question").val("");
    var answers_wrapper = $("#answers-wrapper");
    answers_wrapper.html("");*/

    resetModal();

    var $target = $(event.target);
    var id = Number($target.attr("question_id"));
    currentlyEditingQuestionId = id;

    var questionData;
    for (var i = 0; i < questions.length; i++) {

        if (questions[i].id === id) {
            questionData = questions[i];
            break;
        }
    }
    if (questionData === null || questionData === undefined) {
        console.log("questionData not found");
    }

    $("#question").val(questionData.question);

    for (var j = 0; j < questionData.answers.length; j++) {

        var correct = questionData.answers[j].correct;
        var answer = questionData.answers[j].text;

        addAnswer();
        // answerCount has been reset, every addQuestion adds
        // a question with id-s starting from 0 again
        var answerRow = $("#answer_row_" + j.toString());
        answerRow.find('input[type="checkbox"]').prop("checked", correct);
        answerRow.find('input[type="text"]').val(answer);
    }

    $("#modal-close-button").click(editQuestion);
    $('#edit-modal').modal('open');
};

var editQuestion = function() {
    // kustuta eelmine ja createQuestion
    var id = currentlyEditingQuestionId;
    var previousData;

    for (var i = 0; i < questions.length; i++) {
        if (questions[i].id === id) {
            previousData = questions[i];
            questions.splice(id, 1);
            break;
        }
    }
    if (previousData === null || previousData === undefined) {
        console.log("previousData not found");
    }

    $("#question_row_" + id).remove();

    createQuestion();

    currentlyEditingQuestionId = null;
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

