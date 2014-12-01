package ampa.sa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;

public class ListActivitiesWindow extends JFrame {

	private JPanel contentPane;
	private JTable activitiesTable;
	private ActivityService activityService;

	/**
	 * Create the frame.
	 */
	public ListActivitiesWindow() {
		setTitle("Lista de actividades");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 239);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		JButton btnAcceptButton = new JButton("Aceptar");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAcceptButton))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAcceptButton)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		activitiesTable = new JTable();
		activitiesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activitiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		activitiesTable.setModel(new DefaultTableModel(new Object[][] { { null,
				null, null, null, null }, }, new String[] { "Actividad", "Plazas",
				"Matriculados", "Precio", "Licencia" }) {
			Class[] columnTypes = new Class[] { String.class, Integer.class,
					Integer.class, BigDecimal.class, BigDecimal.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(activitiesTable);
		fillActivitiesTable();

		btnAcceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListActivitiesWindow thisWindow = (ListActivitiesWindow) ((JButton) e
						.getSource()).getTopLevelAncestor();
				thisWindow.setVisible(false);
				thisWindow.dispose();
			}
		});
		contentPane.setLayout(gl_contentPane);

	}

	public void fillActivitiesTable() {
		activityService = ActivityService.getInstance();
		List<Activity> activities = activityService.getActivities();

		DefaultTableModel dtm = (DefaultTableModel) activitiesTable.getModel();
		for (int i = 0; i < dtm.getRowCount(); i++) {
			dtm.removeRow(i);
		}

		for (Activity activity : activities) {
			Object[] data = { activity.getName(), activity.getPlaces(),
					activity.getStudents().size(), activity.getPrize(),
					activity.getLicense() };
			dtm.addRow(data);
		}
		activitiesTable.updateUI();
	}

}
