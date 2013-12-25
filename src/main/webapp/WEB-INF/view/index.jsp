<%@page pageEncoding="UTF-8"%>

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

		<div id="items"></div>
		<div id="message"></div>

	</body>
</html>
