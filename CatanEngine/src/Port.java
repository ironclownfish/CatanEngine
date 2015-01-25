
public class Port {

	private int ratio;
	private ResourceCard takesCard;
	
	public Port(int ratio, ResourceCard takesCard) {
		this.takesCard = takesCard;
		this.ratio = ratio;
	}
	
	public int ratio() {
		return ratio;
	}
	
	public ResourceCard takesCard() {
		return takesCard;
	}
	
}
