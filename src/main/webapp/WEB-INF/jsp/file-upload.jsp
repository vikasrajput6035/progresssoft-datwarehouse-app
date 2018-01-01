<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   

<jsp:include page="common-header.jsp"></jsp:include>

<body>
<div class="container">

<c:if test="${not empty message}">
<div class="alert alert-danger">
	${message}
</div>
</c:if>

<c:if test="${not empty successMessage}">
<div class="alert alert-success">
	${successMessage}
</div>
</c:if>

<form action="save" method="POST" enctype="multipart/form-data">
  
  
  <div class="form-group">
    <label for="file">Please upload your CSV file</label>
    <input id="file" type="file" name="file" class="form-control">
  </div>
  
  <button type="submit" class="btn btn-default">Upload</button>
</form>


</div>
</body>
</html>
