package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JSpinner;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

public class CreateActivityWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textActivityName;
	private ActivityService activityService;

	public CreateActivityWindow() {

		this.activityService = ActivityService.getInstance();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 378, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblActivityName = new JLabel("Nombre de la Actividad:");

		JLabel lblActivityPlaces = new JLabel(
				"N\u00BA de plazas de la Actividad:");

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
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblActivityName)
																						.addComponent(
																								lblActivityPlaces)
																						.addComponent(
																								lblActivityPrice)))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(65)
																		.addComponent(
																				btnCreateActivity)))
										.addGap(50)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(btnCancel)
														.addComponent(
																textActivityName,
																GroupLayout.DEFAULT_SIZE,
																122,
																Short.MAX_VALUE)
														.addComponent(
																spinnerActivityPlaces,
																GroupLayout.PREFERRED_SIZE,
																76,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textFieldActivityPrice))
										.addGap(33)));
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
																lblActivityName)
														.addComponent(
																textActivityName,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(52)
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
										.addGap(51)
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
										.addPreferredGap(
												ComponentPlacement.RELATED, 44,
												Short.MAX_VALUE)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																btnCreateActivity)
														.addComponent(btnCancel))
										.addContainerGap()));

		btnCreateActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textActivityName.getText().isEmpty()
						|| textFieldActivityPrice.getText().isEmpty()) {
					String message = "Existe algun campo vacío\n";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);

				} else {
					String number = textFieldActivityPrice.getText();

					String[] numberSplitted = number.replace(".", ",").split(
							",");
					System.err.println(numberSplitted.length);
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
								activityService.create(new Activity(
										textActivityName.getText(),
										(int) spinnerActivityPlaces.getValue(),
										price, students));
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
