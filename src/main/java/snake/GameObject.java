package snake;

/**
 * Super class for all game objects. Each game object has a position.
 *
 */
public abstract class GameObject {

	private int x;

	private int y;

	protected GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getPositionX() {
		return this.x;
	}

	public int getPositionY() {
		return this.y;
	}

	public void setPositionX(int x) {
		this.x = x;
	}

	public void setPositionY(int y) {
		this.y = y;
	}

}
