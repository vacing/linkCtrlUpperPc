package cn.vacing.mw.gui;

import java.util.ArrayList;

public class LinkStat {

	public static final int AD1 			= 	0;
	public static final int AD2 			= 	1;
	public static final int DA1 			= 	2;
	public static final int DA2				= 	3;
	public static final int TIME_SYN_GATE 	= 	4;
	public static final int DIGIT_CANCE 	= 	5;
	public static final int FREQ_AVER_SEGS 	= 	6;
	public static final int FORGET_FACTOR 	= 	7;
//	public static final int AD1 = 8;
	public String name;
	public String stat;
	
	public LinkStat(String name, String stat)
	{
		this.name = name;
		this.stat =stat;
	}
	
	public static ArrayList<LinkStat> getStatInstance()
	{
		ArrayList<LinkStat> ls = new ArrayList<LinkStat>();
		
		ls.add(new LinkStat("AD1", "关闭"));
		ls.add(new LinkStat("AD2", "关闭"));
		ls.add(new LinkStat("DA1", "关闭"));
		ls.add(new LinkStat("DA2", "关闭"));
		ls.add(new LinkStat("时间捕获门限", "1000"));
		ls.add(new LinkStat("数字干扰抵消", "关闭"));
		ls.add(new LinkStat("频域平滑段数", "关闭"));
		ls.add(new LinkStat("遗忘因子", "0.5"));
		
		return ls;
	}

	public String getName() {
		return name;
	}

	public String getStat() {
		return stat;
	}

}

