<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script   src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<link href="/resources/dipin/example.css" type="text/css" rel="stylesheet" />
<script   src="/resources/dipin/example.js"></script>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>DEPT/LIST</title>
<link rel="shortcut icon" type="image/x-icon"
   href="http://localhost/jspPro/images/SiSt.ico">
<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="google" content="notranslate">
<style>
span.material-symbols-outlined {
   vertical-align: text-bottom;
}
</style>
</head>
<body>
   <h1>DEPT/LIST.JSP</h1>
   <a href="/index">인덱스</a>
   <table border="1" sryle="width:100%">
      <tr>
         <th>deptno</th>
         <th>dname</th>
         <th>loc</th>
      </tr>
      <c:forEach items="${list}" var="vo">
         <tr>
            <td>${vo.deptno}</td>
            <td>${vo.dname}</td>
            <td>${vo.loc}</td>
         </tr>
      </c:forEach>
      
   </table>   
</body>

</html>