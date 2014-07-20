package info.antworld.run;

import java.util.ArrayList;
import java.util.HashMap;

public class Compiler {
	private Runner runner;
	
	private HashMap<String,Object> objects;
	private HashMap<String,String> numbers;
	private HashMap<String,String> strings;
	
	public Compiler(Runner runner)
	{
		this.runner = runner;
	}
	
	public void compileCommand(String command) throws Exception
	{
		ArrayList<String> cmd = arrayListFromString(command);
		String a0 = null; 
		String a1 = null;
		String a2 = null; 
		String a3 = null; 
		String a4 = null; 
		String a5 = null;
		
		if (cmd.size() > 0) a0 = cmd.get(0);
		if (cmd.size() > 1) a1 = cmd.get(1);
		if (cmd.size() > 2) a2 = cmd.get(2);
		if (cmd.size() > 3) a3 = cmd.get(3);
		if (cmd.size() > 4) a4 = cmd.get(4);
		if (cmd.size() > 5) a5 = cmd.get(5);
		
		if (a0.equals("def") && cmd.size() == 3)
		{
			if (isNumber(a1)) throw new Exception("variables cannot be numbers");
			if (isNumber(a2)) 
			{
				numbers.put(a1, a2);
			}
			if (!a2.contains("new"))
			{
				strings.put(a1, a2);
			}
		}
	}

	private ArrayList<String> arrayListFromString(String command)
	{
		command = command + " ";
		ArrayList<String> returnThis = new ArrayList<String>();
		int begin = 0;
		int end = 1;
		/*do 
		{
			command = command.substring(0,command.length()-1);
		} while (command.substring(command.length()-1).equals(""));*/
		
		do 
		{
			if (command.charAt(end) == ' ') 
			{
				returnThis.add(command.substring(begin, end));
				begin = end + 1;
				end++;
			}
			end++;
		} while (end < command.length());
		
		return returnThis;
	}
	
	private boolean isNumber(String string)
	{
		for (int i = 0; i < string.length(); i++)
		{
			char c = string.charAt(i);
			if (c != '0' || c != '1' || c != '2' || c != '3' || c != '4' || c != '5' || c != '6' || c != '7' || c != '8' || c != '9')
			{
				return false;
			}
		}
		return true;
	}
	
}
