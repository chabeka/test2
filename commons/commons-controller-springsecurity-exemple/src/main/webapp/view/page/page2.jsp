<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>Page s�curis�e</title>
<c:import url="/view/header.jsp" />
</head>
<body>

<h6>&nbsp;page 2&nbsp;</h6>

<button type="button" onclick="javascript:location.href='page1.do';">page 1</button>
<button type="button" onclick="javascript:location.href='page3.do';">page 3</button>

<button type="button" onclick="javascript:location.href='j_spring_security_logout';">deconnexion</button>

</body>
</html>



