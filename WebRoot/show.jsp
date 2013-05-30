<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>显示编译后的flash</title>
<script type="text/javascript" src="editor/swfobject.js"></script>
		<script type="text/javascript">
			swfobject.registerObject("myFlashContent", "10.2.0");
		</script>

</head>
<body>
<div>
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="800" height="600" id="myFlashContent">
				<param name="movie" value="" />
				<!--[if !IE]>-->
				<object type="application/x-shockwave-flash" data="untitled.swf" width="800" height="600">
				<!--<![endif]-->
					<a href="http://www.adobe.com/go/getflashplayer">
						<img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" />
					</a>
				<!--[if !IE]>-->
				</object>
				<!--<![endif]-->
		</object>
</div>
	
</body>
</html>