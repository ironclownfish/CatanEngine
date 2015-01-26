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
		
		Board board = new Board();
		
		//Set up ports
		ask("Are the ports connected in suggested order? (y/n)");
		
		//Set up tiles.
		ask("What resource is next to the rock port?");
		
		//Set up players.
		ask("How many players are there?");
		ask("How many turns before my first turn?");
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
	
	public Settlement buildSettlement() {
		return new CSettlement();
	}
	
	public City upgradeToCity(Settlement s) {
		return new CCity();
	}
	
	public DevelopmentCard buildDevelopmentCard() {
		return new CDevelopmentCard();
	}
	
	private class CSettlement implements Settlement{
		
	}
	
	private class CCity implements City {
		
	}
	
	private class CDevelopmentCard implements DevelopmentCard {
		
	}
}
