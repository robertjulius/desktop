package com.ganesha.desktop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ganesha.core.exception.AppException;

public class ActionState implements State {
	private Looper scope;
	private Object action;
	private String stateName;
	private String nextState;

	public ActionState(String stateName, Looper scope, Object action,
			String nextState) throws AppException {
		this.scope = scope;
		this.action = action;
		this.stateName = stateName;
		this.nextState = nextState;
	}

	public void execute(String methodName) throws AppException {
		try {
			preExecute();

			Class<?> clazz = action.getClass();
			Method method = clazz.getMethod(methodName, scope.getClass());
			method.invoke(action, scope);

			postExecute();

		} catch (NoSuchMethodException e) {
			throw new AppException(e);
		} catch (SecurityException e) {
			throw new AppException(e);
		} catch (IllegalAccessException e) {
			throw new AppException(e);
		} catch (IllegalArgumentException e) {
			throw new AppException(e);
		} catch (InvocationTargetException e) {
			throw new AppException(e.getTargetException());
		} finally {

		}
	}

	public Object getAction() {
		return action;
	}

	@Override
	public Looper getLooper() {
		return scope;
	}

	@Override
	public String getStateName() {
		return stateName;
	}

	public String getNextState() {
		return nextState;
	}

	public void postExecute() {

	}

	public void preExecute() {

	}
}
