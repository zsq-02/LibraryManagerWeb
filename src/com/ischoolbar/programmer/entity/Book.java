package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Column;

/**
 * ͼ��ʵ��
 * @author llq
 *
 */
public class Book extends BaseEntity {
	
	private String name;//ͼ������

	@Column(name="book_category",isForeignEntity=true)
	private BookCategory bookCategory;//ͼ�����
	
	private int status;//ͼ��״̬
	
	private int number = 1;//ͼ������
	
	private int freeNumber = 1;//�ɽ�ͼ������
	
	private String photo;//ͼ����Ƭ
	
	private String info;//ͼ�����
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BookCategory getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(BookCategory bookCategory) {
		this.bookCategory = bookCategory;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getFreeNumber() {
		return freeNumber;
	}

	public void setFreeNumber(int freeNumber) {
		this.freeNumber = freeNumber;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
