package ampa.sa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
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
import ampa.sa.util.exceptions.DuplicateInstanceException;

public class CreateActivityWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textActivityName;
	private ActivityService activityService;
	private JTextField textFieldLicensePrice;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public CreateActivityWindow() {
		setTitle("Añadir actividad");
		setResizable(false);

		this.activityService = ActivityService.getInstance();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 481, 239);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblActivityName = new JLabel("Nombre de la Actividad:");

		JLabel lblActivityPlaces = new JLabel(
				"Número de plazas: ");

		JLabel lblActivityPrice = new JLabel("Precio de la Actividad:");

		textActivityName = new JTextField();
		textActivityName.setColumns(10);

		JButton btnCreateActivity = new JButton("Aceptar");

		JButton btnCancel = new JButton("Cancelar");

		JSpinner spinnerActivityPlaces = new JSpinner();
		spinnerActivityPlaces.setModel(new SpinnerNumberModel(new Integer(1),
				new Integer(1), null, new Integer(1)));
		spinnerActivityPlaces.setValue(1);

		JTextField textFieldActivityPrice = new JTextField();

		JLabel lblNewLabel = new JLabel("Licencia");

		JRadioButton rdBtnYesLicense = new JRadioButton("Si");
		buttonGroup.add(rdBtnYesLicense);

		JRadioButton rdbtnNoLicense = new JRadioButton("No");
		rdbtnNoLicense.setSelected(true);
		buttonGroup.add(rdbtnNoLicense);

		textFieldLicensePrice = new JTextField();
		textFieldLicensePrice.setColumns(10);
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
																Alignment.TRAILING,
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblActivityPlaces)
																		.addContainerGap(
																				254,
																				Short.MAX_VALUE))
														.addGroup(
																Alignment.TRAILING,
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblActivityPrice)
																		.addContainerGap(
																				300,
																				Short.MAX_VALUE))
														.addGroup(
																Alignment.TRAILING,
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								Alignment.TRAILING,
																								gl_contentPane
																										.createSequentialGroup()
																										.addComponent(
																												lblActivityName)
																										.addGap(52))
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addComponent(
																												lblNewLabel)
																										.addGap(18)
																										.addComponent(
																												rdBtnYesLicense)
																										.addPreferredGap(
																												ComponentPlacement.UNRELATED)
																										.addComponent(
																												rdbtnNoLicense)
																										.addGap(58)))
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_contentPane
																										.createParallelGroup(
																												Alignment.TRAILING,
																												false)
																										.addComponent(
																												textFieldActivityPrice,
																												Alignment.LEADING)
																										.addComponent(
																												spinnerActivityPlaces,
																												Alignment.LEADING,
																												GroupLayout.DEFAULT_SIZE,
																												42,
																												Short.MAX_VALUE)
																										.addComponent(
																												textFieldLicensePrice,
																												Alignment.LEADING,
																												0,
																												0,
																												Short.MAX_VALUE))
																						.addGroup(
																								gl_contentPane
																										.createParallelGroup(
																												Alignment.TRAILING)
																										.addGroup(
																												gl_contentPane
																														.createSequentialGroup()
																														.addComponent(
																																btnCancel)
																														.addPreferredGap(
																																ComponentPlacement.RELATED)
																														.addComponent(
																																btnCreateActivity))
																										.addComponent(
																												textActivityName,
																												GroupLayout.PREFERRED_SIZE,
																												218,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(29)))));
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
																textActivityName,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblActivityName))
										.addGap(18)
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
										.addGap(18)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblActivityPrice)
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
																lblNewLabel)
														.addComponent(
																rdBtnYesLicense)
														.addComponent(
																rdbtnNoLicense)
														.addComponent(
																textFieldLicensePrice,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(39)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																btnCreateActivity)
														.addComponent(btnCancel))
										.addContainerGap(64, Short.MAX_VALUE)));

		textFieldLicensePrice.setEnabled(false);

		rdBtnYesLicense.addActionListener(new ActionListener() {
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

		btnCreateActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textActivityName.getText().isEmpty()
						|| textFieldActivityPrice.getText().isEmpty()
						|| (textFieldLicensePrice.getText().isEmpty() && (rdBtnYesLicense
								.isSelected()))) {
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
						Set<Student> students = new HashSet<Student>();
						BigDecimal price = null;
						try {
							if (numberSplitted.length == 2) {
								price = new BigDecimal(numberSplitted[0] + "."
										+ numberSplitted[1]);
							} else {
								price = new BigDecimal(numberSplitted[0]
										+ ".00");
							}

							try {
								if (rdBtnYesLicense.isSelected()) {
									String licensePriceString = textFieldLicensePrice
											.getText().toString()
											.replaceAll(",", ".");
									activityService.create(new Activity(
											textActivityName.getText(),
											(int) spinnerActivityPlaces
													.getValue(), price,
											new BigDecimal(licensePriceString),
											students));
								} else {
									activityService.create(new Activity(
											textActivityName.getText(),
											(int) spinnerActivityPlaces
													.getValue(), price,
											new BigDecimal(0), students));
								}

							} catch (DuplicateInstanceException e1) {

								String message = "La actividad ya existe en el sistema\n";
								JOptionPane.showMessageDialog(new JFrame(),
										message, "Dialog",
										JOptionPane.ERROR_MESSAGE);
							}

							CreateActivityWindow thisWindow = (CreateActivityWindow) ((JButton) e
									.getSource()).getTopLevelAncestor();
							thisWindow.setVisible(false);
							thisWindow.dispose();
						} catch (NumberFormatException n) {
							String message = "El precio no está en un formato válido\n";
							JOptionPane.showMessageDialog(new JFrame(),
									message, "Dialog",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateActivityWindow thisWindow = (CreateActivityWindow) ((JButton) e
						.getSource()).getTopLevelAncestor();
				thisWindow.setVisible(false);
				thisWindow.dispose();
			}
		});
		contentPane.setLayout(gl_contentPane);
	}
}
