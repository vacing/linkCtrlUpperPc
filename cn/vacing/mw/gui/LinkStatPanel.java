package cn.vacing.mw.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;



/**
 * 位于主界面左侧，显示链路状态界面
 * @author Gavin
 *
 */
public class LinkStatPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public void stateChange(int row, int column, String newState) {
		model.setValueAt(newState, row, column);
		operateRecord.append((recordNum++) + " - " + model.getValueAt(row, 0) + " -> " + newState + "\n");
	}

	public LinkStatPanel()
	{
		initJPanel();
	}
	
	/**
	 * 初始化表格
	 * @param nameStat
	 */
	@SuppressWarnings("unchecked")
	public void initTable(ArrayList<LinkStat> linkStat )
	{
		// 获取用户信息表格组件的数据模型对象
		Vector<Vector<String>> dataVector = model.getDataVector();
		for(LinkStat ls : linkStat )
		{
			Vector<String> row = new Vector<String>(); // 使用用户信息创建单行数据的向量
			row.add(ls.getName());
			row.add(ls.getStat());
			if (!dataVector.contains(row)) {
				model.getDataVector().add(row); // 把用户信息添加到表格组件中
			}
		}
	}
	
	private void initJPanel()
	{
		setLayout(new GridLayout(0, 1));
		
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 30));
		jScrollPane1.setPreferredSize(new java.awt.Dimension(201, 400));
		Border borderTemp = BorderFactory.createEtchedBorder();
		Border titledBorder = BorderFactory.createTitledBorder(borderTemp, "链路状态");
		jScrollPane1.setBorder(titledBorder);
		
		Font charFont = new Font("华文中宋", Font.PLAIN, 14);
		linkStatTable = new javax.swing.JTable();
		linkStatTable.setFont(charFont);
		
		//创建默认表格模板
		DefaultTableModel dtm = new javax.swing.table.DefaultTableModel(new Object[][] {}, 
				new String[] { "名称", "状态" })
		{
			private static final long serialVersionUID = 1L;	//序列化ID，可忽略
			//设置表格的列是否可以编辑，设为true表示第三列可以被编辑
			boolean[] canEdit = new boolean[] {false, false};
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};
		linkStatTable.setModel(dtm);
		linkStatTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
		jScrollPane1.setViewportView(linkStatTable);
		
		add(jScrollPane1);		
		model = (DefaultTableModel) linkStatTable.getModel();
		

		JScrollPane textPanel = new JScrollPane();
		borderTemp = BorderFactory.createEtchedBorder();
		titledBorder = BorderFactory.createTitledBorder(borderTemp, "操作记录");
		textPanel.setBorder(titledBorder);
		operateRecord = new JTextArea();
		operateRecord.setEditable(false);
		operateRecord.setRows(10);
		operateRecord.setLineWrap(true);
		textPanel.setViewportView(operateRecord);

		add((textPanel));
	}
	
	private int recordNum = 1;
	private JTextArea operateRecord;
	
	private DefaultTableModel model;
	private JScrollPane jScrollPane1;
	protected JTable linkStatTable;
}
