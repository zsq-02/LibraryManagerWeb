package com.ischoolbar.programmer.page;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ҳ��ѯ��װ
 * @author llq
 *
 */

public class Page<T> {
	private int pageNumber;//��ǰҳ��
	
	private int pageSize;//ÿҳ��ʾ����
	
	private int total;//�ܼ�¼��
	
	private int totalPage;//��ҳ��
	
	private int offset;//��Ӧ���ݿ�����α�
	
	private List<T> content = new ArrayList<T>();//�����
	
	private List<SearchProperty> searchProporties = new ArrayList<SearchProperty>();
	
	public Page(int pageNumber,int pageSize){
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.offset = (this.pageNumber - 1) * this.pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public List<SearchProperty> getSearchProporties() {
		return searchProporties;
	}

	public void setSearchProporties(List<SearchProperty> searchProporties) {
		this.searchProporties = searchProporties;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
}
