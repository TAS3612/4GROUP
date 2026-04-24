
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績一覧（科目）
            </h2>

            <%-- 検索フォームエリア --%>
            <div class="card p-4 shadow-sm mb-4">
                <form action="TestListSubjectExecute.action" method="get">
                    <div class="row align-items-center mb-3">
                        <div class="col-2 fw-bold">科目情報</div>
                        <div class="col-auto">
                            <label class="form-label">入学年度</label>
                            <select class="form-select form-select-sm" name="entYear" style="width:120px;">
                                <option value="">--------</option>
                                <c:forEach var="year" items="${entYearList}">
                                    <option value="${year}" <c:if test="${year == selectedEntYear}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <label class="form-label">クラス</label>
                            <select class="form-select form-select-sm" name="classNum" style="width:120px;">
                                <option value="">--------</option>
                                <c:forEach var="num" items="${classList}">
                                    <option value="${num}" <c:if test="${num == selectedClassNum}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-3">
                            <label class="form-label">科目</label>
                            <select class="form-select" name="subject">
                                <option value="">--------</option>
                                <c:forEach var="s" items="${subjectList}">
                                    <option value="${s.cd}" <c:if test="${s.cd == selectedSubject}">selected</c:if>>${s.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto pt-4">
                            <button class="btn btn-secondary" type="submit">検索</button>
                        </div>
                    </div>
                </form>

                <hr>

                <%-- 学生情報検索（デザイン上の下段） --%>
                <form action="TestListStudentExecute.action" method="get">
                    <div class="row align-items-center">
                        <div class="col-2 fw-bold">学生情報</div>
                        <div class="col-4">
                            <label class="form-label">学生番号</label>
                            <input type="text" class="form-control" name="f4" value="${f4}" placeholder="学生番号を入力してください">
                        </div>
                        <div class="col-2 pt-4">
                            <button class="btn btn-secondary" type="submit">検索</button>
                        </div>
                    </div>
                </form>
            </div>

           <%-- エラー表示と表示制御 --%>
            <c:choose>
                <%-- エラーがある場合 --%>
                <c:when test="${not empty error}">
                    <div class="text-danger mb-3">${error}</div>
                </c:when>

                <%-- エラーがない場合のみ、以下の結果表示を実行 --%>
                <c:otherwise>
                    <%-- ① 科目名表示 --%>
                    <div class="mb-2 fw-bold">
                        科目：
                        <c:forEach var="s" items="${subjectList}">
                            <c:if test="${s.cd == selectedSubject}">
                                ${s.name}
                            </c:if>
                        </c:forEach>
                    </div>

                    <%-- ② 成績一覧テーブル --%>
                    <table class="table table-hover border-top">
                        <thead>
                            <tr class="table-light">
                                <th> 入学年度</th>
                                <th> クラス</th>
                                <th> 学生番号</th>
                                <th> 氏名</th>
                                <th class="text-center"> 1回</th>
                                <th class="text-center"> 2回</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="st" items="${list}">
                                <tr>
                                    <td> ${st.entYear}</td>
                                    <td> ${st.classNum}</td>
                                    <td> ${st.studentNo}</td>
                                    <td> ${st.studentName}</td>
                                    <td class="text-center">
                                         <c:out value="${st.getPoint(1)}" default="-" />
                                    </td>
                                    <td class="text-center">
                                         <c:out value="${st.getPoint(2)}" default="-" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>