package cn.vacing.mw.exception;

/**
 * 输入数据长度不匹配异常，指示输入数据的长度不符合要求
 *  
 * @author Gavin
 * 
 */
public class DataLenUnproperException extends Exception {
	private static final long serialVersionUID = 1L;
	public DataLenUnproperException() {}
	public DataLenUnproperException(String msg) {
		super(msg);
	} 
}