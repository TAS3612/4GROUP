<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報変更
            </h2>

            <form action="SubjectUpdateExecute.action" method="post">

                <!-- 科目コード -->
                <div class="mb-3">
                    <label class="fw-bold">科目コード</label><br>
                    <span class="ms-3">${subject.cd}</span>
                    <input type="hidden" name="cd" value="${subject.cd}">

                    <%-- エラーメッセージの表示 --%>
                    <c:if test="${not empty errors}">
                        <div class="ms-3 mt-1 text-warning fw-bold">
                            ${errors}
                        </div>
                    </c:if>
                </div>

                <!-- 科目名 -->
                <div class="mb-3">
                    <label class="fw-bold">科目名</label>
                    <input type="text"
                           name="name"
                           value="${subject.name}"
                           maxlength="30"
                    
                           class="form-control bg-white"
                           <c:if test="${not empty errors}">disabled</c:if>
                           required>
                </div>

                <!-- ボタン -->
                <div class="d-flex gap-3">
                    <input type="submit"
                           value="変更"
                           class="btn btn-primary px-4"
                           <c:if test="${not empty errors}">disabled</c:if>>
                </div>

                <div class="mt-3">
                    <a href="SubjectList.action">戻る</a>
                </div>

            </form>

        </section>

    </c:param>

</c:import>