package ie.tudublin;

import c19124715.C19124715Visual;

//import c123456.*;
public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new C19124715Visual());
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();			
	}
}