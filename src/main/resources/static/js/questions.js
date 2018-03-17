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
    $("#questions-wrapper").html("");
};

var addAnswer = function () {
    var template = $("#question-edit-answer-template").html();
    $("#answers-wrapper").append(template);
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
