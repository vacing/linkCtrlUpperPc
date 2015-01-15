package cn.vacing.mw.exception;

/**
 * 从文本框中输入的数据范围越界
 * @author Gavin
 *
 */
public class NumInputOutOfBounds extends Exception {
	private static final long serialVersionUID = 1L;
	public NumInputOutOfBounds() {}
	public NumInputOutOfBounds(String msg) {
		super(msg);
	} 
}
