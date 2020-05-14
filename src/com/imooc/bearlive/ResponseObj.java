package com.imooc.bearlive;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ResponseObj {

	public static String CODE_SUC = "1";
	public static String CODE_FAIL = "0";

	private String code;
	private String errCode;
	private String errMsg;
	private Object data;

	public static ResponseObj getSuccess(Object data) {
		System.err.println("getSuccess data= " + data);
		ResponseObj successObj = new ResponseObj();
		successObj.code = CODE_SUC;
		successObj.data = data;
		successObj.errCode = "";
		successObj.errMsg = "";

		return successObj;
	}

	public static ResponseObj getFail(String errCode, String errMsg) {
		ResponseObj failObj = new ResponseObj();
		failObj.code = CODE_FAIL;
		failObj.data = null;
		failObj.errCode = errCode;
		failObj.errMsg = errMsg;

		return failObj;
	}

	public static void send(HttpServletResponse resp, ResponseObj responseObj) {
		//
		PrintWriter writer = null;
		try {
			resp.setHeader("Content-type", "text/html;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			writer = resp.getWriter();
			String responseStr = new Gson().toJson(responseObj);
			writer.println(responseStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
