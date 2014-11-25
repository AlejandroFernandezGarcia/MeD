package ampa.sa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.InstanceNotFoundException;

public class UpdateActivityWindow extends JFrame {

	private JPanel contentPane;
	private ActivityService activityService;
	private JTextField textFieldActivityName;
	private JTextField textFieldActivityPrice;

	/**
	 * Create the frame.
	 */
	public UpdateActivityWindow() {

		this.activityService = ActivityService.getInstance();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		List<Activity> listActivities = activityService.getActivities();

		JComboBox comboBox = new JComboBox();

		for (Activity a : listActivities) {
			comboBox.addItem(a.getName());
		}

		JButton btnSelect = new JButton("Seleccionar");

		JLabel lblActivityName = new JLabel("Nombre");

		JLabel lblActivityPlaces = new JLabel("Plazas");

		JLabel lblPrice = new JLabel("Precio");

		textFieldActivityName = new JTextField();
		textFieldActivityName.setColumns(10);

		JSpinner spinnerActivityPlaces = new JSpinner();
		spinnerActivityPlaces.setModel(new SpinnerNumberModel(new Integer(1),
				new Integer(1), null, new Integer(1)));

		textFieldActivityPrice = new JTextField();
		textFieldActivityPrice.setColumns(10);

		JButton btnAccept = new JButton("Aceptar");

		JButton btnCancel = new JButton("Cancelar");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				comboBox,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnSelect))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblActivityName)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				textFieldActivityName,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblActivityPlaces)
																		.addGap(18)
																		.addComponent(
																				spinnerActivityPlaces,
																				GroupLayout.PREFERRED_SIZE,
																				47,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblPrice)
																		.addGap(18)
																		.addComponent(
																				textFieldActivityPrice,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(281, Short.MAX_VALUE))
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap(258, Short.MAX_VALUE)
										.addComponent(btnCancel)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(btnAccept)
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																comboBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(btnSelect))
										.addGap(26)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblActivityName)
														.addComponent(
																textFieldActivityName,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(37)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblActivityPlaces)
														.addComponent(
																spinnerActivityPlaces,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(37)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblPrice)
														.addComponent(
																textFieldActivityPrice,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED, 24,
												Short.MAX_VALUE)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(btnAccept)
														.addComponent(btnCancel))
										.addContainerGap()));

		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Activity activity = null;
				try {
					activity = activityService.findByName(comboBox
							.getSelectedItem().toString());
				} catch (InstanceNotFoundException e1) {
					e1.printStackTrace();
				}
				textFieldActivityName.setText(activity.getName());
				spinnerActivityPlaces.setValue(activity.getPlaces());
				textFieldActivityPrice.setText(activity.getPrize().toString());
			}
		});

		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldActivityName.getText().isEmpty()
						|| textFieldActivityPrice.getText().isEmpty()) {
					String message = "Existe algun campo vac�o\n";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);

				} else {
					String number = textFieldActivityPrice.getText();

					String[] numberSplitted = number.replace(".", ",").split(
							",");
					if (numberSplitted.length > 2) {
						String message = "El precio no est� en un formato v�lido\n";
						JOptionPane.showMessageDialog(new JFrame(), message,
								"Dialog", JOptionPane.WARNING_MESSAGE);

					} else {
						Activity activity = null;
						try {
							activity = activityService.findByName(comboBox
									.getSelectedItem().toString());
						} catch (InstanceNotFoundException e1) {
							e1.printStackTrace();
						}
						BigDecimal price = null;
						try {
							if (numberSplitted.length == 2) {
								price = new BigDecimal(numberSplitted[0] + "."
										+ numberSplitted[1]);
							} else {
								price = new BigDecimal(numberSplitted[0]
										+ ".00");
							}
							int newPlaces = (int) spinnerActivityPlaces
									.getValue();
							int studentsEnrolled = activity.getStudents()
									.size();
							List<Student> s = activityService
									.findStudents(activity);
							if (newPlaces < studentsEnrolled) {
								String message = "Actualmente la actividad posee "
										+ studentsEnrolled + " matriculados\n";
								JOptionPane.showMessageDialog(new JFrame(),
										message, "Dialog",
										JOptionPane.ERROR_MESSAGE);
							} else {
								try {
									activity.setName(textFieldActivityName
											.getText());
									activity.setPlaces(newPlaces);
									activity.setPrize(price);
									activityService.update(activity);
								} catch (InstanceNotFoundException e1) {
									e1.printStackTrace();
								}

								UpdateActivityWindow thisWindow = (UpdateActivityWindow) ((JButton) e
										.getSource()).getTopLevelAncestor();
								thisWindow.setVisible(false);
								thisWindow.dispose();
							}
						} catch (NumberFormatException n) {
							String message = "El precio no est� en un formato v�lido\n";
							JOptionPane.showMessageDialog(new JFrame(),
									message, "Dialog",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateActivityWindow thisWindow = (UpdateActivityWindow) ((JButton) e
						.getSource()).getTopLevelAncestor();
				thisWindow.setVisible(false);
				thisWindow.dispose();
			}
		});

		contentPane.setLayout(gl_contentPane);
	}
}
