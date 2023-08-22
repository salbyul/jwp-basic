<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/include/header.jspf" %>
</head>
<body>
<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
        <div class="panel panel-default content-main">
            <c:if test="${not empty param.questionId}">
            <form name="question" method="post" action="/qna/update?questionId=${param.questionId}">
                </c:if>
                <c:if test="${empty param.questionId}">
                <form name="question" method="post" action="/qna/create">
                    </c:if>
                    <div class="form-group">
                        <label for="writer">글쓴이</label>
                        <input class="form-control" id="writer" name="writer" placeholder="글쓴이" value="${name}"
                               readonly/>
                    </div>
                    <div class="form-group">
                        <label for="title">제목</label>
                        <c:if test="${not empty question}">
                            <input type="text" class="form-control" id="title" name="title" placeholder="제목"
                                   value="${question.title}"/>
                        </c:if>
                        <c:if test="${empty question}">
                            <input type="text" class="form-control" id="title" name="title" placeholder="제목"/>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <label for="contents">내용</label>
                        <textarea name="contents" id="contents" rows="5" class="form-control">
                        <c:if test="${not empty question}">
                            ${question.contents}
                        </c:if>
                    </textarea>
                    </div>
                    <button type="submit" class="btn btn-success clearfix pull-right">질문하기</button>
                    <div class="clearfix"/>
                </form>
        </div>
    </div>
</div>

<%@ include file="/include/footer.jspf" %>
</body>
</html>