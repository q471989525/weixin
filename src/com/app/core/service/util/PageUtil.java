package com.app.core.service.util;

/**
 * easyui分页帮助类
 * 
 * @author ZF
 * @Apr 15, 2011
 */
public class PageUtil {

	// 每页显示多少记录数
	int rows = 10;

	// 当前页
	int page = 1;

	// 总页数
	int totalPage = 0;

	// 开始行
	int startRow = 0;

	// 总记录数
	int totalRows = 0;

	// 排序字段
	String sort;

	// 顺序 asc or desc
	String order;

	public PageUtil() {

	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		/** 开始计算 * */
		if (this.totalRows > 0) {
			if (0 == totalRows % rows) { // 计算总页数
				totalPage = totalRows / rows;
			} else {
				totalPage = totalRows / rows + 1;
			}

			if (getPage() < 1) { // 如果当前页为0,则默认为1
				page = 1;
			} else if (getPage() > totalPage) { // 如果当前页大于总页数，则为最后一页
				page = totalPage;
			}

			startRow = (page - 1) * rows; // 计算开始行
		}
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
