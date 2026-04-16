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
                学生情報変更
            </h2>

            <form action="StudentUpdateExecute.action" method="post">

                <!-- 入学年度 -->
                <div class="mb-3">
                    <label class="fw-bold">入学年度</label><br>
                    <span class="ms-3">${student.entYear}</span>
                    <input type="hidden" name="ent_year" value="${student.entYear}">
                </div>

                <!-- 学生番号 -->
                <div class="mb-3">
                    <label class="fw-bold">学生番号</label><br>
                    <span class="ms-3">${student.no}</span>
                    <input type="hidden" name="no" value="${student.no}">
                </div>

                <!-- 氏名 -->
                <div class="mb-3">
                    <label class="fw-bold">氏名</label>
                    <input type="text"
                           name="name"
                           value="${student.name}"
                           maxlength="30"
                           class="form-control"
                           required>
                </div>

                <!-- クラス -->
                <div class="mb-3">
                    <label class="fw-bold">クラス</label>
                    <select name="class_num" class="form-select">
                        <c:forEach var="c" items="${classList}">
                            <option value="${c}"
                                <c:if test="${student.classNum == c}">
                                    selected
                                </c:if>>
                                ${c}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 在学中 -->
                <div class="mb-4">
                    <label class="fw-bold me-3">在学中</label>
                    <input type="checkbox"
                           name="is_attend"
                           class="form-check-input"
                           <c:if test="${student.attend}">
                               checked
                           </c:if>>
                </div>

                <!-- ボタン -->
                <div class="d-flex gap-3">
                    <input type="submit"
                           value="変更"
                           class="btn btn-primary px-4">

                 
                </div>
                <a href="StudentList.action">
                        戻る
                    </a>

            </form>

        </section>

    </c:param>

</c:import>