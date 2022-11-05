
import java.awt.*;
import java.awt.event.*;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.*;
/*
 * Created by JFormDesigner on Fri Nov 04 18:07:15 CET 2022
 */



/**
 * @author Fabian Ke�ler
 */
public class MenuCanvas extends Canvas {
    public MenuCanvas() {
		initComponents();

        textFieldHostIP.setText(Networking.getOwnIP() + ":" + Networking.PORT);
	}

	private void joinClicked(ActionEvent e) {
        Networking.startClient(textFieldJoinIP.getText());
        GameWindow.window.switchCanvas(new GameCanvas());
	}

	private void hostClicked(ActionEvent e) {
        Networking.startServer();
        GameWindow.window.switchCanvas(new GameCanvas());
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
		// Generated using JFormDesigner Evaluation license - Fabian Ke�ler
		label1 = new JLabel();
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		label2 = new JLabel();
		textFieldJoinName = new JTextField();
		textFieldJoinIP = new JTextField();
		label3 = new JLabel();
		label4 = new JLabel();
		buttonJoin = new JButton();
		panel3 = new JPanel();
		label5 = new JLabel();
		textFieldHostName = new JTextField();
		textFieldHostIP = new JTextField();
		label6 = new JLabel();
		label7 = new JLabel();
		buttonHost = new JButton();

		//---- label1 ----
		label1.setText("Multiplayer");
		label1.setFont(new Font("Rubik", Font.BOLD, 48));
		add(label1);
		label1.setBounds(new Rectangle(new Point(55, 20), label1.getPreferredSize()));

		//======== tabbedPane1 ========
		{
			tabbedPane1.setTabPlacement(SwingConstants.LEFT);

			//======== panel1 ========
			{
				panel1.setLayout(null);

				//---- label2 ----
				label2.setText("Join a Game");
				label2.setFont(new Font("Segoe UI Light", Font.PLAIN, 28));
				panel1.add(label2);
				label2.setBounds(new Rectangle(new Point(85, 5), label2.getPreferredSize()));
				panel1.add(textFieldJoinName);
				textFieldJoinName.setBounds(120, 65, 155, textFieldJoinName.getPreferredSize().height);
				panel1.add(textFieldJoinIP);
				textFieldJoinIP.setBounds(120, 110, 155, 22);

				//---- label3 ----
				label3.setText("Name");
				panel1.add(label3);
				label3.setBounds(new Rectangle(new Point(30, 65), label3.getPreferredSize()));

				//---- label4 ----
				label4.setText("IP:Port");
				panel1.add(label4);
				label4.setBounds(30, 110, 50, 16);

				//---- buttonJoin ----
				buttonJoin.setText("Play");
				buttonJoin.setFont(new Font("Quicksand Light", Font.BOLD, 20));
				buttonJoin.addActionListener(e -> joinClicked(e));
				panel1.add(buttonJoin);
				buttonJoin.setBounds(95, 160, 100, 30);

				{
					// compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < panel1.getComponentCount(); i++) {
						Rectangle bounds = panel1.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = panel1.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					panel1.setMinimumSize(preferredSize);
					panel1.setPreferredSize(preferredSize);
				}
			}
			tabbedPane1.addTab("Join", panel1);

			//======== panel3 ========
			{
				panel3.setLayout(null);

				//---- label5 ----
				label5.setText("Host a Game");
				label5.setFont(new Font("Segoe UI Light", Font.PLAIN, 28));
				panel3.add(label5);
				label5.setBounds(new Rectangle(new Point(85, 5), label5.getPreferredSize()));
				panel3.add(textFieldHostName);
				textFieldHostName.setBounds(120, 65, 155, textFieldHostName.getPreferredSize().height);

				//---- textFieldHostIP ----
				textFieldHostIP.setEditable(false);
				textFieldHostIP.setText("[YOUR IP:PORT]");
				panel3.add(textFieldHostIP);
				textFieldHostIP.setBounds(120, 110, 155, 22);

				//---- label6 ----
				label6.setText("Name");
				panel3.add(label6);
				label6.setBounds(new Rectangle(new Point(30, 65), label6.getPreferredSize()));

				//---- label7 ----
				label7.setText("IP:Port");
				panel3.add(label7);
				label7.setBounds(30, 110, 50, 16);

				//---- buttonHost ----
				buttonHost.setText("Play");
				buttonHost.setFont(new Font("Quicksand Light", Font.BOLD, 20));
				buttonHost.addActionListener(e -> hostClicked(e));
				panel3.add(buttonHost);
				buttonHost.setBounds(95, 160, 100, 30);

				{
					// compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < panel3.getComponentCount(); i++) {
						Rectangle bounds = panel3.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = panel3.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					panel3.setMinimumSize(preferredSize);
					panel3.setPreferredSize(preferredSize);
				}
			}
			tabbedPane1.addTab("Host", panel3);
		}
		add(tabbedPane1);
		tabbedPane1.setBounds(20, 90, 360, 195);

		{
			// compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < getComponentCount(); i++) {
				Rectangle bounds = getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			setMinimumSize(preferredSize);
			setPreferredSize(preferredSize);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
	// Generated using JFormDesigner Evaluation license - Fabian Ke�ler
	private JLabel label1;
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JLabel label2;
	private JTextField textFieldJoinName;
	private JTextField textFieldJoinIP;
	private JLabel label3;
	private JLabel label4;
	private JButton buttonJoin;
	private JPanel panel3;
	private JLabel label5;
	private JTextField textFieldHostName;
	private JTextField textFieldHostIP;
	private JLabel label6;
	private JLabel label7;
	private JButton buttonHost;
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
