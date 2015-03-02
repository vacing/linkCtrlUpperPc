package cn.vacing.mw._main;

public enum ButtonCommand {
	
	//log in
	LOG_IN_CONFIRM			("loginConfrim",		0),					//登陆按钮
	
	//link control
	AD1_OPEN				("AD1_OPEN",			0x10000000),
	AD1_CLOSE				("AD1_CLOSE",			0x10000000),
	AD2_OPEN				("AD2_OPEN",			0x10000000),
	AD2_CLOSE				("AD2_CLOSE",			0x10000000),			
	DA_OPEN					("DA_OPEN",				0x11000000),			//DA开，最低位有效
	DA_CLOSE				("DA_CLOSE",			0x11000001),			//DA关，最低位有效
	TIME_SYN_CONFIG			("TIME_SYN_CONFIG",		0x10000000),			//时间捕获门限配置
	OSCI_STEP_CONFIG		("OSCI_STEP_CONFIG",	0x10000000),			//晶振步进控制
	RF_BOARD_CTR			("RF_BOARD_CTR",		0),						//调用射频干扰抵消控制程序
	LINK_RESET_ON			("LINK_RESET_ON",		0x13000001),			//开始复位链路，最低位有效
	LINK_RESET_OFF			("LINK_RESET_OFF",		0x13000000),			//取消复位链路，最低位有效
	EMIT_CENT_FREQ_CONF		("EMIT_CENT_FREQ_CONF",	0x12000000),			//发射中频配置， 低16位有效
	SMOOTH_SEGS_CONF		("SMOOTH_SEGS_CONF",	0x10000000),			//平滑段数，低6位有效
	FPGA0_ANY				("FPGA1_ANY",			0),						//任意32bit
	FPGA1_ANY				("FPGA1_ANY",			0),						//任意32bit
	
	//digital cancellation
	DI_CANCE_RESET_START	("diCanceResetStart", 	0X22000000),		//1b,	数字干扰抵消复位打开
	DI_CANCE_RESET_STOP		("diCanceResetStop",	0X22000001),		//1b, 	数字干扰抵消复位关闭
	DIR_CURR_CORRECT		("dirCurrCorr",			0X21000001),		//1b,	直流偏置校准
	DI_PARAM_UP_START		("diParamUpdateStart",	0X23000001),		//1b, 	RLS系数更新开
	DI_PARAM_UP_STOP		("diParamUpdatetStop",	0X23000000),		//1b, 	RLS系数更新关
	DI_RECE_TIME_DELAY		("diReceTimeDelay",		0X24000000),		//6b, 	接收之路延时配置
	DI_FEEDBACK_TIME_DELAY	("diFeedBackTimeDelay",	0X25000000),		//6b, 	反馈之路延时配置
	DI_CATCH_LENGTH			("diCatchLength",		0X26000000),		//15b, 	数据缓存长度配置
	
	//show performance gui
	SHOW_CANCE_PERFORM		("showCancePerform",	0),					//显示数字干扰抵消性能窗口
	SHOW_CONSTELLATION		("showConstellation",	0),					//显示链路星座图窗口
	
	//performance
	CANCE_PERFORM_START		("cancePerformStart",	0x00000001),		//数字干扰抵消性能显示开始，抵消前为0x01，抵消后0x02。
	CANCE_PERFORM_STOP		("cancePerformStop",	0),					//数字干扰抵消性能显示停止
	CONSTELLATION_START		("constellationStart",	0x00000003),		//星座图显示开始
	CONSTELLATION_STOP		("constellationStop",	0),					//星座图显示停止							
	
	;	//如果要定义自己的方法，必须在最后一个枚举常量后面添加一个分号
	/**
	 * 未用
	 */
	public final String unUsed;		//内部名称
	/**
	 * 按键发送到fpga的命令数值，全0表示该按钮不发送命令到fpga。
	 */
	public final int	s2fpga;			//发送到fpga的命令字串
	private ButtonCommand(String innerName, int s2fpga) {
		this.unUsed = innerName;
		this.s2fpga = s2fpga;
	}
}
