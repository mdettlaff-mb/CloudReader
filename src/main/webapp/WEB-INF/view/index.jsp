<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Cloud Reader</title>
		<link rel="stylesheet" href="/resources/style.css" type="text/css">
		<script src="/resources/jquery-1.9.1.js"></script>
		<script src="/resources/jquery.hotkeys.js"></script>
		<script src="/resources/cloudreader.js"></script>
	</head>
	<body>

<div id="item1" class="item">
	<hr>
	<div class="title">first element</div>
</div>

<div id="item2" class="item">
	<hr>
	<div class="title">second element</div>
</div>

<div id="item3" class="item">
	<hr>
	<div class="title">third element</div>
</div>

<c:forEach items="${feedItems}" var="item">
	${item.feed.subscription.url}<br>
	${item.feed.title}<br>
	${item.feed.link}<br>
	${item.title}<br>
	${item.link}<br>
	${item.description}
	<hr>
</c:forEach>

<script>
	$(document).bind('keydown', 'n j', goToNextItem);
	$(document).bind('keydown', 'k', goToPreviousItem);
</script>

	</body>
</html>
