<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <%-- タイトルの設定 --%>
    <c:param name="title">ログイン - 得点管理システム</c:param>

    <%-- スクリプト部分 --%>
    <c:param name="scripts">
        <script>
            function togglePassword() {
                const pwInput = document.getElementById('passwordInput');
                pwInput.type = pwInput.type === 'password' ? 'text' : 'password';
            }
        </script>
    </c:param>

    <%-- メインコンテンツ --%>
    <c:param name="content">
        <style>
            /* 画面全体の高さを確保し、中央寄せにするための設定 */
            .login-wrapper {
                min-height: 80vh; /* 画面の高さの8割程度を確保 */
                display: flex;
                align-items: center; /* 垂直方向中央 */
                justify-content: center; /* 水平方向中央 */
            }
            .login-card { 
                width: 100%;
                max-width: 400px; 
                border: none; 
                box-shadow: 0 4px 12px rgba(0,0,0,0.1); 
            }
            .login-header { 
                background-color: #e9ecef; 
                border-bottom: 1px solid #dee2e6; 
                padding: 10px; 
                font-weight: bold; 
            }
            .btn-login { width: 50%; }
            input::-ms-reveal, input::-ms-clear { display: none; }
        </style>

        <%-- login-wrapper で囲むことで中央に配置します --%>
        <div class="login-wrapper">
            <div class="card login-card">
                <div class="login-header text-center">ログイン</div>
                <div class="card-body p-4">
                    <c:if test="${not empty errors}">
                        <div class="alert alert-danger py-2 small">${errors}</div>
                    </c:if>

                    <form action="LoginExecute.action" method="post">
                        <div class="mb-3">
                            <label class="form-label small text-muted">ID</label>
                            <input type="text" name="id" class="form-control" placeholder="IDを入力" value="${id}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label small text-muted">パスワード</label>
                            <input type="password" name="password" id="passwordInput" class="form-control" placeholder="パスワードを入力" required>
                        </div>

                        <div class="mb-3 d-flex justify-content-center">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="showPw" onclick="togglePassword()">
                                <label class="form-check-label small" for="showPw">パスワードを表示</label>
                            </div>
                        </div>

                        <div class="d-flex justify-content-center">
                            <button type="submit" class="btn btn-primary btn-login">ログイン</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <script>
        function togglePassword() {
            const pwInput = document.getElementById('passwordInput');
            if (pwInput.type === 'password') {
                pwInput.type = 'text';
            } else {
                pwInput.type = 'password';
            }
        }
    </script>
        
    </c:param>
</c:import>

