package cn.vacing.mwGui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GuiProperties extends Properties {
	
	
	public void initGuiProp()
	{
		String filePath = "./conf/GuiProperties.txt";
		try {
			FileReader propFileReader = new FileReader(filePath);
			load(propFileReader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GuiProperties gp = (new GuiProperties());
		gp.initGuiProp();
		System.out.println(gp.getProperty("a"));
	}
	
	
	private static final long serialVersionUID = 1L;
}
