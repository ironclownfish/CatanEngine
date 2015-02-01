
public class Board {

	private Tile[][] tiles = new Tile[5][5];
	private Vertex[][] vertices = new Vertex[2][];
	private Port[] ports = new Port[9];
	
	public Board() {
		vertices[0] = new Vertex[6];
		vertices[1] = new Vertex[18];
		vertices[2] = new Vertex[36];
	}
	
	public void setTile(String resource, int x, int y) {
		if (!inBounds(x, y))
			return;
		x += 2; y += 2;
		//TODO
	}
	
	public Tile getTile(int x, int y) {
		if (!inBounds(x, y))
			return null;
		return tiles[x + 2][y + 2];
	}
	
	public boolean inBounds(int x, int y) {
		return Math.abs(x + y) <= 3;
	}
	
	public Port getPort(int t) {
		return ports[t%9];
	}
	
	public void setPorts(Port[] ports) {
		this.ports = ports;
	}
}
