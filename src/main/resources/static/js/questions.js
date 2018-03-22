var questions = [
    {"question": "First Question?", "answers":[
        {"text": "First Text", "correct": false},
        {"text": "Second Text", "correct": true},
        {"text": "Third Text", "correct": false}]},
    {"question": "Second Question?", "answers": [
        {"text": "Only Choice", "correct": true}]}
    ];

$(document).ready(function() {
    $('#edit-modal').modal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5, // Opacity of modal background
        inDuration: 300, // Transition in duration
        outDuration: 200, // Transition out duration
        startingTop: '4%', // Starting top style attribute
        endingTop: '10%', // Ending top style attribute
        ready: function(modal, trigger) {}, // Callback for Modal open. Modal and trigger parameters available.
        complete: function() { // Callback for Modal close
            resetModal();
        }
    });

    var add_button = document.getElementById("add-question-button");
    add_button.onclick = function () {
        $('#edit-modal').modal('open');
    };

    resetModal();
    resetQuestions();
});

var resetQuestions = function() {
    var questions_wrapper = $("#questions-wrapper");
    questions_wrapper.html("");
    var template = $("#questions-question-template").html();

    for (var i = 0; i < questions.length; i++) {
        questions_wrapper.append(template.replace("{question}", questions[i].question));
    }
};

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
};

var resetModal = function() {
    $("#question").val("");

    $("#answers-wrapper").html("");
    addAnswer();
    addAnswer();
};

var addQuestion = function() {
    var questionField = $("#question");
    var question = questionField.val();
    console.log(question);
};
