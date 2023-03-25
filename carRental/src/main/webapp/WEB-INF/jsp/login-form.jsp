<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
<h1 align="center">Caarz</h1>
<h2 align="center">LogIn</h2>
<div style="margin-left:40%">
    <form method="POST"  Style="border: thin; border: black">
    <div>
        UserEmail:<input path="email" name= "username" type="text"/>

    </div>
        <br/>
    <div>
        Password:<input path="password" type="password" name="password"/>

    </div>
        <br/>
<%--    <div>--%>
<%--        Category:<form:select path="category" name="category" id="category">--%>
<%--                <option value="carOwner"> CarOwner</option>--%>
<%--                <option value="customer"> Customer</option>--%>
<%--                </form:select>--%>
<%--        <form:errors path="category"></form:errors>--%>
<%--    </div>--%>
        <br/>
    <input type="submit" value="Submit"/> <br/><br/>
    </form>

    Don't have an account? <a href="${pageContext.request.contextPath}/register.htm">Register</a>

</div>
</body>
</html>


