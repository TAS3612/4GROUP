<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<ul class="nav flex-column">

    <li class="nav-item">
        <a class="nav-link" href="Menu.action">メニュー</a>
    </li>

    <li class="nav-item">
        <a class="nav-link" href="StudentList.action">学生管理</a>
    </li>

    <li class="nav-item mt-2">
        成績管理
        <ul class="nav flex-column ms-3">
            <li class="nav-item">
                <a class="nav-link" href="ScoreList.action">成績登録</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="ScoreReference.action">成績参照</a>
            </li>
        </ul>
    </li>

    <li class="nav-item">
        <a class="nav-link" href="SubjectList.action">科目管理</a>
    </li>

</ul>