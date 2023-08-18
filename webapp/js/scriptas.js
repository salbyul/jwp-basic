String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();
  const queryString = $("form[name=answer]").serialize();

  $.ajax({
    type: 'post',
    url: '/api/qna/addAnswer',
    data: queryString,
    dataType: 'json',
    error: onError,
    success: onSuccess,
  });
}

function onSuccess(json, status) {
  const answerTemplate = $("#answerTemplate").html();
  const template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

function onError() {
  alert("에러!");
}

$(".qna-comment").click(deleteAnswer);

async function deleteAnswer(e) {
  e.preventDefault();
  const queryString = $("form[class=form-delete]").serialize();

  const result = await $.ajax({
    type: 'post',
    url: '/api/qna/deleteAnswer',
    data: queryString,
    dataType: 'json',
    error: onError,
    success: deleteSuccess,
  })
  if (result.status === true) {
    const article = e.target.parentNode.parentNode.parentNode.parentNode.parentNode;
    article.remove();
  }
}

function deleteSuccess(json, status) {
  if (json.status === false) {
    alert("삭제에 실패했습니다.");
    return;
  }
  return true;
}