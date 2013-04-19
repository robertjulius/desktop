package com.ganesha.desktop;

public interface Action {
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";

	public void initial(Looper looper);
}
