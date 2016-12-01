package snake;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * This class handles the game loop: reading the user input, calculating the
 * next position and rendering the next frame.
 */
public class GameController {

	private ArrayList<Bodypart> bodyparts = null;
	private ArrayList<Food> foodList = null;
	private Direction currentDirection = Direction.UP;

	private Arena arena;

	public GameController(Arena arena) {
		this.arena = arena;
		this.initGameObjects();
	}

	public void initGameObjects() {
		initBodyparts();
		initFood();
	}

	private void initFood() {
		foodList = new ArrayList<Food>();
		Food foodObject = new Food(4, 4);
		foodList.add(foodObject);
	}

	private void initBodyparts() {
		this.bodyparts = new ArrayList<Bodypart>();
		Bodypart head = new Bodypart(10, 10);
		head.setIsHead(true);
		Bodypart tail = new Bodypart(10, 11);
		tail.setNextBodypart(head);
		bodyparts.add(head);
		bodyparts.add(tail);
	}

	public ArrayList<Bodypart> getBodypartList() {
		return this.bodyparts;
	}

	public ArrayList<Food> getFoodList() {
		return this.foodList;
	}

	public void moveSnake(Direction direction) {
		for (int i = bodyparts.size() - 1; i >= 0; i--) {
			Bodypart part = bodyparts.get(i);
			if (part.isHead()) {
				moveHead(part, direction);
			} else {
				Bodypart next = part.getNextBodypart();
				if (next != null) {
					part.setPositionX(next.getPositionX());
					part.setPositionY(next.getPositionY());
				}
			}
		}
	}

	private void moveHead(Bodypart head, Direction direction) {
		this.setCurrentDirection(direction);
		switch (currentDirection) {
		case UP:
			head.setPositionY(head.getPositionY() - 1);
			break;
		case DOWN:
			head.setPositionY(head.getPositionY() + 1);
			break;
		case LEFT:
			head.setPositionX(head.getPositionX() - 1);
			break;
		case RIGHT:
			head.setPositionX(head.getPositionX() + 1);
			break;
		default:
			// something strange happened
		}

	}

	public boolean isBorderIntersecting() {
		boolean borderIntersection = false;
		Bodypart head = this.bodyparts.get(0);
		if (head.getPositionX() < 0 || head.getPositionX() >= this.arena.x || head.getPositionY() < 0
				|| head.getPositionY() >= this.arena.y) {
			borderIntersection = true;
		}

		return borderIntersection;
	}

	public void addBodypart() {
		Bodypart newBodypart = new Bodypart(-1, -1);
		newBodypart.setNextBodypart(bodyparts.get(bodyparts.size() - 1));
		bodyparts.add(newBodypart);

	}

	public boolean isSelfIntersecting() {
		boolean selfIntersection = false;
		Bodypart head = bodyparts.get(0);
		for (int i = 1; i < bodyparts.size(); i++) {
			Bodypart part = bodyparts.get(i);
			if (head.getPositionX() == part.getPositionX() && head.getPositionY() == part.getPositionY()) {
				selfIntersection = true;
				break;
			}
		}

		return selfIntersection;
	}

	public boolean isGameOver() {
		boolean isGameOver = false;
		if (this.isBorderIntersecting() || this.isSelfIntersecting()) {
			isGameOver = true;
		}
		return isGameOver;
	}

	public boolean isSnakeOnThisPosition(int x, int y) {
		boolean isOnPosition = false;
		for (Bodypart part : this.bodyparts) {
			if (part.getPositionX() == x && part.getPositionY() == y) {
				isOnPosition = true;
				break;
			}
		}
		return isOnPosition;
	}

	/**
	 * Only call this if you have not won yet and if the count of food is
	 * smaller than the max count of food Creates ONE food
	 * 
	 */
	public void createRandomFood() {
		int x, y;
		Random random = new Random();
		do {
			x = random.nextInt(this.arena.x + 1);
			y = random.nextInt(this.arena.y + 1);
		} while (this.isSnakeOnThisPosition(x, y) || this.isFoodOnThisPosition(x, y));

		this.createFood(x, y);
	}

	public void createFood(int x, int y) {
		Food food = new Food(x, y);
		this.foodList.add(food);
	}

	public boolean isFoodOnThisPosition(int x, int y) {
		boolean isOnPosition = false;
		for (Food food : this.foodList) {
			if (food.getPositionX() == x && food.getPositionY() == y) {
				isOnPosition = true;
				break;
			}
		}
		return isOnPosition;
	}

	public Food getFoodOnSnakehead() {
		Food result = null;
		Bodypart head = this.bodyparts.get(0);
		for (Food food : this.foodList) {
			if (food.getPositionX() == head.getPositionX() && food.getPositionY() == head.getPositionY()) {
				result = food;
				break;
			}
		}
		return result;
	}

	public void eatFood() {
		Food food = this.getFoodOnSnakehead();
		if (food != null) {
			this.foodList.remove(food);
			this.addBodypart();
		}

	}

	public boolean isWin() {
		boolean win = false;
		int possiblePositions = this.arena.x * this.arena.y;
		if (this.bodyparts.size() >= possiblePositions) {
			win = true;
		}
		return win;
	}

	public void calculateFrame() {

		this.moveSnake(this.getCurrentDirection());
		if (this.isGameOver()) {
			// show game over screen in gui
		} else {
			this.eatFood();
			if (this.isWin()) {
				// show win screen in gui
			} else {
				if (this.foodList.size() < this.arena.maxFood) {
					this.createRandomFood();
				}
			}
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(Direction direction) {
		if (currentDirection == Direction.UP && direction == Direction.DOWN) {
			// TODO simplify ifs
		} else if (currentDirection == Direction.DOWN && direction == Direction.UP) {
			// do nothing
		} else if (currentDirection == Direction.RIGHT && direction == Direction.LEFT) {
			// do nothing
		} else if (currentDirection == Direction.LEFT && direction == Direction.RIGHT) {
			// do nothing
		} else {
			this.currentDirection = direction;
		}
	}

}
