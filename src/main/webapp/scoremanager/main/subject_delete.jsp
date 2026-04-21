<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <%-- タイトルバー --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報削除
            </h2>

            <form action="SubjectDeleteExecute.action" method="post">
                <%-- Actionから渡された値をHiddenで保持 --%>
                <input type="hidden" name="cd" value="${cd}">

                <div class="mb-3 ms-1">
                    <%-- 確認メッセージ --%>
                    <p id="subject">
                        「${name}(${cd})」を削除してもよろしいですか？
                    </p>
                </div>

                <%-- 削除ボタン --%>
                <div class="mb-3">
                    <input type="submit" value="削除" class="btn btn-danger btn-sm px-3">
                </div>

                <%-- 戻るリンク --%>
                <div class="mt-4">
                    <a href="SubjectList.action" class="text-decoration-none">戻る</a>
                </div>

            </form>

        </section>

    </c:param>

</c:import>