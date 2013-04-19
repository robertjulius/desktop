package com.ganesha.test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ganesha.desktop.Looper;
import com.ganesha.desktop.ViewState;

public class View2 extends JDialog implements ViewState {

	private static final long serialVersionUID = -2253864918190912124L;
	private final JPanel contentPanel = new JPanel();

	private Looper scope;
	private ViewType viewType;
	private String stateName;

	public View2(String stateName, Looper scope, ViewType viewType) {

		this.scope = scope;
		this.viewType = viewType;
		this.stateName = stateName;

		setTitle(stateName);

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						getLooper().run("action2");
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	@Override
	public void close() {
		dispose();
	}

	@Override
	public Looper getLooper() {
		return scope;
	}

	@Override
	public String getStateName() {
		return stateName;
	}

	@Override
	public ViewType getViewType() {
		return viewType;
	}

	@Override
	public void open() {
		setVisible(true);
	}
}
