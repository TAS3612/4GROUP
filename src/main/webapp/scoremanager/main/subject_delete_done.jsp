<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報削除
            </h2>

            <%-- 緑色の完了メッセージバー --%>
            <div class="alert alert-success bg-success bg-opacity-50 text-dark border-0 rounded-0 p-2 text-center mb-4">
                削除が完了しました
            </div>

            <div class="mt-4">
                <a href="SubjectList.action" class="text-decoration-none">科目一覧</a>
            </div>

        </section>

    </c:param>

</c:import>