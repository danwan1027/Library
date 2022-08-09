import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class Search_table extends JFrame {
	private String [][] data= {{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Hello","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Hello","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Hello","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Book","Lisa","","可借閱"},{"Hello","Lisa","","可借閱"},{"Book","Lisa","","可借閱"}};
	private String [] dataTitle = {"書名","作者","出版社","借閱狀態"};
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	private JScrollPane scrollPane;


	public static void main(String[] args) {

		Search_table frame = new Search_table();
		frame.setVisible(true);
			
	}


	public Search_table() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("書名");
		lblNewLabel.setBounds(334, 35, 36, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(334, 49, 194, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("作者");
		lblNewLabel_1.setBounds(334, 80, 36, 15);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(334, 95, 194, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("出版社");
		lblNewLabel_2.setBounds(334, 131, 58, 15);
		contentPane.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(334, 144, 194, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(334, 183, 194, 33);
		contentPane.add(panel);
		
		JButton btnNewButton = new JButton("查詢");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("借書");
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("還書");
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		table = new JTable(data,dataTitle) {
			public boolean isCellEditable(int data, int dataTitle) {
				return false;
			}
			
			public Component prepareRenderer(TableCellRenderer r,  int data, int dataTitle) {
				Component c = super.prepareRenderer(r, data, dataTitle);
				if(data % 2 == 0 ) {
					c.setBackground(Color.white);
				}
				else {
					c.setBackground(Color.LIGHT_GRAY);
				}
				if (isCellSelected(data, dataTitle)) {
					c.setBackground(Color.blue);
				}
				return c;
			}

		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(0, 314, 216, -313);
		//contentPane.add(table);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 1, 302, 271);
		contentPane.add(scrollPane);
		
		
	}
}
