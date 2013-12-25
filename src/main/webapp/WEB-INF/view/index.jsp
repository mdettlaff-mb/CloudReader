<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
		<title>Cloud Reader</title>
		<link rel="stylesheet" href="/resources/css/style.css" type="text/css">
		<script src="/resources/js/jquery-1.9.1.js"></script>
		<script src="/resources/js/jquery.hotkeys.js"></script>
		<script src="/resources/js/date.format.js"></script>
		<script src="/resources/js/cloudreader.js"></script>
		<script>
			$(function () {
				cloudReader.init();
			});
		</script>
	</head>
	<body>


<c:if test="${empty feedItems}">
No items to display.
</c:if>
<c:forEach items="${feedItems}" var="item">
<div id="${item.guid}" class="item">
	<div class="date">
		<fmt:formatDate value="${item.sortDate}" pattern="yyyy-MM-dd HH:mm" />
	</div>
	<div class="title">
		<a href="${item.link}" target="_blank">
			${item.title}
		</a>
	</div>
	<div>
		<a href="${item.feed.link}" target="_blank" class="feedTitle">
			${item.feed.title}
		</a>
		<span class="author">
		<c:if test="${not empty item.author}">
			(author: ${item.author})
		</c:if>
		</span>
	</div>
	<div class="description">${item.description}</div>
</div>
</c:forEach>


	</body>
</html>
