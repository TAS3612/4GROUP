<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <%-- ① タイトル：グレーの背景バー --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

            <%-- 検索用フォーム --%>
            <form action="TestRegist.action" method="get">
                <%-- 全体の背景を薄いグレー(bg-light)にし、内側の要素を調整 --%>
                <div class="bg-light p-3 border d-flex align-items-end gap-3 mb-4">
                    
                    <%-- 入学年度 (背景を白に指定) --%>
                    <div style="width: 120px;">
                        <label class="form-label small mb-1">入学年度</label>
                        <select name="entYear">
        			<option value="">--------</option>
       				<c:forEach var="y" items="${entYearSet}">
       				<option value="${y}" ${y == entYear ? "selected" : ""}>${y}</option>
        			</c:forEach>
    				</select>
                    </div>

                    <%-- クラス (背景を白に指定) --%>
                    <div style="width: 120px;">
                        <label class="form-label small mb-1">クラス</label>
                        <select name="classNum" class="form-select form-select-sm bg-white">
                            <option value="">--------</option>
                            <c:forEach var="cNum" items="${classList}">
                                <option value="${cNum}" <c:if test="${cNum == classNum}">selected</c:if>>${cNum}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- 科目 (背景を白に指定) --%>
                    <div style="width: 250px;">
                        <label class="form-label small mb-1">科目</label>
                        <select name="subjectCd" class="form-select form-select-sm bg-white">
                            <option value="">----------------</option>
                            <c:forEach var="sub" items="${subjectList}">
                                <option value="${sub.cd}" <c:if test="${sub.cd == subjectCd}">selected</c:if>>${sub.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- 回数 (背景を白に指定) --%>
                    <div style="width: 120px;">
                        <label class="form-label small mb-1">回数</label>
                        <select name="testNo" class="form-select form-select-sm bg-white">
                            <option value="">--------</option>
                            <option value="1" <c:if test="${testNo == '1'}">selected</c:if>>1</option>
                            <option value="2" <c:if test="${testNo == '2'}">selected</c:if>>2</option>
                        </select>
                    </div>

                    <%-- 検索ボタン --%>
                    <div>
                        <input type="submit" value="検索" class="btn btn-secondary btn-sm px-3 shadow-sm">
                    </div>
                </div>
            </form>

            <%-- 成績一覧表示エリア --%>
            <c:if test="${not empty testList}">
                <div class="mb-2 text-dark fw-bold">
                    科目：${subjectName}
                </div>
                <form action="TestRegistExecute.action" method="post">
                    <%-- 検索条件を hidden で引き継ぐ --%>
                    <input type="hidden" name="subjectCd" value="${subjectCd}">
                    <input type="hidden" name="testNo" value="${testNo}">
                    <input type="hidden" name="classNum" value="${classNum}">
                    <table class="table table-hover mt-3">
                        <thead>
                            <tr class="border-bottom border-dark">
                                <th>学籍番号</th>
                                <th>氏名</th>
                                <th>点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="test" items="${testList}">
                                <tr>
                                    <td>${test.student.no}</td>
                                    <td>${test.student.name}</td>
                                    <td>
                                        <input type="hidden" name="studentNo" value="${test.student.no}">
                                        <input type="number" name="point" value="${test.point}" 
                                               class="form-control form-control-sm bg-white" style="width: 80px;"
                                               min="0" max="100">
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="mt-3">
                        <input type="submit" value="登録して終了" class="btn btn-primary btn-sm px-4">
                    </div>
                </form>
            </c:if>

            <%-- エラー表示 --%>
            <c:if test="${not empty errors}">
                <div class="alert alert-danger mt-3 py-2">
                    <c:forEach var="error" items="${errors}">
                        <div class="small">${error}</div>
                    </c:forEach>
                </div>
            </c:if>
        </section>
    </c:param>
</c:import>