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
	Board board = new Board();
	
	public void setup() {
		
		String answer = "";
		
		//Set up ports
		answer = ask("Are the ports connected in suggested order? (y/n)");
		switch (answer.charAt(0)) {
		case 'y':
		case 'Y':
			board.setPorts(new Port[] {
				new Port(2, ResourceCard.TYPE_ORE),
				new Port(2, ResourceCard.TYPE_WHEAT),
				new Port(3, ResourceCard.TYPE_GENERIC),
				new Port(2, ResourceCard.TYPE_WOOD),
				new Port(2, ResourceCard.TYPE_CLAY),
				new Port(3, ResourceCard.TYPE_GENERIC),
				new Port(3, ResourceCard.TYPE_GENERIC),
				new Port(3, ResourceCard.TYPE_SHEEP),
				new Port(3, ResourceCard.TYPE_GENERIC)
		});
			break;
		default:
			Port[] ports = new Port[9];
			ports[0] = new Port(2, ResourceCard.TYPE_ORE);
			for (int i = 1; i < 9; i++) {
				answer = ask("What kind of resource does the port " + i + " spaces counterclockwise from the ore port accept?");
				byte type = ResourceCard.inferResourceTypeFromPlayerInputString(answer);
				int ratio = Port.getExpectedTradeRatioFromType(type);
				ports[i] = new Port(ratio, type);

				//See if we can infer the next port by what this one is (if they're on the same board segment).
				if 		(type == ResourceCard.TYPE_WHEAT || type == ResourceCard.TYPE_CLAY || type == ResourceCard.TYPE_SHEEP)
					ports[++i] = new Port(3, ResourceCard.TYPE_GENERIC);
			}
		}
		
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
