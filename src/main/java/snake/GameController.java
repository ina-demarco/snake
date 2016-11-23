package snake;

import java.util.ArrayList;

public class GameController {

	private ArrayList<Bodypart> bodyparts = null;
	private ArrayList<Food> food = null;
	private Direction currentDirection = Direction.UP;
	private Arena arena;

	public GameController() {
		this.arena = new Arena();
		this.arena.x = 20;
		this.arena.y = 20;
	}

	public void initGameObjects() {
		initBodyparts();
		initFood();
	}

	private void initFood() {
		food = new ArrayList<Food>();
		Food foodObject = new Food(4, 4);
		food.add(foodObject);
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
		return this.food;
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
		if (currentDirection == Direction.UP && direction == Direction.DOWN) {
			// TODO simplify ifs
		} else if (currentDirection == Direction.DOWN && direction == Direction.UP) {
			// do nothing
		} else if (currentDirection == Direction.RIGHT && direction == Direction.LEFT) {
			// do nothing
		} else if (currentDirection == Direction.LEFT && direction == Direction.RIGHT) {
			// do nothing
		} else {
			currentDirection = direction;
		}
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

	public boolean checkBorderIntersection() {
		boolean borderIntersection = false;
		Bodypart head = this.bodyparts.get(0);
		if (head.getPositionX() < 0 || head.getPositionX() > this.arena.x || head.getPositionY() < 0
				|| head.getPositionY() > this.arena.y) {
			borderIntersection = true;
		}

		return borderIntersection;
	}
}
