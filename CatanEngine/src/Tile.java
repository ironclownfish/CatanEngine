import java.util.ArrayList;


public class Tile {
	
	private byte resourceTypeGained = ResourceCard.TYPE_NONE;
	public byte resourceTypeGained() {
		return resourceTypeGained;
	}
	
	public Tile(byte resourceType) {
		resourceTypeGained = resourceType;
	}

}
