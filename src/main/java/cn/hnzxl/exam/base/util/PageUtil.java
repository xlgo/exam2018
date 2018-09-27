package cn.hnzxl.exam.base.util;

import java.util.List;

import cn.hnzxl.exam.base.model.BaseModel;

/**
 * @author ZXL
 * @date 2014年11月3日 上午1:08:27
 *
 */
public class PageUtil<MODEL extends BaseModel> {
	private int pageNum;
	private int pageSize;
	private long total;
	private int pages;
	private List<MODEL> list;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<MODEL> getList() {
		return list;
	}

	public void setList(List<MODEL> list) {
		this.list = list;
	}
}
