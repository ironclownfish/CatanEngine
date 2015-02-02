import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class CatanDriver {

	public static void main(String[] args) {
		CatanDriver cd = new CatanDriver();
		cd.setup();
	}
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Board board = new Board();
	ArrayList<Player> players;
	
	public void setup() {
		
		String answer = "";
		
		//Set up ports
		if(askForYN("Are the ports connected in suggested order? (y/n)"))
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
		}); else {
			int[] memoryOfPlaced = new int[9];
			
			Port[] ports = new Port[9];
			ports[0] = new Port(2, ResourceCard.TYPE_ORE);
			for (int i = 1; i < 9; i++) {
				byte type = askForPortType("What kind of resource does the port " + i + " spaces counterclockwise from the ore port accept?");
				int ratio = Port.getExpectedTradeRatioFromType(type);
				ports[i] = new Port(ratio, type);

				//See if we can infer the next port by what this one is (if they're on the same board segment).
				if (type == ResourceCard.TYPE_WHEAT || type == ResourceCard.TYPE_CLAY || type == ResourceCard.TYPE_SHEEP)
					ports[++i] = new Port(3, ResourceCard.TYPE_GENERIC);
			}
			board.setPorts(ports);
		}
		
		//Set up tiles.
		int[] x = {-2, -2, -1,  0,  1,  2,  2,  2,  1,  0, -1, -2,/**/ -1, -1,  0,  1,  1,  0,/**/ 0},
			  y = { 1,  0, -1, -2, -2, -2, -1,  0,  1,  2,  2,  2,/**/  1,  0, -1, -1,  0,  1,/**/ 0};
		for (int i = 0; i < 19; i++) {
			byte type = ResourceCard.TYPE_NONE;
			switch(i) {
			case 0:
				type = askForTileType("What kind of tile is next to the rock port?");
				break;
			case 1:
				type = askForTileType("What kind of tile is between the rock port and the " + ResourceCard.typeName(board.getPort(1).takesType()) + " port next to it?");
				break;
			case 2:
			case 3:
			case 4:
				type = askForTileType("What kind of tile is next to the " + ResourceCard.typeName(board.getPort(i - 1).takesType()) + " port?");
				break;
			case 6:
			case 7:
			case 8:
				type = askForTileType("What kind of tile is next to the " + ResourceCard.typeName(board.getPort(i - 2).takesType()) + " port?");
				break;
			case 9:
				type = askForTileType("What kind of tile is between the " + ResourceCard.typeName(board.getPort(i - 3).takesType()) + " port"
						+ " and the " + ResourceCard.typeName(board.getPort(i - 2).takesType()) + " port next to it?");
				break;
			case 10:
			case 11:
				type = askForTileType("What kind of tile is next to the " + ResourceCard.typeName(board.getPort(i - 3).takesType()) + " port?");
				break;
				
			case 18:
				type = askForTileType("What kind of tile is in the center?");
				break;
			default:
				type = askForTileType("What kind of tile is " + i + " places counterclockwise from the rock port (spiraling toward the center)?");	
			}
			board.setTile(type, x[i], y[i]);
		}
		
		//Set up players.
		int numPlayers = askForNumber("How many players will there be (including A.I.)?");
		players = new ArrayList<Player>(numPlayers);
			
		for (int i = 1; i <= numPlayers; i++) {
			String name = ask("Please enter a name for player " + i);
			addPlayer(name);
		};
		
		//Make sure the turn order is correct
		while (!askForYN("Is this the correct turn order?\n " + listPlayerNames().toString())) {
			ArrayList<Player> playersCopy = new ArrayList<Player>();
			playersCopy.addAll(players);
			
			for (Player p : playersCopy) {
				int place = -1;
				while (place < 0 || place >= players.size())
					place = askForNumber("What turn placement does " + p.getName() + " have? (1 for 1st, 2 for 2nd...)") - 1;
				Collections.swap(players, players.indexOf(p), place);
			}
		}
		
		//Setup is complete.
		System.out.println("Setup is complete.");

		for (int i = -2; i <= 2; i++)
			for (int j = -2; j <= 2; j++)
				if (board.inBounds(i, j)) {
					System.out.println(ResourceCard.typeName(board.getTile(i, j).resourceTypeGained()));	
				}
		return;
	}
	
	private void addPlayer(String name) {
		players.add(new Player(name));
	}
	
	private ArrayList<String> listPlayerNames() {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < players.size(); i++)
			ret.add(players.get(i).getName());
		return ret;
	}
	
	public static int askForNumber(String out) {
		String answer;
		while(true) {
			answer = ask(out);
			if (answer.matches("\\d+"))
				return Integer.parseInt(answer);
			System.out.println("Answer must be a number.");
		}
	}
	
	public static byte askForPortType(String out) {
		String answer;
		
		while (true) {
			answer = ask(out).toLowerCase();
			switch(answer) {
			case "wood":
			case "lumber":
				return ResourceCard.TYPE_WOOD;
			case "clay":
			case "brick":
				return ResourceCard.TYPE_CLAY;
			case "wool":
			case "sheep":
				return ResourceCard.TYPE_SHEEP;
			case "wheat":
			case "grain":
				return ResourceCard.TYPE_WHEAT;
			case "rock":
			case "ore":
				return ResourceCard.TYPE_ORE;
			case "?":
			case "any":
			case "generic":
			case "all":
				return ResourceCard.TYPE_GENERIC;
			default:
				System.out.println("This is not a valid resource type. Try wood, clay, wheat, wool, ore, or ?.");
			}
		}
	}
	
	public static byte askForTileType(String out) {
		String answer;
		
		while (true) {
			answer = ask(out).toLowerCase();
			switch(answer) {
			case "wood":
			case "lumber":
				return ResourceCard.TYPE_WOOD;
			case "clay":
			case "brick":
				return ResourceCard.TYPE_CLAY;
			case "wool":
			case "sheep":
				return ResourceCard.TYPE_SHEEP;
			case "wheat":
			case "grain":
				return ResourceCard.TYPE_WHEAT;
			case "rock":
			case "ore":
				return ResourceCard.TYPE_ORE;
			case "desert":
			case "nothing":
				return ResourceCard.TYPE_NONE;
			default:
				System.out.println("This is not a valid tile type. Try wood, clay, wheat, wool, ore, or desert.");
			}
		}
	}
	
	public static byte askForResourceType(String out) {
		String answer;
		
		while (true) {
			answer = ask(out).toLowerCase();
			switch(answer) {
			case "wood":
			case "lumber":
				return ResourceCard.TYPE_WOOD;
			case "clay":
			case "brick":
				return ResourceCard.TYPE_CLAY;
			case "wool":
			case "sheep":
				return ResourceCard.TYPE_SHEEP;
			case "wheat":
			case "grain":
				return ResourceCard.TYPE_WHEAT;
			case "rock":
			case "ore":
				return ResourceCard.TYPE_ORE;
			default:
				System.out.println("This is not a valid resource type. Try wood, clay, wheat, wool, or ore.");
			}
		}
	}
	
	public static boolean askForYN(String out) {
		String answer;
		while (true) {
			answer = ask(out);
			switch(answer.charAt(0)) {
			case 'y':
			case 'Y':
				return true;
			case 'n':
			case 'N':
				return false;
			default:
				System.out.println("Did not understand answer to yes/no question. Try yes or no.");
			}
		}
	}
	
	public static String ask(String out) {
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
