var url;
var pollId;
var poll;

var questionsCollection;
var answerQuestionTemplate;
var answerAnswerTemplate;
var questionArea;
var answersArea;

var currentQuestionId;

$(document).ready(function () {
    url = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');
    pollId = $("#poll_id").attr("content");

    questionsCollection = $("#questions-collection");
    answerQuestionTemplate = $("#answer-question-template").children().first();
    answerAnswerTemplate = $("#answer-answer-template").children().first();
    questionArea = $("#question");
    answersArea = $("#answers-div");

    $("#save-button").click(sendAnswersToServer);

    getQuestions();
});

function getQuestions() {
    $.getJSON(url + "/api/polls/" + pollId, function (data) {
        poll = data;
        console.log(data);
        buildQuestions();
    })
}

function buildQuestions() {
    questionsCollection.html("");

    for (var i = 0; i < poll.questions.length; i++) {
        var question = poll.questions[i];

        var $template = answerQuestionTemplate.clone();
        $template.attr("question_id", question.id);
        $template.text(question.question);
        $template.click(question.id, changeQuestion);
        questionsCollection.append($template);

        for (var j = 0; j < question.questionAnswers.length; j++) {
            var answer = question.questionAnswers[j];
            answer.checked = false;
        }
    }

    activateFirstQuestion();
}

function activateFirstQuestion() {
    if (poll.questions.length > 0) {
        changeQuestion(poll.questions[0].id);
    }
}

function changeQuestion(ob) {
    var id;
    if (typeof ob === "number") {
        id = ob;
    } else {
        // passed by jQuery with additional stuff
        id = ob.data;
    }

    currentQuestionId = id;

    var question = findQuestionById(id);
    questionArea.text(question.question);

    buildAnswers(question);
}

function findQuestionById (id) {
    for (var i = 0; i < poll.questions.length; i++) {
        if (poll.questions[i].id === id) {
            return poll.questions[i];
        }
    }
    return null;
}

function findAnswerById (id) {
    var question = findQuestionById(currentQuestionId);
    for (var i = 0; i < question.questionAnswers.length; i++) {
        if (question.questionAnswers[i].id === id) {
            return question.questionAnswers[i];
        }
    }
    return null;
}

function buildAnswers(question) {
    answersArea.html("");

    for (var i = 0; i < question.questionAnswers.length; i++) {
        var answer = question.questionAnswers[i];

        var $template = answerAnswerTemplate.clone();
        $template.find("input").first().attr("answer_id", answer.id);
        $template.find(".answer-text").first().text(answer.answer);

        if (answer.checked) {
            $template.find("input").first().prop('checked', true);
        }

        answersArea.append($template);
    }
}

function sendAnswersToServer() {

    var question = findQuestionById(currentQuestionId);
    for (var i = 0; i < question.questionAnswers.length; i++) {
        var id = question.questionAnswers[i].id;
        var chosen = $('input[answer_id=' + id + ']').first().prop("checked");
        question.questionAnswers[i].checked = chosen;
    }

    // TODO:
    // Send answer data to server so a API request could
    // be made by the teacher to get statistics
}
