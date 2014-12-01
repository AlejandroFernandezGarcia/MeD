package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class AboutUsWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AboutUsWindow() {
		setTitle("Acerca de");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblAboutUs = new JLabel("");
		lblAboutUs.setIcon(new ImageIcon(AboutUsWindow.class.getResource("/ampa/sa/util/Logo.jpg")));
		
		JButton btnAccept = new JButton("Aceptar");
		
		JLabel lblNewLabel = new JLabel("AMPA S.A. © Copyrigth 2014. All rigths reserved");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(25))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(80)
					.addComponent(lblAboutUs)
					.addContainerGap(92, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(138)
					.addComponent(btnAccept)
					.addContainerGap(145, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAboutUs)
					.addGap(26)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(btnAccept)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutUsWindow thisWindow = (AboutUsWindow) ((JButton) e
						.getSource()).getTopLevelAncestor();
				thisWindow.setVisible(false);
				thisWindow.dispose();
			}
		});
		
		contentPane.setLayout(gl_contentPane);
	}
}
