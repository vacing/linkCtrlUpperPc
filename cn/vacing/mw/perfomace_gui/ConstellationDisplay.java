package cn.vacing.mw.perfomace_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import cn.vacing.mw.FinalVar;
import cn.vacing.mw.tools.Complex;

public class ConstellationDisplay  extends javax.swing.JDialog {
	
//    private MainFrame mfGui;							//父窗体
    private JFreeChart constellationChart;              //星座图显示图表
    private XYPlot constellationPlot;                   //星座图Plot
//    private NumberFormat numberFormat = NumberFormat.getInstance();     //数字格式工具
    private ActionListener performEvents;
    private WindowListener wl;
    
    private static ConstellationDisplay singlePerformDialog;		//single Instance
    private static Object classLock = new Object();					//class synchronize lock
    private Object objLock = new Object();								//object lock
    
    //legends
    public static final String BEFORE = "BeforeCancellation";
    public static final String AFTER = "AfterCancellation";
   
    /**
     * Creates new form PerformanceDialog
     */
    public static ConstellationDisplay getInstance(ActionListener performEvents, WindowListener wl)
    {
    	synchronized(classLock)
    	{
    		if(singlePerformDialog == null) {
    			singlePerformDialog = (new ConstellationDisplay(performEvents, wl));
    		}
    	}
    	return singlePerformDialog;
    }
    
    //for test only, needn'g assign many arguments
    private ConstellationDisplay()
    {
    	panelInit(); 
    }
    
    //private constructors
    private ConstellationDisplay(ActionListener performEvents, WindowListener wl) {
    	this.performEvents = performEvents;
    	this.wl = wl;
        panelInit();    
    }
    
