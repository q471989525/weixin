Current Version: 1.4
======================
This software is allowed to use under GPL or you need to buy commercial license for better support or other purpose.
Please contact us at info@jeasyui.com

【增加】
	添加:public.css

【修改】
	easyui-lang-zh_CN.js第3行,修改为：$.fn.pagination.defaults.afterPageText = '页,共{pages}页';
	icon.css中，增加自定义按钮图片
	easyui.css中 36行，增加body样式  .panel-body-overflow { overflow: hidden; }
	
	
//初始化主题
/*
$(document).ready(function(){
	var cookieSkin = $.cookie('theme_cookie'); // 获得cookie
	if(typeof(cookieSkin) != "undefined"){//cookie不为空
		$("#skinChange").combobox("setValue", cookieSkin);
		$("#easyuiCssLink").attr("href",ctx+"/common/easyui1.4/themes/"+cookieSkin+"/easyui.css");
	}
});

function selectSkin(record){
	var skin = record.value;
	$.cookie('theme_cookie', skin); // 设置cookie
	$("#easyuiCssLink").attr("href",ctx+"/common/easyui1.4/themes/"+skin+"/easyui.css");
}*/