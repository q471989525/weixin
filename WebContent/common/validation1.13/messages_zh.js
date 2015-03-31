(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery", "../jquery.validate"], factory );
	} else {
		factory( jQuery );
	}
}(function( $ ) {

/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
$.extend($.validator.messages, {
	required: "必须填写",
	remote: "请修正此栏位",
	email: "请输入有效的电子邮件",
	url: "请输入有效的网址",
	date: "请输入有效的日期",
	dateISO: "请输入有效的日期 (YYYY-MM-DD)",
	number: "请输入正确的数字",
	digits: "只可输入整数",
	creditcard: "请输入有效的信用卡号码",
	equalTo: "你的输入不相同",
	extension: "请输入有效的后缀",
	maxlength: $.validator.format("最多 {0} 个字"),
	minlength: $.validator.format("最少 {0} 个字"),
	rangelength: $.validator.format("请输入长度为 {0} 至 {1} 之間的字串"),
	range: $.validator.format("请输入 {0} 至 {1} 之间的数值"),
	max: $.validator.format("请输入不大于 {0} 的数值"),
	min: $.validator.format("请输入不小于 {0} 的数值")
});

}));

/*
下面是默认校验规则，也可以自定义规则
(1)required:true 必输字段
(2)remote:"check.php" 使用ajax方法调用check.php验证输入值
(3)email:true 必须输入正确格式的电子邮件
(4)url:true 必须输入正确格式的网址
(5)date:true 必须输入正确格式的日期
(6)dateISO:true 必须输入正确格式的日期(ISO)，例如：2009-06-23，1998/01/22 只验证格式，不验证有效性
(7)number:true 必须输入合法的数字(负数，小数)
(8)digits:true 必须输入整数
(9)creditcard: 必须输入合法的信用卡号
(10)equalTo:"#field" 输入值必须和#field相同
(11)accept: 输入拥有合法后缀名的字符串（上传文件的后缀）
(12)maxlength:5 输入长度最多是5的字符串(汉字算一个字符)
(13)minlength:10 输入长度最小是10的字符串(汉字算一个字符)
(14)rangelength:[5,10] 输入长度必须介于 5 和 10 之间的字符串")(汉字算一个字符)
(15)range:[5,10] 输入值必须介于 5 和 10 之间
(16)max:5 输入值不能大于5
(17)min:10 输入值不能小于10
*/