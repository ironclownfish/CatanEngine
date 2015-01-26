import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class CatanDriver {

	public static void main(String[] args) {
		CatanDriver cd = new CatanDriver();
		cd.setup();
	}
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public void setup() {
		//Set up ports
		ask("Are the ports connected in suggested order? (y/n)");
		
		//Set up tiles.
		ask("What resource is next to the rock port?");
	}
	
	private String ask(String out) {
		System.out.println(out);
		String ret = null;
		try {
			ret = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
