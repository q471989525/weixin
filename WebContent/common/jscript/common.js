//弹出窗口
var Common = {
	dialog:function(properties){
		var width=document.body.clientWidth; //网页可见区域宽
		var height=document.body.clientHeight; //网页可见区域高
		var modal = false; //非模态窗口
		var resizable="yes";
		var scroll="yes";
		var status="no";
		var center="yes";
		var help="no";
		var location="no";
		var fullscreen="no";
		var winName = "_blank";
		var url=properties.url;
		var id="";
		var title="";
		if (properties.id != undefined) id = properties.id;
		if (properties.title != undefined) title = properties.title;
		if (properties.width != undefined) width = properties.width;
		if (properties.height != undefined) height = properties.height;
		if (properties.resizable != undefined) resizable = properties.resizable;
		if (properties.scroll != undefined) scroll = properties.scroll;
		if (properties.status != undefined) status = properties.status;
		if (properties.center != undefined) center = properties.center;
		if (properties.help != undefined) help = properties.help;
		if (properties.fullscreen != undefined) fullscreen = properties.fullscreen;
		if (properties.winName != undefined) winName = properties.winName;
		if (properties.modal != undefined) modal = properties.modal;
		var iTop = (window.screen.availHeight - height) / 2;
        var iLeft = (window.screen.availWidth - width) / 2;
		if (properties.iTop != undefined) iTop = properties.iTop;
		if (properties.iLeft != undefined) iLeft = properties.iLeft;
        if(modal){
        	width = width - 80;  height = height - 60;
        	$.dialog({
    			id:id, title:title, lock: true, fixed: true, width: width+"px", height: height+"px", content: "url:"+url
        	});
        	//return window.showModalDialog(url,window,"dialogLeft="+iLeft+";dialogTop="+iTop+";dialogWidth="+width+"px;dialogHeight="+height+"px;resizable="+resizable+";scroll="+scroll+";status="+status+";center="+center+";help="+help+";");
        }else{
        	return window.open(url,winName,'height='+height+',width='+width+',top='+(iTop-20)+',left='+iLeft+',scrollbars='+scroll+',resizable='+resizable+',location='+location+',status='+status+',fullscreen='+fullscreen+',toolbar=no,menubar=no');
        }
	},
	singleUserSelect:function(properties){ //单选用户
		var userId = "", userName = "", hideState = "", param = "";
		var width = document.body.clientWidth-100; //网页可见区域宽
		var height = document.body.clientHeight-80; //网页可见区域高
		if(properties != undefined){
			if (properties.userId != undefined && properties.userName != undefined){
				userId = properties.userId;
				userName = encodeURI(encodeURI(properties.userName));//两次编码,后台解码
				param = "&userId="+userId+"&userName="+userName;
			}
			if (properties.hideState != undefined) param += "&hideState="+properties.hideState; //隐藏状态，默认为空；hideState=y,
			if (properties.width != undefined) width = properties.width;
			if (properties.height != undefined) height = properties.height;
		}
		$.dialog({
			id:"singlePeopleSelect", title:"人员选择框(单选)", lock: true, fixed: true, width: width+"px", height: height+"px",
    	    content: "url:"+ctx+"/sysorganization/userSelect.do?selectType=single"+param
    	});
		//return Common.dialog({url:ctx+"/sysorganization/userSelect.do?selectType=single"+param,width:600,height:600,modal:true});
	},
	multiUserSelect:function(properties){ //多选用户
		var userId = "", userName = "", hideState = "", limitSize = 50, param = "";
		var width = document.body.clientWidth-100; //网页可见区域宽
		var height = document.body.clientHeight-80; //网页可见区域高
		if(properties != undefined){
			if (properties.userId != undefined && properties.userName != undefined){
				userId = properties.userId;
				userName = encodeURI(encodeURI(properties.userName));//两次编码,后台解码
				param = "&userId="+userId+"&userName="+userName;
			}
			if (properties.hideState != undefined) param += "&hideState="+properties.hideState; //隐藏状态，默认为空；hideState=y
			if (properties.limitSize != undefined){
				param += "&limitSize="+properties.limitSize; //限制选择用户数
			}else{
				param += "&limitSize="+limitSize; //默认50个用户
			}
			if (properties.width != undefined) width = properties.width;
			if (properties.height != undefined) height = properties.height;
		}else{
			param = "&limitSize="+limitSize;
		}
		$.dialog({
			id:"multiPeopleSelect", title:"人员选择框(多选)", lock: true, fixed: true, width: width+"px", height: height+"px",
    	    content: "url:"+ctx+"/sysorganization/userSelect.do?selectType=multi"+param
    	});
		//return Common.dialog({url:ctx+"/sysorganization/userSelect.do?selectType=multi"+param,width:600,height:600,modal:true});
	},
	showBottomMsg:function(msg){ //底部居中提示,延迟3秒自动关闭
		$.messager.show({title:"提示",msg:msg,showType:"slide",timeout:3000,style:{right:"",top:"",bottom:-document.body.scrollTop-document.documentElement.scrollTop}});
	},
	clearForm:function(f_id){ //重置表单
		var f = $("#"+f_id);
 		f.find(":input:hidden").each(function(i){
 		   this.value="";
 		});
 		f.get(0).reset();
 	},
 	getFormJson:function(id){ //将form中的值转换为键值对,例如：{id:'1',name: 'name01'}
 		var paramJson = {}; 
 		var fields = $("#"+id).serializeArray(); //返回的JSON对象是由一个对象数组组成的 [ {name: 'firstname', value: 'Hello'}, {name: 'lastname', value: 'World'}, {name: 'alias'}, // this one was empty ]
 		jQuery.each(fields, function(i, field){ 
 			paramJson[this.name] = this.value || '';
 		}); 
 		return paramJson;
 	},
 	downloadFile:function(id){//公共附件下载
 		$("#frameForDownload").remove();
 		var iframe = document.createElement("iframe");
 		iframe.id = "frameForDownload";
 		iframe.src = ctx+"/filedownload?fileid="+id;
 		iframe.style.display = "none";
 		//创建完成之后，添加到body中
 		document.body.appendChild(iframe);
 	}
};

//通过省id,查找市
function getCityByPId(provinceId, cityId){
	if(provinceId != ""){
		var url = ctx+"/pubglobalregion/findCity.do";
        $.post(url, {provinceId:provinceId},function(data){
        	if(data.state == "success"){
        		var cdata = data.cityData;
           		var selectObj = document.getElementById(cityId);
           		selectObj.options.length = 0; //先清空
           		var varItem0 = new Option("请选择...", "");
           		selectObj.options.add(varItem0);
            	for(var ind=0; ind < cdata.length ; ind++){
            		var varItem = new Option(cdata[ind].regionName, cdata[ind].resourceid);
            		selectObj.options.add(varItem);
            	}
            }else{
	           	$.messager.alert("", "加载城市失败", "error");  
            }
        },"json");
  	}
}

//转大写
function numberToUpper(obj, upperId) {
	var n = obj.value;
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
        return "数据非法";
    var unit = "仟百拾亿仟百拾万仟百拾元角分", str = "";
        n += "00";
    var p = n.indexOf('.');
    if (p >= 0)
        n = n.substring(0, p) + n.substr(p+1, 2);
        unit = unit.substr(unit.length - n.length);
    for (var i=0; i < n.length; i++)
        str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    var upperStr = str.replace(/零(仟|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元");
	document.getElementById(upperId).value = upperStr;
}