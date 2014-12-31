package cn.vacing.mw;

public class FinalVar {
	
	//login panel
	//inner button command 
	public static final String LOG_IN_CONFIRM = "loginConfrim";
	
	public static final int PORT_1 = 6001;
	public static final int PORT_2 = 6003;
	
	
	//innner button command
	//link control panel
	public static final String 	AD1_OPEN 				= 	"ad1Open";				//AD1 开
	public static final String 	AD1_CLOSE 				=	"ad1Close";				//AD1 关
	public static final String 	AD2_OPEN 				= 	"ad2Open";				//AD2 开
	public static final String 	AD2_CLOSE 				=	"ad2Close";				//AD2 关
	public static final String 	DA1_OPEN 				= 	"da1Open";				//DA1 开
	public static final String 	DA1_CLOSE 				=	"da1Close";				//DA1 关
	public static final String 	DA2_OPEN 				= 	"da2Open";				//DA2 开
	public static final String 	DA2_CLOSE 				=	"da2Close";				//DA2 关
	public static final String	TIME_SYN_CONFIG			=	"timeSynConfig";		//时间捕获门限配置
	public static final String	OSCI_STEP_CONFIG		=	"osciStepConfig";		//晶振步进控制
	public static final String 	RF_BOARD_CTR			=	"rfBoardCtr";			//调用射频干扰抵消控制程序
	//rf cancellation panel
//	public static final String	
	//base cancellation panel
	public static final String DIGIT_CANCE_START		=	"digitCanceStart";		//数字干扰抵消打开
	public static final String DIGIT_CANCE_STOP			=	"digitCanceStop";		//数字干扰抵消关闭
	public static final String FREQ_AVER_SEGS_CONFIG	=	"freqAverSegsConf";		//频域平滑段数配置
	public static final String FORGET_FACTOR_CONFIG		=	"forgetFactorConf";		//遗忘因子配置
	//performance
	public static final String	SHOW_CANCE_PERFORM 		=	"showCancePerform";		//显示数字干扰抵消性能窗口
	public static final String 	SHOW_CONSTELLATION 		= 	"showConstellation";	//显示链路星座图窗口
	
	//spectrum and constellation panel
	public static final String	CANCE_PERFORM_START		=	"cancePerformStart";	//开始显示抵消性能频谱
	public static final String	CANCE_PERFORM_STOP		=	"cancePerformStop";		//停止显示抵消性能频谱
	public static final String 	CONSTELLATION_START		=	"constellationStart";	//开始显示星座图
	public static final String 	CONSTELLATION_STOP		=	"constellationStop";	//停止显示星座图

	//send command
	//main frame
	public static final int 	AD1_OPEN_COMMAND 				= 	0X11113456;		//开 AD
	public static final int 	AD1_CLOSE_COMMAND 				= 	0x11115678;		//关 AD
	public static final int 	AD2_OPEN_COMMAND 				= 	0X11123456;		//开 AD
	public static final int 	AD2_CLOSE_COMMAND 				= 	0x11125678;		//关 AD
	public static final int 	DA1_OPEN_COMMAND 				= 	0X11113456;		//开 AD
	public static final int 	DA1_CLOSE_COMMAND 				= 	0x11115678;		//关 AD
	public static final int 	DA2_OPEN_COMMAND 				= 	0X11123456;		//开 AD
	public static final int 	DA2_CLOSE_COMMAND 				= 	0x11125678;		//关 AD
	
	//performance
	public static final int		CANCE_PERFORM_START_COMMAND		=	0x000000c7;		//数字干扰抵消性能显示
	public static final int 	CONSTELLATION_START_COMMAND 	=	0x11111113;		//星座图显示

	
	
	
}
