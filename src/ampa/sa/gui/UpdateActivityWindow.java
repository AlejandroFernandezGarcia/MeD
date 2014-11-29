package ampa.sa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private JTextField textFieldActivityPrice;
	private JTextField textFieldLicensePrice;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	public UpdateActivityWindow() {
		setTitle("Modificar actividades");

		this.activityService = ActivityService.getInstance();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		List<Activity> listActivities = activityService.getActivities();
		JComboBox comboBox = new JComboBox();
		for (Activity a : listActivities) {
			comboBox.addItem(a.getName());
		}

		JButton btnSelect = new JButton("Seleccionar");

		JLabel lblActivityPlaces = new JLabel("Plazas");

		JLabel lblPrice = new JLabel("Precio");

		JSpinner spinnerActivityPlaces = new JSpinner();
		spinnerActivityPlaces.setModel(new SpinnerNumberModel(new Integer(1),
				new Integer(1), null, new Integer(1)));

		textFieldActivityPrice = new JTextField();
		textFieldActivityPrice.setColumns(10);

		JButton btnAccept = new JButton("Aceptar");

		JButton btnCancel = new JButton("Cancelar");

		JLabel lblLicense = new JLabel("Licencia");

		JRadioButton rdbtnYesLicense = new JRadioButton("Si");
		buttonGroup.add(rdbtnYesLicense);

		JRadioButton rdbtnNoLicense = new JRadioButton("No");
		buttonGroup.add(rdbtnNoLicense);

		textFieldLicensePrice = new JTextField();
		textFieldLicensePrice.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(comboBox,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(btnSelect)
										.addContainerGap(272, Short.MAX_VALUE))
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblActivityPlaces)
														.addComponent(lblPrice))
										.addGap(30)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																textFieldActivityPrice,
																0, 0,
																Short.MAX_VALUE)
														.addComponent(
																spinnerActivityPlaces,
																GroupLayout.DEFAULT_SIZE,
																47,
																Short.MAX_VALUE))
										.addContainerGap(302, Short.MAX_VALUE))
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblLicense)
										.addGap(18)
										.addComponent(rdbtnYesLicense)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(rdbtnNoLicense)
										.addGap(18)
										.addComponent(textFieldLicensePrice,
												GroupLayout.PREFERRED_SIZE, 46,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(201, Short.MAX_VALUE))
						.addGroup(
								Alignment.TRAILING,
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap(229, Short.MAX_VALUE)
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
										.addGap(27)
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
										.addGap(27)
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
										.addGap(18)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblLicense)
														.addComponent(
																rdbtnYesLicense)
														.addComponent(
																rdbtnNoLicense)
														.addComponent(
																textFieldLicensePrice,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(btnCancel)
														.addComponent(btnAccept))
										.addContainerGap(76, Short.MAX_VALUE)));

		rdbtnYesLicense.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldLicensePrice.setEnabled(true);
			}
		});

		rdbtnNoLicense.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldLicensePrice.setEnabled(false);
			}
		});

		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Activity activity = null;
				try {
					activity = activityService.findByName(comboBox
							.getSelectedItem().toString());
				} catch (InstanceNotFoundException e1) {
					e1.printStackTrace();
				}
				spinnerActivityPlaces.setValue(activity.getPlaces());
				textFieldActivityPrice.setText(activity.getPrize().toString());
				if (activity.getLicense().equals(new BigDecimal(0))) {
					rdbtnNoLicense.setSelected(true);
					textFieldLicensePrice.setEnabled(false);
				} else {
					rdbtnYesLicense.setSelected(true);
					textFieldLicensePrice.setEnabled(true);
					textFieldLicensePrice.setText(activity.getLicense()
							.toString());
				}

			}
		});

		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((int) spinnerActivityPlaces.getValue() == 0)
						|| textFieldActivityPrice.getText().isEmpty()) {
					String message = "Existe algun campo vacío\n";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);

				} else {
					String number = textFieldActivityPrice.getText();

					String[] numberSplitted = number.replace(".", ",").split(
							",");
					if (numberSplitted.length > 2) {
						String message = "El precio no está en un formato válido\n";
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
									activity.setPlaces(newPlaces);
									activity.setPrize(price);
									if (rdbtnYesLicense.isSelected()) {
										String licensePriceString = textFieldLicensePrice
												.getText().toString()
												.replaceAll(",", ".");
										activity.setLicense(new BigDecimal(
												licensePriceString));
									} else {
										activity.setLicense(new BigDecimal(0));
									}
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
							String message = "El precio no está en un formato válido\n";
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
