package cn.vacing.mw.perfomace_gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.text.NumberFormat;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import cn.vacing.mw.FinalVar;

public class SpectrumDisplay  extends javax.swing.JDialog {
	
//    private MainFrame mfGui;							//父窗体
    private JFreeChart spectrumChart;               	//频谱显示图表
    private XYPlot spectrumPlot;                    	//频谱Plot
//    private NumberFormat numberFormat = NumberFormat.getInstance();     //数字格式工具
    private double avgPowerBefore;            	//保存抵消前平均功率，用于计算抵消效果（相对精确）
    private double avgPowerAfeter;
    private ActionListener performEvents;
    private WindowListener wl;
    
    private static SpectrumDisplay singlePerformDialog;		//single Instance
    private static Object classLock = new Object();					//class synchronize lock
    private Object objLock = new Object();								//object lock
    
    //legends
    public static final String BEFORE = "BeforeCancellation";
    public static final String AFTER = "AfterCancellation";
    public static final String NOISE = "Noise";
   
    /**
     * Creates new form PerformanceDialog
     */
    public static SpectrumDisplay getInstance(ActionListener performEvents, WindowListener wl)
    {
    	synchronized(classLock)
    	{
    		if(singlePerformDialog == null) {
    			singlePerformDialog = (new SpectrumDisplay(performEvents, wl));
    		}
    	}
    	return singlePerformDialog;
    }
    
    //private constructors
    private SpectrumDisplay(ActionListener performEvents, WindowListener wl) {
    	this.performEvents = performEvents;
    	this.wl = wl;
        panelInit();  
    }
    
