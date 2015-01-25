import java.util.Arrays;


public class ResourceCard {
	
	public static String[] types = new String[] {
		"wood",
		"clay",
		"ore",
		"wheat",
		"sheep"
	};
	
	public static ResourceCard cardOfType(String type) {
		return new ResourceCard(type);
	}
	
	public String resourceType = null;
	
	public ResourceCard(String type) {
		//Check that the type exists
		for(String availableType : ResourceCard.types)
			if (type.equalsIgnoreCase(availableType))
				this.resourceType = type;
	}
}
