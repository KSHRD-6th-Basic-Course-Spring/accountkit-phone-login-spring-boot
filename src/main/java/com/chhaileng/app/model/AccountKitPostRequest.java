package com.chhaileng.app.model;

public class AccountKitPostRequest {
	private String csrf;
	private String code;

	public String getCsrf() {
		return csrf;
	}

	public void setCsrf(String csrf) {
		this.csrf = csrf;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "AccountKitPostRequest [csrf=" + csrf + ", code=" + code + "]";
	}

}
