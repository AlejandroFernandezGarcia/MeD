package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.InstanceNotFoundException;

public class DeleteActivityWindow extends JFrame {

	private JPanel contentPane;
	private ActivityService activityService;

	/**
	 * Create the frame.
	 */
	public DeleteActivityWindow() {
		setTitle("Eliminar actividad");

		this.activityService = ActivityService.getInstance();

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 328, 90);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		List<Activity> listActivities = activityService.getActivities();
		JComboBox comboBox = new JComboBox();
		for (Activity a : listActivities) {
			comboBox.addItem(a.getName());
		}

		JButton btnDelete = new JButton("Borrar");

		JButton btnAccept = new JButton("Aceptar");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
					.addComponent(btnDelete)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAccept)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnAccept)
							.addComponent(btnDelete))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(228, Short.MAX_VALUE))
		);

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Activity activity = null;
				try {
					activity = activityService.findByName(comboBox
							.getSelectedItem().toString());
					if (activity.getStudents().isEmpty()) {
						activityService.remove(activity);
						DeleteActivityWindow thisWindow = (DeleteActivityWindow) ((JButton) e
								.getSource()).getTopLevelAncestor();
						thisWindow.setVisible(false);
						thisWindow.dispose();
					} else {
						String message = "No se puede eliminar la actividad, posee "
								+ activity.getStudents().size()
								+ " matriculados\n";
						JOptionPane.showMessageDialog(new JFrame(), message,
								"Dialog", JOptionPane.ERROR_MESSAGE);
					}
				} catch (InstanceNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteActivityWindow thisWindow = (DeleteActivityWindow) ((JButton) e
						.getSource()).getTopLevelAncestor();
				thisWindow.setVisible(false);
				thisWindow.dispose();
			}
		});

		contentPane.setLayout(gl_contentPane);
	}
}
