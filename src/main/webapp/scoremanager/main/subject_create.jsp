<%-- 科目登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2> <form action="SubjectCreateExecute.action" method="post" class="px-4">
                
                <div class="mb-3">
                    <label class="form-label" for="subject-cd-input">科目コード</label> <input class="form-control" type="text" id="subject-cd-input" name="cd" 
                           placeholder="科目コードを入力してください" value="${cd}" maxlength="3" required /> <%-- エラーメッセージ表示エリア --%>
                    <c:if test="${not empty error_code}">
                        <div class="text-danger small">${error_code}</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="subject-name-input">科目名</label> <input class="form-control" type="text" id="subject-name-input" name="name" 
                           placeholder="科目名を記入してください" value="${name}" required /> </div>

                <div class="mt-4">
                    <button class="btn btn-primary" type="submit" id="register-button">登録</button> </div>
            </form>

            <div class="mt-3 px-4">
                <a href="SubjectList.action">戻る</a> </div>
        </section>
    </c:param>
</c:import>