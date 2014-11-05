package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ActivityManagementWindow extends JFrame implements  ActionListener {

	private JPanel contentPane;
	private javax.swing.JList listAnhadidos, listNoAnhadidos;
	private ActivityService activityService;
	private JButton btnNewButton, btnNewButton_1;
	private DefaultListModel anhadidos, noAnhadidos;
	private JList list_1, list_2;
	
	public ActivityManagementWindow(Student student) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnNewButton = new JButton("A\u00F1adir ->");
		btnNewButton.addActionListener(this);
		
		btnNewButton_1 = new JButton("<- Quitar");
		btnNewButton_1.addActionListener(this);
		
        noAnhadidos = new DefaultListModel();
        anhadidos = new DefaultListModel();
		
		activityService = ActivityService.getInstance();
		List<String> activitiesStr = new ArrayList<String>();
		
		for (int i = 0; i <activityService.getActivities().size(); i++ )
			anhadidos.addElement(activityService.getActivities().get(i).getName());
		
		//anhadidos.addElement(activitiesStr);
		
		list_1 = new JList(anhadidos);
		
		list_2 = new JList(noAnhadidos);
	    
		JScrollPane scrollPane_2 = new JScrollPane(list_1);
		
		JScrollPane scrollPane_1 = new JScrollPane(list_2);
		
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
	
		contentPane.setLayout(gl_contentPane);
	    
	}
	
	public void actionPerformed(ActionEvent e) 
    {
        int i = 0;
        
        // When the 'in' button is pressed,
        // we take the indices and values of the selected items
        // and output them to an array.

        if(e.getSource() == btnNewButton)
        {
            int[] fromindex = list_1.getSelectedIndices();
			Object[] from = list_1.getSelectedValues();

            // Then, for each item in the array, we add them to
            // the other list.
            for(i = 0; i < from.length; i++)
            {
                noAnhadidos.addElement(from[i]);
            }
            
            // Finally, we remove the items from the first list.
            // We must remove from the bottom, otherwise we try to 
            // remove the wrong objects.
            for(i = (fromindex.length-1); i >=0; i--)
            {
                anhadidos.remove(fromindex[i]);
            }
        }
        
        // If the out button is pressed, we take the indices and values of
        // the selected items and output them to an array.
        else if(e.getSource() == btnNewButton_1)
        {
            @SuppressWarnings("deprecation")
			Object[] to = list_2.getSelectedValues();
            int[] toindex = list_2.getSelectedIndices();
            
            // Then, for each item in the array, we add them to
            // the other list.
            for(i = 0; i < to.length; i++)
            {
                anhadidos.addElement(to[i]);
            }
            
            // Finally, we remove the items from the first list.
            // We must remove from the bottom, otherwise we try to
            // remove the wrong objects.
            for(i = (toindex.length-1); i >=0; i--)
            {
                noAnhadidos.remove(toindex[i]);
            }
        }
    }

	
	
}