	/**
	 * Initialize the contents of the dialog.
	 */
	private void panelInit() {		        
		constellationPaneInit();		//星座图面板初始化
		
        jTabsPane = new javax.swing.JTabbedPane();
        jTabsPane.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N	//设置标签显示字体
        jTabsPane.addTab("Constellation Map", jpnlConstellationMap);
 
        //dialog初始化
        addWindowListener(wl);
		setTitle("星座图");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);     
        getContentPane().add(jTabsPane, java.awt.BorderLayout.CENTER);
        pack();
        
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		setBounds((screenSize.width - 1200) / 2, (screenSize.height - 700) / 2,
				1000, 600);
	}  

	/**
	 * 更新星座图样点。
	 * 二维数组中，第一个一维数组表示横坐标集合，第二个一维数组表示每个横坐标对应的纵坐标。
	 */
    public void drawConstellation(String legend, double[][] constellationDataset) {
    	synchronized(objLock) {									//Synchronized method
    		double[][] constCopy = constellationDataset.clone();//数据拷贝到本地显示
        	XYSeries xys = new XYSeries(legend);
        	for(int i = 0; i< constCopy[0].length; i++){
        		xys.add(constCopy[0][i], constCopy[1][i]);
        	}
        	XYSeriesCollection xysc = new XYSeriesCollection(xys);
        	
            if (BEFORE.equals(legend)) {
            	constellationPlot.setDataset(0, xysc);
            } else if (AFTER.equals(legend)) {
            	constellationPlot.setDataset(1, xysc);
            } else {
            	System.out.println("Series legend is error");
            }
    	}
    }
    
    /**
     * 描绘星座图表
     */
    private JFreeChart createConstellationChart() {
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Constellation Graph",
                "I Data",
                "Q Data",
                null,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        chart.setBorderPaint(Color.black);
        chart.setBackgroundPaint(Color.white);

        constellationPlot = chart.getXYPlot();
        constellationPlot.setBackgroundPaint(Color.black);
        constellationPlot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        constellationPlot.setDomainGridlinePaint(Color.white);
        constellationPlot.setRangeGridlinePaint(Color.white);

        LegendTitle legend = chart.getLegend();
        legend.setBackgroundPaint(Color.black);
        legend.setItemFont(new Font("Times New Roman", Font.PLAIN, 16));
        legend.setItemPaint(Color.white);

        NumberAxis rangeAxis = (NumberAxis) constellationPlot.getRangeAxis();
        rangeAxis.setRange(-1, +1);
        NumberAxis domainAxis = (NumberAxis) constellationPlot.getDomainAxis();
        domainAxis.setRange(-1.0, 1.0);
        
        //分别设置渲染效果
        XYDotRenderer rendererZero = new XYDotRenderer();
        constellationPlot.setRenderer(0, rendererZero);
        rendererZero.setSeriesPaint(0, Color.RED);
        rendererZero.setDotWidth(3);
        rendererZero.setDotHeight(3);
        
        XYDotRenderer rendererFir = new XYDotRenderer();
        constellationPlot.setRenderer(1, rendererFir);
        rendererFir.setSeriesPaint(0, Color.YELLOW);
        rendererFir.setDotWidth(3);
        rendererFir.setDotHeight(3);       
        
        return chart;
    }
	
	/**
	 * 星座图显示面板初始化
	 */
	private void constellationPaneInit()
	{
        /**
         * 星座图标签面板设置
         */
		jpnlConstellationMap = new JPanel();
        jpnlConstellationMap.setLayout(new BorderLayout(10, 10));					//设置星座图表承载面板布局
        //中心-星座图表面板
        constellationChart = createConstellationChart();						//星座图图表
        ChartPanel chartPanel = new ChartPanel(constellationChart);
//        jpnlConstellationMap.add(chartPanel);
        jpnlConstellationMap.add(chartPanel, "Center");
        
        //右侧-按键面板
        constellationCtrlPanel = new JPanel();
        constellationCtrlPanel.setPreferredSize(new Dimension(130, 371));
        jpnlConstellationMap.add(constellationCtrlPanel, BorderLayout.EAST); 
        showConstellationStart = new JButton("Start");								//Start按钮
        showConstellationStart.setPreferredSize(new Dimension(125, 40));
        showConstellationStart.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        showConstellationStart.addActionListener(performEvents);
        showConstellationStart.setActionCommand(FinalVar.CONSTELLATION_START);
        showConstellationStop = new JButton("Stop");														//Stop按钮
        showConstellationStop.setPreferredSize(new Dimension(125, 40));
        showConstellationStop.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        showConstellationStop.addActionListener(performEvents);
        showConstellationStop.setActionCommand(FinalVar.CONSTELLATION_STOP);
        
        GroupLayout gl_panel = new GroupLayout(constellationCtrlPanel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 130, Short.MAX_VALUE)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(showConstellationStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(showConstellationStop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(0, 5, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 420, Short.MAX_VALUE)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(47)
        			.addComponent(showConstellationStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
        			.addComponent(showConstellationStop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(114))
        );
        constellationCtrlPanel.setLayout(gl_panel);
//        numberFormat.setMaximumFractionDigits(2);		//格式化小数位数
	}
	
	//GUI Variables declaration
    private javax.swing.JTabbedPane jTabsPane;

    //星座图面板主要控件
    private javax.swing.JPanel jpnlConstellationMap;
    private JPanel constellationCtrlPanel;
    private JButton showConstellationStart;					//星座图开
    private JButton showConstellationStop;					//星座图关

    // End of variables declaration
    private static final long serialVersionUID = -5955667932192512066L;
    
    public static void main(String[] args) {
    	ConstellationDisplay c = new ConstellationDisplay();
    	c.setVisible(true);
    	
    	double[][] dataB = new double[][]{
    			{0.5, 0.5, 0.5, 0.5,0.5, 0.5},	//x axis
    			{0.1, 0.2, 0.3, 0.4,0.5, 0.6},	//y axis
    	};
    	
    	c.drawConstellation(c.BEFORE, dataB);
    	
    	double[][] dataA = new double[][]{
    			{0.1, 0.2, 0.3, 0.4,0.5, 0.6},
    			{0.5, 0.5, 0.5, 0.5,0.5, 0.5},
    	};
    	
    	c.drawConstellation(c.AFTER, dataA);

    }
}
