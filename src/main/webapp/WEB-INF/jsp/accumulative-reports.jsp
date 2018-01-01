<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   

<jsp:include page="common-header.jsp"></jsp:include>


<div class="container">

<table class="table table-bordered table-hover">
<thead>
<tr>
    	<td>
    		Currency
    	</td>
    	
    	<td>
    		No. Of. Deals Imported
    	</td>
    	
    </tr>
</thead>
<tbody>    
<c:forEach items="${fxAccumulativeCountBeans}" var="fxAccumulativeCountBean">
    
    
    <tr>
    	<td>
    	${fxAccumulativeCountBean.currencyCode}
    	</td>
    	
    	<td>
    	${fxAccumulativeCountBean.dealCount}
    	</td>
    	
    </tr>
    
    
</c:forEach>
</tbody>
</table>
</div>

