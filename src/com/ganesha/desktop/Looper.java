package com.ganesha.desktop;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.JOptionPane;

import com.ganesha.core.exception.AppException;
import com.ganesha.desktop.ViewState.ViewType;

public class Looper {
	private Map<String, State> states;
	private Form form;
	private String currentState;
	private String name;
	// private ViewState lastView;
	private Stack<ViewState> views;

	public Looper(String name) {
		states = new HashMap<>();
		views = new Stack<>();
		this.name = name;
	}

	public void addState(State state) throws AppException {
		if (states.containsKey(state.getStateName())) {
			throw new AppException("State '" + state.getStateName()
					+ "' already exists in loop '" + name + "'");
		}
		states.put(state.getStateName(), state);
	}

	public String getCurrentState() {
		return currentState;
	}

	public Form getForm() {
		return form;
	}

	public State getState(String stateName) throws AppException {
		if (!states.containsKey(stateName)) {
			throw new AppException("State '" + stateName
					+ "' is not found in loop '" + name + "'");
		}
		return states.get(stateName);
	}

	public void run(String nextState) {
		boolean initial = false;
		try {
			if (currentState == null) {
				if (nextState.equals("initial")) {
					initial = true;
				} else {
					throw new AppException(new NullPointerException(
							"currentState is null"));
				}
			}

			State next = getState(nextState);
			State current = null;

			Form form = null;

			if (!initial) {
				current = getState(currentState);
				if (ViewState.class.isAssignableFrom(current.getClass())) {
					assignViewToForm((ViewState) current, form);
				}
			}

			while (ActionState.class.isAssignableFrom(next.getClass())) {
				ActionState actionState = (ActionState) next;
				actionState.execute(nextState);
				currentState = nextState;
				nextState = actionState.getNextState();
				next = getState(nextState);
				current = getState(currentState);
			}

			if (ViewState.class.isAssignableFrom(next.getClass())) {
				ViewState nextView = (ViewState) next;
				assignFormToView(form, nextView);

				ViewState lastView = views.isEmpty() ? null : views.peek();
				if (nextView.getClass().equals(lastView)) {
					/*
					 * Do nothing
					 */
				} else if (lastView != null
						&& lastView.getViewType()
								.equals(nextView.getViewType())) {
					nextView.open();
					if (lastView != null) {
						lastView.close();
						views.pop();
					}
					views.push(nextView);
				} else if (nextView.getViewType().equals(ViewType.PAGE)) {
					if (lastView != null) {
						lastView.close();
						views.pop();
					}
				} else if (nextView.getViewType().equals(ViewType.POPUP)) {
					views.push(nextView).open();
				} else {
					throw new AppException("Unkown combination!");
				}
			} else {
				throw new AppException("State '" + next + "' is not supported");
			}
			currentState = nextState;
		} catch (AppException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {

		}
	}

	public void setForm(Form form) {
		this.form = form;
	}

	private void assignFormToView(Form form, ViewState view) {

	}

	private void assignViewToForm(ViewState view, Form form) {

	}
}
