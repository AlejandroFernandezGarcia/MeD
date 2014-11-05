package ampa.sa.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ActivityManagementWindow extends JFrame {

	private JPanel contentPane;
	private javax.swing.JList listAnhadidos;
	private ActivityService activityService;
	
	
	public ActivityManagementWindow(Student student) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("A\u00F1adir ->");
		
		JButton btnNewButton_1 = new JButton("<- Quitar");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton btnNewButton_2 = new JButton("Aceptar");
		
		JButton btnNewButton_3 = new JButton("Cancelar");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addGap(22)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
								.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(btnNewButton_1))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(7)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
								.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton_1)
								.addComponent(btnNewButton)))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGap(27)
							.addComponent(btnNewButton_2)
							.addGap(35)
							.addComponent(btnNewButton_3)))
					.addContainerGap())
		);
		
		activityService = ActivityService.getInstance();
		List<String> activitiesStr = new ArrayList<String>();
		
		for (int i = 0; i <activityService.getActivities().size(); i++ )
			activitiesStr.add(activityService.getActivities().get(i).getName());
		
		JList list_1 = new JList();
		scrollPane_1.setViewportView(list_1);
		
		JList list_2 = new JList(activitiesStr.toArray());
		scrollPane_2.setViewportView(list_2);
		contentPane.setLayout(gl_contentPane);
		
		
	}
	
	
}

