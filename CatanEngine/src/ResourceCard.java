import java.util.ArrayList;
import java.util.Arrays;

public class ResourceCard {

	public static final byte TYPE_ORE = 0;
	public static final byte TYPE_WOOD = 1;
	public static final byte TYPE_SHEEP = 2;
	public static final byte TYPE_WHEAT = 3;
	public static final byte TYPE_CLAY = 4;
	public static final byte TYPE_GENERIC = 5;
	public static final byte TYPE_NONE = 6;
	
	private byte myType = TYPE_GENERIC;
	
	public byte type() {
		return myType;
	}
	
	public static ResourceCard getCardOfType(byte wantType) {
		switch (wantType) {
			case TYPE_ORE:
				return new Ore();
			case TYPE_WOOD:
				return new Wood();
			case TYPE_SHEEP:
				return new Sheep();
			case TYPE_WHEAT:
				return new Wheat();
			case TYPE_CLAY:
				return new Clay();
			default:
				return null;
		}
	}
	
	public static String typeName(byte type) {
		switch(type) {
		case TYPE_ORE:
			return "ore";
		case TYPE_WOOD:
			return "wood";
		case TYPE_SHEEP:
			return "sheep";
		case TYPE_WHEAT:
			return "wheat";
		case TYPE_CLAY:
			return "clay";
		case TYPE_GENERIC:
			return "any";
		case TYPE_NONE:
			return "none";
		}
		return "no type";
	}
}
