package snake;

public class Bodypart extends GameObject {

	private Bodypart nextBodypart;
	private boolean isHead = false;

	public Bodypart(int x, int y) {
		super(x, y);
	}

	public Bodypart getNextBodypart() {
		return this.nextBodypart;
	}

	public void setNextBodypart(Bodypart nextBodypart) {
		this.nextBodypart = nextBodypart;
	}

	public void setIsHead(boolean isHead) {
		this.isHead = true;
	}

	public boolean isHead() {
		return this.isHead;
	}
}
