<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>提交业务信息</title>

<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
function config() {
	$.ajax({
		type : 'post',
		url : " http://6dbef4e9.ngrok.io/NIC-wechat/work/config",
		data : {'url' :location.href.split('#')[0]},
		dataType : 'json',
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		success : function(data) {
			var obj = eval(data[0]);
			wx.config({
				debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				appId : obj.appId, // 必填，公众号的唯一标识
				timestamp : obj.timestamp, // 必填，生成签名的时间戳
				nonceStr : obj.nonceStr, // 必填，生成签名的随机串
				signature : obj.signature,// 必填，签名，见附录1
				jsApiList : [ 'chooseImage', 'uploadImage',
						'downloadImage','checkJsApi' ]
			});
		}
	});
}
function isWeiXin5() {
    var ua = window.navigator.userAgent.toLowerCase();
    var reg = /MicroMessenger\/[5-9]/i;
    return reg.test(ua);
}
 

window.onload = function() {
	alert("上ssss");
    //     if (isWeiXin5() == false) {
    //           alert("您的微信版本低于5.0，无法使用微信支付功能，请先升级！");
    //         }
    config();
    alert("上s");
};


function takePicture(){
    wx.chooseImage({
        count: 1, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片

            wx.uploadImage({
                localId: localIds.toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
                isShowProgressTips: 1, // 默认为1，显示进度提示
                success: function (res) {
                    var mediaId = res.serverId; // 返回图片的服务器端ID，即mediaId
                    alert("上传成功");
                    //将获取到的 mediaId 传入后台 方法savePicture
                    $.ajax({
						   type: "POST",
						   url: "<%=request.getContextPath()%>/work/savePicture",//请求的后台地址
						   data: {"mediaId":mediaId},//前台传给后台的参数
						   success: function(filename){//filename:返回值
							  	 alert(filename);
							  	$("#fileName").val(filename);
							  	alert("1");
							  	var name=$("[id='fileName']").val(); 
							  	alert("2");
							  	alert(name);
							  	alert("3");
						   },
						   error:function(XMLHttpRequest, textStatus, errorThrown) {  
		                       alert(XMLHttpRequest.status);  
		                       alert(XMLHttpRequest.readyState);  
		                       alert(textStatus);  
		                   }  
						});
                   // alert(ll);
                   <%--  $.post("<%=request.getContextPath()%>/work/savePicture",{mediaId:mediaId},
                    	function(filename){
                    	alert(filename);
	                        if(filename == 'success'){
	                        	alert('上传成功')
	                        }else{
	                            alert(res.msg)
	                        }
                    }) --%>
                },
                fail: function (res) {
                    alertModal('上传图片失败，请重试')
                }
            }); 
        }
    });
}
</script>
<style type="text/css">

body{
    font-size: 1em;
    font-family: "Microsoft YaHei";
    color: #535353;
    box-sizing: border-box;
}
*{
    margin: 0;
    padding: 0;
}
a{
    text-decoration: none;
    color:#374782;
}
input{
    outline: none;
}


body{
    background: #efeff4;
    width: 100%;
    height: 100%;
}
.regTop{
    width: 100%;
    padding:8% 0 6%;
    background: #50b4f9;
    text-align: center;
    color: #ffffff;
    position: relative;
}
.back{
    position: absolute;
    left: 5%;
    top: 50%;
    color: #ffffff;
}
.point{
    padding: 6% 5%;
}
.content form input:not(:nth-child(6)){
    border: 0;
    border-bottom: 1px solid #c3c3c5;
}
.content form{
    width: 100%;
    height: 35.21%;
}
.message{
    background: #ffffff;
    padding:2% 5% 0 5%;
    position: relative;
}
.message input{
    width: 90%;
    padding: 4% 0 4% 10%;
    font-size: 0.875em;
    font-family: "Microsoft YaHei";
}
.message .icons b{
    position: absolute;
    width: 18%;
    height: 4%;
    top: 7%;
    left: 7%;
}
.message .icons b img{
    width: 100%;
}
.message .icons b:nth-child(2){
    width: 5%;
    top: 26%;
    left: 6%;
}
.message .icons b:nth-child(3){
    top: 43%;
}
.message .icons b:nth-child(4){
    top: 61%;
}
.code{
    position: absolute;
    top: 60%;
    right:10%;
    color: #21a9f5;
    font-size: 0.875em;
    font-family: "Microsoft YaHei";
}
select{
    width: 55%;
    padding: 3% 2%;
    margin: 8% 5%;
    font-size: 1em;
    color: #909093;
    border: solid 1px #909093;
    font-family: "Microsoft YaHei";
}
.submit{
    width: 84%;
    margin: 4% 8%;
    background: #21a9f5;
    color: #ffffff;
    border: 0;
    padding: 3.25% 0;
    font-size: 1em;
    font-family: "Microsoft YaHei";
}
form .agree input[type="checkbox"] :default{
    outline: 5px solid #21a9f5;
}

</style>
</head>
<body style="font-size: 60px;">
	 <div class="register">
		<div class="regTop">
			<span>提交报修信息</span>
		</div>
		<div class="content">
			<div class="point">
				<span style="font-size: 45px;">用户须完善信息之后才能提交业务信息。</span>
			</div>
			<form action="${pageContext.request.contextPath }/work/addwork"
				method="post">
				<div class="message">
					<select name="worktype">
						<option value="0">选择业务类型</option>
						<option value="1">技术支持</option>
						<option value="2">设备报修</option>
						<option value="3">日常运维</option>
					</select>
					<table style="width: 100%; font-size: 40px;">
						<tr>
							<td style="width: 30%;"><label>设备名称：</label></td>
							<td><input type="text" placeholder="请输入设备名称"
								name="devicename" /></td>
						</tr>
						<tr>
							<td><label>具体描述：</label></td>
							<td><input type=text placeholder="请输入具体描述"
								name="description" /></td>
						</tr>
						<tr>
							<td><label>具体地点：</label></td>
							<td><input type=text placeholder="请输入具体地点" name="location" /></td>
						</tr>
						<tr align="center" style="margin-top: 14%;">
						<td></td>
						<td>
							<div style="width: 80%; margin-top: 7%;margin-left:-25%; height: 120px; margin-bottom: 5%;">
							<input type="button" value="点击上传图片"
								onclick="takePicture()" />
							</div>	
							</td>
						</tr>
						<td><input type="hidden" id="fileName"  name="filename" /></td>
					</table>
				</div>
				<button class="submit" type="submit">提交信息</button>
			</form>
		</div>
	</div> 
	
</body>
</html>