import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleShell {
	
	public static void main(String[] args) throws java.io.IOException{
		
		String commandLine;
		BufferedReader console = new BufferedReader
				(new InputStreamReader(System.in));
		
		ProcessBuilder pb = new ProcessBuilder();
		List<String> history = new ArrayList<String>();
		int index = 0;
		//we break out with <ctrl c>	
		while(true){
			//read what the user enters
			System.out.print("jsh>");
			commandLine = console.readLine();
			
			//input parsed into array of strings(commands) 
			String[] commands = commandLine.split(" ");
			List<String> list = new ArrayList<String>();
			
			
			//loop through to see if parsing worked
			for(int i = 0;i<commands.length;i++){
				//System.out.println(commands[i]); ***check to see if parsing/split worked***
				list.add(commands[i]);
				
			}
			//System.out.print(list); ***check to see if list was added correctly***
			history.addAll(list);
			try{
				//display history of shell with index
				if(list.get(list.size()-1).equals("history")){
					for(String s : history)
					System.out.println((index++) + " " + s);
					continue;
				}
				
			//change directory 
			
			if(list.contains("cd")){
				if(list.get(list.size()-1).equals("cd")){
					File home = new File(System.getProperty("user.home"));
					//test to see what user.home changes to
					//System.out.println("The home directory is " + home);
					pb.directory(home);
					continue;
				
				}else{
					String dir = list.get(1);
					//test to see what directory was passed
					//System.out.println("The directory passed is " + dir);		
					File newPath = new File(dir); 
					boolean exists = newPath.exists();
					
					if(exists){
					System.out.println("/" + dir); //added the "/" for cleaner output
					pb.directory(newPath);
					continue;
					}
				else{   		//if directory doesn't exist
						System.out.print("Path ");
					}
				}
			}
				//Testing to see if home directory works 
//			File home = new File(System.getProperty("user.home"));
//			pb.directory(home);
			
			
			
			//!! command returns the last command in history
			if(list.get(list.size()-1).equals("!!")){
				pb.command(history.get(history.size()-2));
				
			}//!<integer value i> command
			else if			
			(list.get(list.size()-1).charAt(0) == '!'){
				int b = Character.getNumericValue(list.get(list.size()-1).charAt(1));
				if(b<=history.size())//check if integer entered isn't bigger than history size
				pb.command(history.get(b));
			}
			else{
			pb.command(list);
			}
			
			Process process = pb.start();
			
			//obtain the input stream
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			//read output of the process
			String line;
			while((line = br.readLine()) != null)
				System.out.println(line);
			br.close();
			
			
			
			//if the user entered a return, just loop again
			if(commandLine.equals(" "))
				continue;
			}
			
			//catch ioexception, output appropriate message, resume waiting for input
			catch (IOException e){
				System.out.println("Input Error, Please try again!");
			}
			
			/** The steps are:
			 * 1. parse the input to obtain the command and any parameters
			 * 2. create a ProcessBuilder object
			 * 3. start the process
			 * 4. obtain the output stream
			 * 5. output the contents returned by the command
			 */
		
		}
		
		

	}

}
