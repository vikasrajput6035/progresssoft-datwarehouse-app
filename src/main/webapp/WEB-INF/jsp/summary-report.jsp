<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   


<jsp:include page="common-header.jsp"></jsp:include>
<body>

<div class="container">

<div class="form-group">
  <form:form  action="summary-report" method="GET" commandName="fileReportBean">
  <div class="row" style="align-items: center;">
  
  	<div class="col-lg-2">
  			
  	</div>
  	<div class="col-lg-6">
  		<form:input path="fileName" type="text" class="form-control" placeholder="Please Type File Name" />	
  	</div>
  	
  	<div class="col-lg-2">
  		
		    <button type="submit" class="btn btn-info">
		      <span class="glyphicon glyphicon-search"></span> Search
		    </button>
			
  	</div>
  	<div class="col-lg-2">
  	</div>
  </div>
  
  </form:form>
</div>

<c:if test = "${empty fileReportBeans}">
	<div class="row" style="text-align: center;">
	No Record(s) Found
	</div>
</c:if>

<c:if test = "${!empty fileReportBeans}">

<table class="table table-bordered table-hover">
<thead>
<tr>
    	<td>
    		File Name
    	</td>
    	
    	<td>
    		No. Of. Deals Imported
    	</td>
    	
    	<td>
    		No. Of. InValid Deals
    	</td>
    	
    	<td>
    		Process End time
    	</td>
    	<td>
    		Process Start time
    	</td>
    	<td>
    		Process Duration (In MilliSeconds)
    	</td>
    </tr>
</thead>
<tbody>    
<c:forEach items="${fileReportBeans}" var="fileReportBean">
    
    
    <tr>
    	<td>
    	${fileReportBean.fileName}
    	</td>
    	
    	<td>
    	${fileReportBean.noOfValidRecords}
    	</td>
    	
    	<td>
    	${fileReportBean.noOfInValidRecords}
    	</td>
    	
    	<td>
    	${ fileReportBean.processEndTime}
    	</td>
    	
    	<td>
    		${ fileReportBean.processStartTime} 
    	</td>
    	
    	<td>
    	<fmt:parseNumber value="${(fileReportBean.processEndTime.time - fileReportBean.processStartTime.time)}" integerOnly="true" />	
    	</td>
    	
    </tr>
    
    
</c:forEach>
</tbody>
</table>
</c:if>
</div>
</body>
</html>
