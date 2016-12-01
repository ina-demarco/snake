package snake;

/**
 * Enum for User Input Direction
 */
public enum Direction {
	RIGHT, LEFT, UP, DOWN;

	public int getAngle() {
		int angle = 0;
		switch (this) {
		case UP:
			angle = 90;
			break;
		case LEFT:
			angle = 180;
			break;
		case DOWN:
			angle = 270;
			break;
		case RIGHT:
			angle = 0;
			break;
		default:
			angle = 0;
			System.out.println("using default angle 0");
		}
		return angle;
	}
}
