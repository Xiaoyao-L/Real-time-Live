package com.imooc.bearlive.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAction {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) ;
}
