<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="js/select-ui.min.js"></script>
<script type="text/javascript" src="editor/kindeditor.js"></script>

<script type="text/javascript">
    KE.show({
        id : 'content7',
        cssPath : './index.css'
    });
  </script>
  
<script type="text/javascript">
$(document).ready(function(e) {
    $(".select1").uedSelect({
		width : 345			  
	});
	$(".select2").uedSelect({
		width : 167  
	});
	$(".select3").uedSelect({
		width : 100
	});
});
</script>
</head>

<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">系统设置</a></li>
    </ul>
    </div>
    
    <div class="formbody">
      
    	<div id="usual1" class="usual"> 
    
    	<div class="itab">
		  	<ul> 
		    <li><a href="#tab1" class="selected">Add a new blog</a></li> 
		  	</ul>
    	</div> 
    
	  		<div id="tab1" class="tabson">
	    
	    		<div class="formtext">Hello, 
	    			<b>${mySelf.nickName}</b>, use blog to document your colorful life
	    		</div>
    
    		<ul class="forminfo">
    		
	    		<form action="AddBlogServlet" method="post">
		    		<li><label>Title<b>*</b></label>
		    			<input name="title" type="text" class="dfinput" 
		    				value="please type in the blog title here"  
		    				style="width:700px;"/>
		    		</li>
		   
			    	<li><label>Blog Content<b>*</b></label>
			    		<textarea id="content7" name="content" 
			    			style="width:700px;height:250px;visibility:hidden;">
			    		</textarea>
				    </li>
				    <li><label>&nbsp;</label>
				    	<input name="" type="submit" class="btn" value="Publish blog"/>
				    </li>
			    </form>
		    </ul>
    
    	</div> 
    
		<script type="text/javascript"> 
	      $("#usual1 ul").idTabs(); 
	    </script>
	    
	    <script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
		</script>
 
    </div>


</body>

</html>
