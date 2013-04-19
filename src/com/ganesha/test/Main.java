package com.ganesha.test;

import com.ganesha.core.exception.AppException;
import com.ganesha.desktop.Action;
import com.ganesha.desktop.ActionState;
import com.ganesha.desktop.Looper;
import com.ganesha.desktop.ViewState.ViewType;

public class Main {

	public static void main(String[] args) throws AppException {
		test1();
	}

	public static void test1() throws AppException {
		Looper looper = new Looper("Test");
		looper.setForm(new TestForm());

		Action action = new TestAction();
		looper.addState(new ActionState("initial", looper, action, "View_1"));
		looper.addState(new ActionState("action1", looper, action, "View_2"));
		looper.addState(new ActionState("action2", looper, action, "action3"));
		looper.addState(new ActionState("action3", looper, action, "action4"));
		looper.addState(new ActionState("action4", looper, action, "View_3"));

		looper.addState(new View1("View_1", looper, ViewType.PAGE));
		looper.addState(new View2("View_2", looper, ViewType.POPUP));
		looper.addState(new View3("View_3", looper, ViewType.PAGE));

		looper.run("initial");
	}
}
