package com.app.core.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.app.base.entity.SysGlobalRegion;
import com.app.base.entity.SysDictionary;
import com.app.utils.Constants_App;
import com.app.utils.MemeryCacheManager;
import com.app.utils.PropertiesUtil;

/**
 * 
 * TODO：自定义标签
 * 
 * @author zhoufeng
 */
public class SelectTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String classCss; // 样式

	private String style = "";

	private String disabled;

	private String onblur;

	private String onchange;

	private String onclick;

	private String ondblclick;

	private String onfocus;

	private String dictionaryCode; // 字典编码

	private String value;

	private String pvalue; // 主要用于省市

	private String choose = "Y";

	private String size = "10";

	@Override
	public int doStartTag() throws JspException {
		StringBuilder sb = new StringBuilder("<select");
		if (StringUtils.isNotEmpty(name)) {
			if (StringUtils.isEmpty(id))
				sb.append(" id='" + name + "'");
			else
				sb.append(" id='" + id + "'");
			sb.append(" name='" + name + "'");
		}
		if (classCss != null && !classCss.equals("")) {
			sb.append(" class='" + classCss + "'");
		}
		if (style != null && !style.equals("")) {
			sb.append(" style='" + style + "'");
		}
		if (disabled != null && !disabled.equals("")) {
			sb.append(" disabled='" + disabled + "'");
		}
		if (onblur != null && !onblur.equals("")) {
			sb.append(" onblur='" + onblur + "'");
		}
		if (onchange != null && !onchange.equals("")) {
			sb.append(" onchange='" + onchange + "'");
		}
		if (onclick != null && !onclick.equals("")) {
			sb.append(" onclick='" + onclick + "'");
		}
		if (ondblclick != null && !ondblclick.equals("")) {
			sb.append(" ondblclick='" + ondblclick + "'");
		}
		if (onfocus != null && !onfocus.equals("")) {
			sb.append(" onfocus='" + onfocus + "'");
		}
		sb.append(" >");
		if (choose.equals("Y")) {
			sb.append("<option value=''>请选择...</option>");
		}

		if (StringUtils.isNotEmpty(dictionaryCode)) {
			
			if (StringUtils.equalsIgnoreCase(dictionaryCode, "D_Province")) { //省
				List<SysGlobalRegion> provinceList = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).getList(dictionaryCode, SysGlobalRegion.class);
				if (null != provinceList) {
					for (SysGlobalRegion region : provinceList) {
						sb.append("<option value='" + StringUtils.trimToEmpty(region.getResourceid()) + "'");
						if (StringUtils.isNotEmpty(value)) { // 初始化默认值
							if (StringUtils.equals(value, region.getResourceid()))
								sb.append(" selected='selected'");
						}
						sb.append(">");
						sb.append(region.getRegionName());
						sb.append("</option>");
					}
				}

			} else if(StringUtils.equalsIgnoreCase(dictionaryCode, "D_City") && StringUtils.isNotEmpty(pvalue)){ //市
				List<SysGlobalRegion> cityList = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).getList(PropertiesUtil.instance().get("D_PROVINCE")+"_"+pvalue, SysGlobalRegion.class);
				if (null != cityList) {
					for (SysGlobalRegion region : cityList) {
						sb.append("<option value='" + StringUtils.trimToEmpty(region.getResourceid()) + "'");
						if (StringUtils.isNotEmpty(value)) { // 初始化默认值
							if (StringUtils.equals(value, region.getResourceid()))
								sb.append(" selected='selected'");
						}
						sb.append(">");
						sb.append(region.getRegionName());
						sb.append("</option>");
					}
				}
				
			} else {
				// 从缓存中获取字典对象
				List<SysDictionary> childrens = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).getList(dictionaryCode, SysDictionary.class);
				if (null != childrens) {
					for (SysDictionary childDict : childrens) {
						if (StringUtils.equals(childDict.getIsValid(), "y")) {
							sb.append("<option value='" + StringUtils.trimToEmpty(childDict.getItemValue()) + "'");
							if (StringUtils.isNotEmpty(value)) { // 初始化默认值
								if (StringUtils.equals(value, childDict.getItemValue()))
									sb.append(" selected='selected'");
							} else if (StringUtils.equals(childDict.getIsDefault(), "y")) {
								sb.append(" selected='selected'");
							}
							sb.append(">");
							sb.append(childDict.getItemName());
							sb.append("</option>");
						}
					}
				}
			}

		}

		sb.append("</select>");
		JspWriter writer = this.pageContext.getOut();
		try {
			writer.append(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassCss() {
		return classCss;
	}

	public void setClassCss(String classCss) {
		this.classCss = classCss;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPvalue() {
		return pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}

	public String getDictionaryCode() {
		return dictionaryCode;
	}

	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}
