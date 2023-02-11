import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class ToDoList {
	private int money = 0;
	
	public ToDoList() {
		JFrame frame = new JFrame("Piggy Planner");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		
		// Top Menu Bar
		JMenuBar mb = new JMenuBar();
		JMenu m1 = new JMenu("File");
		JMenu m2 = new JMenu("Help");
		mb.add(m1);
		mb.add(m2);
		JMenuItem mi1 = new JMenuItem("Open");
		JMenuItem mi2 = new JMenuItem("Save as...");
		m1.add(mi1);
		m1.add(mi2);
		
		// Center Table
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Task");
		model.addColumn("Cost");
		JTable table = new JTable(model);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		
		// Bottom Panel
		JPanel bottomPanel = new JPanel();
		JLabel l1 = new JLabel("Task");
		JTextField tf1 = new JTextField(20);
		JLabel l2 = new JLabel("Cost");
		
		SpinnerModel sm = new SpinnerNumberModel(0, -50, 50, 5);
		JSpinner spinner1 = new JSpinner(sm);
		spinner1.setPreferredSize(new Dimension(50, 30));
		spinner1.setMinimumSize(new Dimension(50, 30));
		
		JButton addButton = new JButton("Add");

		bottomPanel.add(l1);
		bottomPanel.add(tf1);
		bottomPanel.add(l2);
		bottomPanel.add(spinner1);
		bottomPanel.add(addButton);
		
		// Right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		
		JLabel moneyCount = new JLabel("Points: " + money);
		JButton doneButton = new JButton("Done");
		JButton deleteButton = new JButton("Delete");
		rightPanel.add(moneyCount);
		rightPanel.add(doneButton);
		rightPanel.add(deleteButton);
		
		addButton.addActionListener(e -> {
			if (tf1.getText().isEmpty()) return;

			DefaultTableModel model1 = (DefaultTableModel) table.getModel();
			model1.addRow(new Object[] {tf1.getText(), spinner1.getValue()});
			table.changeSelection(table.getRowCount()-1, table.getColumnCount()-1, false, false);
			tf1.setText(null);
			spinner1.setValue(0);
		});
		
		doneButton.addActionListener(e -> {
			if (table.getSelectedRowCount() == 0) return;

			int offset = 0; // so that loop can get accurate rows while deleting elements

			for (int row : table.getSelectedRows()) {
				int cost = (int) table.getValueAt(row - offset, 1);

				if (money + cost < 0) continue;

				money += cost;
				model.removeRow(row - (offset++));
				moneyCount.setText("Points: " + money);
			}
		});
		
		deleteButton.addActionListener(e -> {
			if (table.getSelectedRowCount() == 0) return;

			int offset = 0; // so that loop can get accurate rows while deleting elements
			for (int row : table.getSelectedRows())
				model.removeRow(row - (offset++));
		});
		
		frame.getContentPane().add(BorderLayout.NORTH, mb);
		frame.getContentPane().add(BorderLayout.CENTER, new JScrollPane(table));
		frame.getContentPane().add(BorderLayout.EAST, rightPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ToDoList();
	}
}
