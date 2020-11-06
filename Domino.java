
public class Domino {
	private String top;
	private String bottom;
	private String DominoNumber;
	
	public Domino(String top, String bottom, String num) {
		this.top = top;
		this.bottom = bottom;
		this.DominoNumber = num;
	}
	
	public String getTop() {
		return top;
	}
	
	public String getBottom() {
		return bottom;
	}
	
	public String getDominoNum() {
		String dNum = "D" + DominoNumber;
		return dNum;
	}
	
	public boolean isValidDomino() {
		if(top.startsWith(bottom) || bottom.startsWith(top)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		String d = top + " / " + bottom;
		return d;
	}
}
