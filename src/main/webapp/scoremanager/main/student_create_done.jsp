<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
 
    <c:param name="title">学生登録完了</c:param>
 
    <c:param name="content">
 
        <h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
    		学生登録完了
		</h2>
 
        <div class="alert alert-success">
            学生情報の登録が完了しました。
        </div>
 
        <div class="d-flex gap-2 mt-4">
            <a href="StudentCreate.action" class="btn btn-primary">
                戻る
            </a>
 
            <a href="StudentList.action" class="btn btn-secondary">
                学生番号一覧
            </a>
        </div>
 
    </c:param>
 
</c:import>