
public class Port {

	public static int getExpectedTradeRatioFromType(byte type) {
		return type == ResourceCard.TYPE_GENERIC? 3 : 2;
	}
	
	private int ratio;
	private byte takesType;
	
	public Port(int ratio, byte takesType) {
		this.takesType = takesType;
		this.ratio = ratio;
	}
	
	public int ratio() {
		return ratio;
	}
	
	public byte takesType() {
		return takesType;
	}
	
	public ResourceCard trade(ResourceCard[] payment, byte wantType) {
		//Check for valid payment.
		for (int i = 0; i < payment.length; i++) {
			for (int j = i+1; j < payment.length; j++) {
				ResourceCard card1 = payment[i], card2 = payment[j];
				if (card1 == card2 || card1 == null || card2 == null || card1.type() != takesType || payment.length != ratio) {
					System.out.println("Invalid trade.");
					return null;
				}
			}
		}
		
		return ResourceCard.getCardOfType(wantType);
	}
}
