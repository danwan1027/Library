import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import javax.swing.JMenuBar;
import java.util.ArrayList;
import javax.swing.table.*;


public class Table extends JFrame implements ActionListener{
private String [] dataTitle = {"書名","作者","出版社","借閱狀態"};
private JPanel contentPane;
private JTextField textField;
private JTable table;
private JScrollPane scrollPane;
public Member login_user ;
public ListSelectionListener table_select_action_listner = new ListSelectionListener(){

	@Override

	//點選table顯示書名
	public void valueChanged(ListSelectionEvent e) {
		textField.setText(_table.getModel().getValueAt(_table.getSelectedRow(),0).toString());
		// for(int c=0;c<dataTitle.length;c++){
		// 	System.out.print(_table.getModel().getValueAt(_table.getSelectedRow(),c)+"|");
		// }
	}
	};



// 宣告table
	JTable _table;
//更新table
	public void update_table(ArrayList<Book> show_books){
		DefaultTableModel dftmodel = (DefaultTableModel)this._table.getModel();
		dftmodel.getDataVector().removeAllElements();//remove all elements in table

		// remove action event
		this._table.getSelectionModel().removeListSelectionListener(this.table_select_action_listner);

		for(Book book: show_books){
			dftmodel.addRow(book.toObjectArray());
		}
		dftmodel.fireTableDataChanged();
		this._table.setModel(dftmodel);
	}


//Constructor
	public Table(Member user) {
		this.login_user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel lblNewLabel = new JLabel("書名");
		lblNewLabel.setBounds(338, 190, 36, 15);
		contentPane.add(lblNewLabel);

        JLabel imagelabel = new JLabel( new ImageIcon("Gary.jpg"));
        // imagelabel.setbounds(200,200,50, 50);
        imagelabel.setBounds(386, 44, 124, 119);
        contentPane.add(imagelabel);

		textField = new JTextField();
		textField.setBounds(377, 186, 152, 21);
		contentPane.add(textField);
		textField.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBounds(314, 219, 248, 33);
		contentPane.add(panel);
		//按查詢做的事(模糊搜尋：找書可以同時找作者、書名、出版商、借書狀態)
		JButton btnNewButton = new JButton("查詢");
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Book> search_book_list= Library.fuzzy_search_book(textField.getText());
				update_table(search_book_list);
				//將選取table事件加回來
				_table.getSelectionModel().addListSelectionListener(table_select_action_listner);
			}
		});

		JButton btnNewButton_1 = new JButton("借書");
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String book_title = textField.getText();
				Book b_book = Library.getBookbyTitle(book_title);
				login_user.borrowBook_parameter(b_book);
				update_table(Library.books);
				_table.getSelectionModel().addListSelectionListener(table_select_action_listner);
			}
		});

		JButton btnNewButton_2 = new JButton("還書");
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login_user.returnBook(textField.getText());
				textField.setText("");
				update_table(Library.books);
				_table.getSelectionModel().addListSelectionListener(table_select_action_listner);
			}
		});

		DefaultTableModel init_model = new DefaultTableModel() ;
// 設table上的column
		init_model.setColumnIdentifiers(this.dataTitle);

		this._table = new JTable(init_model) {
			public boolean isCellEditable(int data, int dataTitle) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r,  int data, int dataTitle) {
				Component c = super.prepareRenderer(r, data, dataTitle);
				String book_state = _table.getValueAt(data,3).toString();
				if (book_state.equals("lend")){
					c.setBackground(Color.pink);
				}
				else{
					c.setBackground(Color.white);
				}
				// if(data % 2 == 0 ) { // 偶數行底為白色
				// 	c.setBackground(Color.white);
				// }
				// else {  // 奇數行底為灰色
				// 	c.setBackground(Color.LIGHT_GRAY);
				// }
				if (isCellSelected(data, dataTitle)) {  // 被點到的底變藍色
					c.setBackground(Color.LIGHT_GRAY);
				}
				return c;
			}
		};
		this.update_table(Library.books);
		this._table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this._table.setBounds(0, 314, 216, -313);
		this._table.getSelectionModel().addListSelectionListener(table_select_action_listner);


		scrollPane = new JScrollPane(this._table);
		scrollPane.setBounds(0, 1, 302, 271);
		contentPane.add(scrollPane);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(511, 1, 57, 22);
		contentPane.add(menuBar);

		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		JMenuItem menuitem1 = new JMenuItem("Information");
		menu.add(menuitem1);
		menuitem1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        login_user.show_information();
      }
    });

		JMenuItem menuitem2 = new JMenuItem("History");
		menu.add(menuitem2);
		menuitem2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        login_user.show_history();
      }
    });

		JMenuItem menuitem3 = new JMenuItem("Log out");
		menu.add(menuitem3);
		menuitem3.addActionListener(new ActionListener() {
      		public void actionPerformed(ActionEvent ev) {
        		Library.Login_GUI();
				dispose();
				return;
      }
    });

		this.setVisible(true);
	}
	// End constructor


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }


}
