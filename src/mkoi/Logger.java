package mkoi;

import java.math.BigInteger;

public abstract class Logger 
{
	public static final int CONSOLE_MODE = 1;
	public static final int GUI_MODE = 2;
	
	protected BigInteger n;
	protected boolean consoleMode = false;
}
