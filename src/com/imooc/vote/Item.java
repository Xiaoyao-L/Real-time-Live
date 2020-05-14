package com.imooc.vote;

public class Item {
	private String item;
	private int num;
	public Item(String name, int num) {
		this.item = name;
		this.num = num;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

}
