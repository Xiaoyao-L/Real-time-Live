package com.imooc.bearlive;

public class Error {

	public static String Error_Code_Exception = "500";
	private static String Error_Msg_Exception = "�������쳣";

	public static String getErrorMsgException(String e) {
		return Error_Msg_Exception + ":" + e;
	}

	public static String Error_Code_NoParam = "501";
	private static String Error_Msg_NoParam = "ȱ�ٲ���ֵ";

	public static String getErrorMsgNoParam(String paramName) {
		return Error_Msg_NoParam + ":" + paramName;
	}
	
	public static String Error_Code_ErrorParam = "502";
	private static String Error_Msg_ErrorParam = "����ֵ����ȷ";
	public static String getErrorMsgErrorParam(String paramName) {
		return Error_Msg_ErrorParam + ":" + paramName;
	}

	
}
