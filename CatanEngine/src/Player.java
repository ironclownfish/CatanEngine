
public class Player {
	
	private String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDiceRoll() {
		int roll;
		while (true) {
			roll = CatanDriver.askForNumber("What does " + name + " roll?");
			if (roll > 0 && roll < 13)
				return roll;
			System.out.println("Roll must be from 1 to 12");
		}
	}
}
