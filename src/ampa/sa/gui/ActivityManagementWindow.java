package ampa.sa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.InstanceNotFoundException;

public class ActivityManagementWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private javax.swing.JList listNotMatriculated, listMatriculated;
	private Student student;
	private ActivityService activityService;
	private FamilyService familyService;
	private JButton matriculateButton, unMatriculateButton, acceptButton,
			cancelButton;
	private DefaultListModel notMatriculated, matriculated;
	private JList list_1, list_2;
	private MainWindow mainWindow;

	public ActivityManagementWindow(MainWindow mainWindow, Student stu) {
		setTitle("Modificar actividades de un alumno");
		setResizable(false);

		this.mainWindow = mainWindow;
		this.student = stu;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 457, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		matriculateButton = new JButton("Matricular ->");
		matriculateButton.addActionListener(this);

		unMatriculateButton = new JButton("<- Desmatricular");
		unMatriculateButton.addActionListener(this);

		matriculated = new DefaultListModel();
		notMatriculated = new DefaultListModel();

		activityService = ActivityService.getInstance();
		familyService = FamilyService.getInstance();
		List<String> activitiesStr = new ArrayList<String>();

		List<Activity> activities = activityService.getActivities();
		Set<Activity> activitiesStudent = student.getActivities();

		Iterator<Activity> iter;
		Activity aux;
		iter = activities.iterator();
		while (iter.hasNext()) {
			aux = iter.next();
			if (activitiesStudent.contains(aux)) {
				matriculated.addElement(aux.getName());
			} else {
				notMatriculated.addElement(aux.getName());
			}
		}

		list_1 = new JList(notMatriculated);

		list_2 = new JList(matriculated);

		JScrollPane scrollPane_2 = new JScrollPane(list_1);

		JScrollPane scrollPane_1 = new JScrollPane(list_2);

		acceptButton = new JButton("Aceptar");
		acceptButton.addActionListener(this);

		cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(this);

		JLabel lblNewLabel = new JLabel("Estudiante:");
		lblNewLabel.setName("lblNewLabel");
		lblNewLabel.setText("Estudiante: " + student.getName() + " "
				+ student.getLastname());

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addGap(26)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblNewLabel)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								matriculateButton,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								scrollPane_2,
																								GroupLayout.DEFAULT_SIZE,
																								123,
																								Short.MAX_VALUE))
																		.addGap(28)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								unMatriculateButton,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								scrollPane_1,
																								GroupLayout.DEFAULT_SIZE,
																								130,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								acceptButton,
																								GroupLayout.DEFAULT_SIZE,
																								106,
																								Short.MAX_VALUE)
																						.addComponent(
																								cancelButton,
																								GroupLayout.DEFAULT_SIZE,
																								118,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addComponent(lblNewLabel,
												GroupLayout.PREFERRED_SIZE, 32,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				scrollPane_1,
																				GroupLayout.DEFAULT_SIZE,
																				210,
																				Short.MAX_VALUE)
																		.addGroup(
																				gl_contentPane
																						.createSequentialGroup()
																						.addComponent(
																								acceptButton)
																						.addGap(15)
																						.addComponent(
																								cancelButton)))
														.addComponent(
																scrollPane_2,
																GroupLayout.DEFAULT_SIZE,
																210,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																unMatriculateButton)
														.addComponent(
																matriculateButton))
										.addContainerGap()));

		contentPane.setLayout(gl_contentPane);

	}

	public void actionPerformed(ActionEvent e) {
		int i = 0;

		// When the 'in' button is pressed,
		// we take the indices and values of the selected items
		// and output them to an array.

		if (e.getSource() == matriculateButton) {
			int[] fromindex = list_1.getSelectedIndices();
			Object[] from = list_1.getSelectedValuesList().toArray();

			// Then, for each item in the array, we add them to
			// the other list.
			for (i = 0; i < from.length; i++) {
				matriculated.addElement(from[i]);
			}

			// Finally, we remove the items from the first list.
			// We must remove from the bottom, otherwise we try to
			// remove the wrong objects.
			for (i = (fromindex.length - 1); i >= 0; i--) {
				notMatriculated.remove(fromindex[i]);
			}
		}

		// If the out button is pressed, we take the indices and values of
		// the selected items and output them to an array.
		else if (e.getSource() == unMatriculateButton) {
			Object[] to = list_2.getSelectedValuesList().toArray();
			int[] toindex = list_2.getSelectedIndices();

			// Then, for each item in the array, we add them to
			// the other list.
			for (i = 0; i < to.length; i++) {
				notMatriculated.addElement(to[i]);
			}

			// Finally, we remove the items from the first list.
			// We must remove from the bottom, otherwise we try to
			// remove the wrong objects.
			for (i = (toindex.length - 1); i >= 0; i--) {
				matriculated.remove(toindex[i]);
			}
		} else if (e.getSource() == acceptButton) {

			for (i = 0; i < notMatriculated.size(); i++) {
				Activity activity = null;
				try {
					activity = activityService.findByName(notMatriculated
							.get(i).toString());
				} catch (InstanceNotFoundException e1) {
					e1.printStackTrace();
				}
				activityService.unEnrollsStudentToActivity(student, activity);
			}

			List<Activity> exceededCapacityActivities = new ArrayList<Activity>();
			
			for (i = 0; i < matriculated.size(); i++) {
				Activity activity = null;
				try {
					activity = activityService.findByName(matriculated.get(i)
							.toString());
				} catch (InstanceNotFoundException e1) {
					e1.printStackTrace();
				}
				activityService.enrollmentStudentInActivity(student, activity);
				
				if (activityService.exceededCapacity(activity)) {
					exceededCapacityActivities.add(activity);
				}	
			}
			
			if (exceededCapacityActivities.size() != 0) {
				String message = "";
				if (exceededCapacityActivities.size() > 1) {
					message += "Existen actividades cuyo número de matriculados excede al número de plazas: ";
				} else {
					message += "Existe una actividad cuyo número de matriculados excede al número de plazas: ";
				}
				
				for (i = 0; i < exceededCapacityActivities.size(); i++) {
					if (i > 0) {
						message += ", ";
					}
					message += exceededCapacityActivities.get(i).getName();
				}

				JOptionPane.showMessageDialog(new JFrame(),
						message, "Alerta",
						JOptionPane.WARNING_MESSAGE);
			}
			
			
			ActivityManagementWindow thisWindow = (ActivityManagementWindow) ((JButton) e
					.getSource()).getTopLevelAncestor();
			mainWindow.fillRightPanel(mainWindow.getRigthPanel());
			thisWindow.setVisible(false);
			thisWindow.dispose();
		} else if (e.getSource() == cancelButton) {
			ActivityManagementWindow thisWindow = (ActivityManagementWindow) ((JButton) e
					.getSource()).getTopLevelAncestor();
			thisWindow.setVisible(false);
			thisWindow.dispose();
		}
	}
}
