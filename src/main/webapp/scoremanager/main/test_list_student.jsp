<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績一覧（学生）
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
			        				<%-- 選択状態の保持を追加 --%>
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
                <form action="TestListStudentExecute.action" method="get">
                    <div class="row align-items-center">
                        <div class="col-2 fw-bold">学生情報</div>
                        <div class="col-4">
                            <label class="form-label">学生番号</label>
                            <input type="text" class="form-control" name="f4" value="${f4}" required placeholder="学生番号を入力してください">
                        </div>
                        <div class="col-2 pt-4">
                            <button class="btn btn-secondary" type="submit">検索</button>
                        </div>
                    </div>
                </form>
            </div>

            <%-- ① 氏名・学生番号表示 --%>
            <c:if test="${not empty target_student}">
                <div class="mb-2 fw-bold">
                    氏名：${target_student.name} (${target_student.no})
                </div>
            </c:if>

            <%-- ② エラーメッセージ表示（成績なし等） --%>
            <c:if test="${not empty error}">
                <div class="text-danger mb-3">${error}</div>
            </c:if>

            <%-- ② 成績一覧テーブル --%>
            <c:if test="${not empty tests_student}">
                <table class="table table-hover border-top mt-3">
                    <thead>
                        <tr class="table-light">
                            <th> 科目名</th>
                            <th> 科目コード</th>
                            <th class="text-center"> 回数</th>
                            <th class="text-center"> 点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ts" items="${tests_student}">
                            <tr>
                                <td> ${ts.subjectName}</td>
                                <td> ${ts.subjectCd}</td>
                                <td class="text-center"> ${ts.num}</td>
                                <td class="text-center"> ${ts.point}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </section>
    </c:param>
</c:import>