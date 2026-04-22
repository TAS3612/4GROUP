<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">学生新規登録</c:param>

    <c:param name="content">

        <h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
    		学生新規登録
		</h2>

        <form action="StudentCreateExecute.action" method="post">

            <div class="mb-3">
                <label class="form-label">入学年度</label>
                <select name="ent_year" class="form-select" required>

    				<!-- 初期値 -->
    				<option value="">--------</option>

				    <c:forEach var="year" items="${entYearList}">
				        <option value="${year}"
            				<c:if test="${year == ent_year}">selected</c:if>>
				            ${year} 
				        </option>
				    </c:forEach>

				</select>
            </div>

            <div class="mb-3">
                <label class="form-label">学生番号</label>
                <input type="text"
                       name="no"
                       value="${no}"
                       maxlength="10"
                       class="form-control"
                       placeholder="学生番号を入力してください"
                       required>
            </div>

            <div class="mb-3">
                <label class="form-label">氏名</label>
                <input type="text"
                       name="name"
                       value="${name}"
                       maxlength="30"
                       class="form-control"
                       placeholder="氏名を入力してください"
                       required>
            </div>

            <div class="mb-3">
                <label class="form-label">クラス</label>
                <select name="class_num" class="form-select" required>
                    <c:forEach var="c" items="${classList}">
                        <option value="${c}">${c}</option>
                    </c:forEach>
                </select>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    ${error}
                </div>
            </c:if>

            <div class="d-flex gap-2 mt-4">
                <button type="submit" name="end" class="btn btn-primary">
                    登録して終了
                </button>

                <a href="StudentList.action" class="btn btn-secondary">
                    戻る
                </a>
            </div>

        </form>

    </c:param>

</c:import>