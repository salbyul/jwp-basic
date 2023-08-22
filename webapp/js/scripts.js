// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
}

function onSuccess(json, status){
  const elementById = document.getElementById("countsOfAnswer");
  elementById.innerText = (elementById.innerText * 1) + 1;
  var answer = json.answer;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);

  $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".article-util button[type=submit]").click(deleteAnswer);

async function deleteAnswer(e) {
  e.preventDefault();
  const answerId = e.currentTarget.parentNode[0].value;

  const result = await $.ajax({
    type : 'post',
    url : '/api/qna/deleteAnswer',
    data : {answerId: answerId},
    dataType : 'json',
    error: onError,
    success : onDeleteSuccess,
  });
  console.log(result);
  if (result.result.status === true) {
    const willRemoveNode = e.currentTarget.parentNode.parentNode.parentNode.parentNode.parentNode;
    willRemoveNode.remove();
  }
}

function onDeleteSuccess(json, status) {
  alert('삭제되었습니다.');
}