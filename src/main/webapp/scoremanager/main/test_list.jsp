<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績参照
            </h2>

            <div class="card p-4 shadow-sm mb-4">
                <%-- 科目情報検索フォーム --%>
                <form action="TestListSubjectExecute.action" method="get">
                    <div class="row align-items-center mb-3">
                        <div class="col-2 fw-bold">科目情報</div>
                        <div class="col-auto">
                            <label class="form-label">入学年度</label>
                            <%-- ActionのgetParameter("entYear")に合わせる --%>
                            <select class="form-select form-select-sm" name="entYear" style="width:120px;">
                                <option value="">--------</option>
                                <c:forEach var="year" items="${entYearList}">
                                    <%-- ActionのsetAttribute("selectedEntYear")に合わせる --%>
                                    <option value="${year}" <c:if test="${year == selectedEntYear}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <label class="form-label">クラス</label>
                            <%-- ActionのgetParameter("classNum")に合わせる --%>
                            <select class="form-select form-select-sm" name="classNum" style="width:120px;">
                                <option value="">--------</option>
                                <c:forEach var="num" items="${classList}">
                                    <%-- ActionのsetAttribute("selectedClassNum")に合わせる --%>
                                    <option value="${num}" <c:if test="${num == selectedClassNum}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-3">
                            <label class="form-label">科目</label>
                            <%-- ActionのgetParameter("subject")に合わせる --%>
                            <select class="form-select" name="subject">
                                <option value="">--------</option>
                                <c:forEach var="subject" items="${subjectList}">
                                    <%-- ActionのsetAttribute("selectedSubject")に合わせる --%>
                                    <option value="${subject.cd}" <c:if test="${subject.cd == selectedSubject}">selected</c:if>>${subject.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto pt-4">
                            <button class="btn btn-secondary" type="submit">検索</button>
                        </div>
                    </div>
                </form>
                
            <!-- エラー表示  -->
                <c:if test="${not empty error}">
                    <div class="text-warning">
                        ${error}
                    </div>
                </c:if>

                <hr>

			
			
			<%-- 学生情報検索フォーム --%>
				<form action="TestListStudentExecute.action" method="get">
    				<div class="row align-items-center">
        				<div class="col-2 fw-bold">学生情報</div>
        				<div class="col-4">
            				<label class="form-label">学生番号</label>
            				<input type="text" class="form-control" name="f4" value="${f4}" placeholder="学生番号を入力してください" required>
        				</div>
        				<div class="col-2 pt-4">
            				<button class="btn btn-secondary" type="submit">検索</button>
        				</div>
    				</div>
				</form>
			</div>
			  <c:if test="${not empty error1}">
                <div class="text-danger mb-3">${error1}</div>
            </c:if>
            

            <%-- 検索結果表示エリア --%>
            <c:choose>
                <%-- ActionのsetAttribute("list")に合わせる --%>
                <c:when test="${not empty list}">
                    <div class="mt-4">
                        <%-- 科目名は必要に応じてActionで別途セットするか、listの最初の要素から取得 --%>
                        <h5 class="fw-bold mb-3">科目別検索結果</h5>
                        <table class="table table-hover border-top">
                            <thead>
                                <tr class="table-light">
                                    <th>入学年度</th>
                                    <th>クラス</th>
                                    <th>学生番号</th>
                                    <th>氏名</th>
                                    <th class="text-center">1回</th>
                                    <th class="text-center">2回</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="test" items="${list}">
                                    <tr>
                                        <td>${test.entYear}</td>
                                        <td>${test.classNum}</td>
                                        <td>${test.studentNo}</td>
                                        <td>${test.studentName}</td>
                                        <td class="text-center">
                                            <c:out value="${test.points[1]}" default="-" />
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${test.points[2]}" default="-" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:when test="${not empty tests_student}">
                    <%-- 学生別成績表示が必要な場合はここに実装 --%>
                </c:when>
                
                <c:otherwise>
                    <c:if test="${empty error and empty error1}">
                        <div class="text-info">
                            科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>