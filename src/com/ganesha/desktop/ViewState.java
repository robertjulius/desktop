package com.ganesha.desktop;

public interface ViewState extends State {

	public enum ViewType {
		PAGE, POPUP;
	}

	public void close();

	public ViewType getViewType();

	public void open();
}
