
public class Vertex {

	private SettlementFactory settlement = null;
	
	public boolean isSettled() {
		return settlement != null;
	}
	
	public SettlementFactory getSettlement() {
		return settlement;
	}
}