	/**
	 * Initialize the contents of the dialog.
	 */
	private void panelInit() {		        
		spectrumPanelInit();			//频谱面板初始化
		
        jTabsPane = new javax.swing.JTabbedPane();
        jTabsPane.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N	//设置标签显示字体
        jTabsPane.addTab("Spectrum Display", jpnlSpectrumDisplay);
 
        //数字格式化工具
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);	//最多两位小数
        //dialog初始化
        addWindowListener(wl);
		setTitle("数字干扰抵消性能分析");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);     
        getContentPane().add(jTabsPane, java.awt.BorderLayout.CENTER);
        pack();
        
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		setBounds((screenSize.width - 1200) / 2, (screenSize.height - 700) / 2,
				1000, 600);
	}  
    
	/**
	 * 更新频谱显示曲线
	 * @param
	 * legend-曲线名称
	 * powerDateset-曲线数据集，为二维数组，第一个一维数组表示横坐标，第二个一维数组表示纵坐标。
	 */
    public void drawSpectrum(String legend, double[][] powerDataset) {
    	synchronized(objLock) {
	    	double[][] powerCopy = powerDataset.clone();//数据拷贝到本地显示
	    	XYSeries xys = new XYSeries(legend);
	    	for(int i = 0; i< powerCopy[0].length; i++){
	    		xys.add(powerCopy[0][i], powerCopy[1][i]);
	    	}
	    	XYSeriesCollection xysc = new XYSeriesCollection(xys);
	    	
	        if (BEFORE.equals(legend)) {
	            spectrumPlot.setDataset(0, xysc);
	        } else if (NOISE.equals(legend)) {
	        	spectrumPlot.setDataset(2, xysc);
			} else if (AFTER.equals(legend)){
	            spectrumPlot.setDataset(1, xysc);
	        } else {
	        	System.out.println("Series legend is error");
	        }
    	}
    }

    /**
     * 显示抵消前平均功率
     */
    public void showAvgPowerBefore(double avgPower) {
        jtfPowerBefore.setText(numberFormat.format(avgPower));
        avgPowerBefore = avgPower;   
    }

    /**
     * 显示抵消后平均功率
     */
    public void showAvgPowerAfter(double power) {
        jtfPowerAfter.setText(numberFormat.format(power));
        avgPowerAfeter = power;
        showCancelledPower();
    }
    
    /**
     * 显示噪声平均功率
     */
    public void showAvgPowerNoise(double power) {        
//    	avgPower = avgPower + Math.random() - 0.5;
        jtfPowerNoise.setText(numberFormat.format(power));       
    }
    
    /**
     * 显示抵消了多少功率
     */
    private void showCancelledPower()
    {
    	jtfCancellation.setText(numberFormat.format(avgPowerBefore - avgPowerAfeter));
    }    
    /**
     * 描绘频谱图表，参数初始化
     * @return
     */
    private JFreeChart createSpectrumChart() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Spectrum Graph",			//title
                "Frequency (KHz)",			//xlabel
                "Power",					//ylabel
                null,						//data
                PlotOrientation.VERTICAL,	//
                true,						//显示图例
                true,						//使用生成工具
                false);						//不用生成URL地址
        chart.setBorderPaint(Color.RED);
        chart.setBackgroundPaint(Color.WHITE);

        //图表网格背景设置
        spectrumPlot = chart.getXYPlot();
        spectrumPlot.setBackgroundPaint(Color.BLACK);
        spectrumPlot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        spectrumPlot.setDomainGridlinePaint(Color.WHITE);			//竖线颜色
        spectrumPlot.setRangeGridlinePaint(Color.WHITE);			//横线颜色
        
        //曲线标示设置
        LegendTitle legend = chart.getLegend();
        legend.setBackgroundPaint(Color.BLACK);
        legend.setItemFont(new Font("Times New Roman", Font.PLAIN, 15));
        legend.setItemPaint(Color.WHITE);

        //图表坐标轴范围设置
        NumberAxis rangeAxis = (NumberAxis) spectrumPlot.getRangeAxis();	//纵轴
        rangeAxis.setRange(-50.0, 10.0);
        rangeAxis.setAutoRange(false);	//纵坐标不自动变化
        NumberAxis domainAxis = (NumberAxis) spectrumPlot.getDomainAxis();	//横轴
        domainAxis.setRange(0.0, 1024.0);
        
        //分别设置渲染效果
        XYLineAndShapeRenderer rendererZero = new XYLineAndShapeRenderer();
        XYLineAndShapeRenderer rendererFir = new XYLineAndShapeRenderer();
        XYLineAndShapeRenderer rendererSec = new XYLineAndShapeRenderer();
        spectrumPlot.setRenderer(0, rendererZero);
        spectrumPlot.setRenderer(1, rendererFir);
        spectrumPlot.setRenderer(2, rendererSec);
        rendererZero.setSeriesPaint(0, Color.RED);		//设置曲线颜色
        rendererFir.setSeriesPaint(0, Color.GREEN);
        rendererSec.setSeriesPaint(0, Color.YELLOW);
        rendererZero.setBaseShapesVisible(false);		//设置在数据点位置显示形状
        rendererFir.setBaseShapesVisible(false);
        rendererSec.setBaseShapesVisible(false);
 
        return chart;
    }
    

	/**
	 * 频谱显示面板初始化
	 */
	private void spectrumPanelInit()
	{
		java.awt.GridBagConstraints gridBagConstraints;

        jpnlSpectrumDisplay = new javax.swing.JPanel();
        jpnlSpectrumNorth = new javax.swing.JPanel();
        jpnlSpectrumSouth = new javax.swing.JPanel();
        jpnlSpectrumSouth = new javax.swing.JPanel();
        jtfCancellation = new javax.swing.JTextField();
        jtfPowerAfter = new javax.swing.JTextField();
        jtfPowerBefore = new javax.swing.JTextField();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        JPanel jPanel1 = new javax.swing.JPanel();
        JPanel jPanel4 = new javax.swing.JPanel();
        jlblCheat = new javax.swing.JLabel();
        jpnlSpectrumWest = new javax.swing.JPanel();
        jpnlSpectrumEast = new javax.swing.JPanel();
        showSpectrumStart = new javax.swing.JButton();
        showSpectrumStop = new javax.swing.JButton();

        jpnlSpectrumDisplay.setLayout(new java.awt.BorderLayout(10, 10));			//设置频谱总体面板布局

        //上侧面板-这个面板什么也没有，应该是为了占空而写
        jpnlSpectrumNorth.setPreferredSize(new java.awt.Dimension(600, 20));
        GroupLayout jpnlSpectrumNorthLayout = new GroupLayout(jpnlSpectrumNorth);
        jpnlSpectrumNorth.setLayout(jpnlSpectrumNorthLayout);
        jpnlSpectrumNorthLayout.setHorizontalGroup(
            jpnlSpectrumNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
        );
        jpnlSpectrumNorthLayout.setVerticalGroup(
            jpnlSpectrumNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jpnlSpectrumDisplay.add(jpnlSpectrumNorth, java.awt.BorderLayout.NORTH);

        //下侧面板-参数显示
        jpnlSpectrumSouth.setPreferredSize(new java.awt.Dimension(795, 60));
        jpnlSpectrumSouth.setLayout(new java.awt.GridBagLayout());
        jpnlSpectrumDisplay.add(jpnlSpectrumSouth, java.awt.BorderLayout.SOUTH);
      
        //nothing，but占空
        jPanel1.setPreferredSize(new java.awt.Dimension(50, 25));
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jpnlSpectrumSouth.add(jPanel1, gridBagConstraints);

        //抵消前功率的数字显示
        jtfPowerBefore.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtfPowerBefore.setPreferredSize(new java.awt.Dimension(80, 25));
        jtfPowerBefore.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 10, 5, 5);
        jpnlSpectrumSouth.add(jtfPowerBefore, gridBagConstraints);
        //抵消前功率的单位显示
        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("dBm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 5, 5, 5);
        jpnlSpectrumSouth.add(jLabel2, gridBagConstraints);
        //抵消前功率的标签显示
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Before Cancellation:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpnlSpectrumSouth.add(jLabel1, gridBagConstraints);

        //抵消后功率的数字显示
        jtfPowerAfter.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtfPowerAfter.setPreferredSize(new java.awt.Dimension(80, 25));
        jtfPowerAfter.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 10, 5, 5);
        jpnlSpectrumSouth.add(jtfPowerAfter, gridBagConstraints);
        //抵消后功率的标签显示
        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("After Cancellation:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jpnlSpectrumSouth.add(jLabel3, gridBagConstraints);
        //抵消后功率的单位显示
        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("dBm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 5, 5, 5);
        jpnlSpectrumSouth.add(jLabel4, gridBagConstraints);

        //nothing，but占空....
        jPanel4.setPreferredSize(new java.awt.Dimension(50, 25));
        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        jpnlSpectrumSouth.add(jPanel4, gridBagConstraints);
        
        //抵消功率的标签显示
        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Cancellation:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        jpnlSpectrumSouth.add(jLabel5, gridBagConstraints);
        //抵消功率的数字显示
        jtfCancellation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtfCancellation.setPreferredSize(new java.awt.Dimension(80, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 10, 5, 5);
        jtfCancellation.setEditable(false);
        jpnlSpectrumSouth.add(jtfCancellation, gridBagConstraints);
        //抵消功率的单位显示
        jlblCheat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlblCheat.setText("dB");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 5, 5, 0);
        jpnlSpectrumSouth.add(jlblCheat, gridBagConstraints);
        
        //噪声标签显示
        JLabel lblNoise = new JLabel();
        lblNoise.setText("Noise:");
        lblNoise.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        GridBagConstraints gbc_lblNoise = new GridBagConstraints();
        gbc_lblNoise.anchor = GridBagConstraints.EAST;
        gbc_lblNoise.insets = new Insets(0, 0, 0, 5);
        gbc_lblNoise.gridx = 0;
        gbc_lblNoise.gridy = 1;
        jpnlSpectrumSouth.add(lblNoise, gbc_lblNoise);
        //噪声数字显示
        jtfPowerNoise = new JTextField();
        jtfPowerNoise.setPreferredSize(new Dimension(80, 25));
        jtfPowerNoise.setFont(new Font("Arial", Font.PLAIN, 14));
        GridBagConstraints gbc_jtfPowerNoise = new GridBagConstraints();
        gbc_jtfPowerNoise.insets = new Insets(0, 10, 0, 5);
        gbc_jtfPowerNoise.gridx = 1;
        gbc_jtfPowerNoise.gridy = 1;
        jtfPowerNoise.setEditable(false);
        jpnlSpectrumSouth.add(jtfPowerNoise, gbc_jtfPowerNoise);
        //噪声功率单位显示
        JLabel label_1 = new JLabel();
        label_1.setText("dBm");
        label_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.insets = new Insets(0, 5, 5, 5);
        gbc_label_1.gridx = 2;
        gbc_label_1.gridy = 1;
        jpnlSpectrumSouth.add(label_1, gbc_label_1);      

        
        //左侧面板-nothing，but占空
        jpnlSpectrumWest.setPreferredSize(new java.awt.Dimension(50, 371));
        GroupLayout jpnlSpectrumWestLayout = new GroupLayout(jpnlSpectrumWest);
        jpnlSpectrumWest.setLayout(jpnlSpectrumWestLayout);
        jpnlSpectrumWestLayout.setHorizontalGroup(
            jpnlSpectrumWestLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jpnlSpectrumWestLayout.setVerticalGroup(
            jpnlSpectrumWestLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        jpnlSpectrumDisplay.add(jpnlSpectrumWest, java.awt.BorderLayout.WEST);

        //右侧面板-按钮
        jpnlSpectrumEast.setPreferredSize(new java.awt.Dimension(130, 371));
        jpnlSpectrumDisplay.add(jpnlSpectrumEast, java.awt.BorderLayout.EAST);
        showSpectrumStart.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N	//打开频谱显示按钮
        showSpectrumStart.setText("Start");
        showSpectrumStart.addActionListener(performEvents);
        showSpectrumStart.setActionCommand(FinalVar.CANCE_PERFORM_START);
        showSpectrumStart.setPreferredSize(new java.awt.Dimension(125, 40));
        showSpectrumStop.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N	//关闭频谱显示按钮
        showSpectrumStop.setText("Stop");
        showSpectrumStop.addActionListener(performEvents);
        showSpectrumStop.setActionCommand(FinalVar.CANCE_PERFORM_STOP);
        showSpectrumStop.setPreferredSize(new java.awt.Dimension(125, 40));
        javax.swing.GroupLayout jpnlSpectrumEastLayout = new javax.swing.GroupLayout(jpnlSpectrumEast);
        jpnlSpectrumEast.setLayout(jpnlSpectrumEastLayout);
        jpnlSpectrumEastLayout.setHorizontalGroup(
            jpnlSpectrumEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlSpectrumEastLayout.createSequentialGroup()
                .addGroup(jpnlSpectrumEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showSpectrumStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showSpectrumStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE))
        );
        jpnlSpectrumEastLayout.setVerticalGroup(
            jpnlSpectrumEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlSpectrumEastLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(showSpectrumStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addComponent(showSpectrumStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );
            
        spectrumChart = createSpectrumChart();									//显示频谱图表
        jpnlSpectrumDisplay.add(new ChartPanel(spectrumChart), "Center");       //界面添加频谱图表
	}
	
	
	//GUI Variables declaration
    private javax.swing.JTabbedPane jTabsPane;	
    //频谱面板主要控件
    private javax.swing.JButton showSpectrumStop;			//频谱显示关
    private javax.swing.JButton showSpectrumStart;			//频谱显示开
    private javax.swing.JLabel jlblCheat;					//抵消功率单位标签
    private javax.swing.JPanel jpnlSpectrumDisplay;			//频谱显示面板
    private javax.swing.JPanel jpnlSpectrumEast;
    private javax.swing.JPanel jpnlSpectrumNorth;
    private javax.swing.JPanel jpnlSpectrumSouth;
    private javax.swing.JPanel jpnlSpectrumWest;
    private javax.swing.JTextField jtfCancellation;			//抵消的功率
    private javax.swing.JTextField jtfPowerAfter;			//抵消后功率
    private javax.swing.JTextField jtfPowerBefore;			//抵消前功率
    private JTextField jtfPowerNoise;						//噪声功率

    private NumberFormat numberFormat; // 数字格式工具
    // End of variables declaration
    private static final long serialVersionUID = -5955667932192512069L;
}
