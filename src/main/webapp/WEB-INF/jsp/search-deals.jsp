<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<h2>Hello World!</h2>

<table>
<c:forEach items="${fxDealsBeans}" var="fxDealsBean">
    
    
    <tr>
    	<td>
    	${fxDealsBean.dealUniqueID}
    	</td>
    	
    	<td>
    	${fxDealsBean.fromCurrencyISOCode}
    	</td>
    	
    	<td>
    	${fxDealsBean.toCurrencyISOCode}
    	</td>
    	
    	<td>
    	${fxDealsBean.dealTimeStamp}
    	</td>
    	<td>
    	${fxDealsBean.dealAmount}
    	</td>
    </tr>
    
    
</c:forEach>
</table>

</body>
</html>
