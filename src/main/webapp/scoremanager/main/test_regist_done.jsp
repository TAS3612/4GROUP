<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

            
            <div class="alert alert-success bg-success bg-opacity-50 text-dark border-0 rounded-0 p-2 text-center mb-4">
                登録が完了しました
            </div><br><br><br>

            <div class="row mt-3">
                    <div class="col-auto">
                        <a href="TestRegist.action">戻る</a> </div>
                    <div class="col-auto ms-5">
                        <a href="TestList.action">成績参照</a> </div>
                </div>
        </section>
    </c:param>
</c:import>