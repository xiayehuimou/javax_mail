package com.example.demo.mail;


import java.io.Serializable;

/**
 * 
 * @author wanghao
 */
public class ResultBean implements Serializable, MsgNames {
	/**
	 * 
	 */
	private static final long serialVersionUID = 541111297236898043L;

	private boolean status = true;
	private String messages;
	private Object data;
	// 当前页
	private int page;
	// 总条数
	private int record;
	// 总页数
	private int total;

	public static ResultBean success() {
		return success("");
	}

	public static ResultBean error() {
		return error("");
	}

	public static ResultBean success(String code, String... args) {
		ResultBean result = new ResultBean();
		result.setMessages(MessageUtil.getMessage(code, args));
		return result;
	}

	public static ResultBean error(String code, String... args) {
		ResultBean result = new ResultBean();
		result.setStatus(false);
		result.setMessages(MessageUtil.getMessage(code, args));
		return result;
	}

	public static ResultBean result(Boolean bool) {
		if (bool) {
			return ResultBean.success(MSG_S_HANDLER);
		} else {
			return ResultBean.error(MSG_E_HANDLER);
		}
	}

	public static ResultBean result(Boolean bool, String success, String fail) {
		if (bool) {
			return ResultBean.success(success);
		} else {
			return ResultBean.error(fail);
		}
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
