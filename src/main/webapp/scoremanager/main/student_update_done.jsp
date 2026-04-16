<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-2 px-4 rounded">
                学生情報変更完了
            </h2>

            <div class="alert alert-success" role="alert">
                変更が完了しました
            </div>

            <a href="StudentList.action">
                学生一覧
            </a>

        </section>

    </c:param>

</c:import>