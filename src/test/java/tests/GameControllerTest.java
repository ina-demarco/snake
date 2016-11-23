package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import snake.Direction;
import snake.GameController;

public class GameControllerTest {

	GameController controller;

	@Before
	public void setup() {
		controller = new GameController();
		controller.initGameObjects();
	}

	@Test
	public void testBodypartList() {
		assertEquals(controller.getBodypartList().size(), 2);
	}

	@Test
	public void testFoodList() {
		assertEquals(controller.getFoodList().size(), 1);
	}

	@Test
	public void testMoveSnakeLeft() {

		int headX = controller.getBodypartList().get(0).getPositionX();
		int headY = controller.getBodypartList().get(0).getPositionY();
		controller.moveSnake(Direction.LEFT);
		int headXAfterMove = controller.getBodypartList().get(0).getPositionX();
		int headYAfterMove = controller.getBodypartList().get(0).getPositionY();
		int bodyXAfterMove = controller.getBodypartList().get(1).getPositionX();
		int bodyYAfterMove = controller.getBodypartList().get(1).getPositionY();

		assertEquals(bodyXAfterMove, headX);
		assertEquals(bodyYAfterMove, headY);
		assertEquals(headX - 1, headXAfterMove);
		assertEquals(headY, headYAfterMove);
	}

	@Test
	public void testMoveSnakeRight() {

		int headX = controller.getBodypartList().get(0).getPositionX();
		int headY = controller.getBodypartList().get(0).getPositionY();
		controller.moveSnake(Direction.RIGHT);
		int headXAfterMove = controller.getBodypartList().get(0).getPositionX();
		int headYAfterMove = controller.getBodypartList().get(0).getPositionY();
		int bodyXAfterMove = controller.getBodypartList().get(1).getPositionX();
		int bodyYAfterMove = controller.getBodypartList().get(1).getPositionY();

		assertEquals(bodyXAfterMove, headX);
		assertEquals(bodyYAfterMove, headY);
		assertEquals(headX + 1, headXAfterMove);
		assertEquals(headY, headYAfterMove);
	}

	@Test
	public void testMoveSnakeUp() {

		int headX = controller.getBodypartList().get(0).getPositionX();
		int headY = controller.getBodypartList().get(0).getPositionY();
		controller.moveSnake(Direction.UP);
		int headXAfterMove = controller.getBodypartList().get(0).getPositionX();
		int headYAfterMove = controller.getBodypartList().get(0).getPositionY();
		int bodyXAfterMove = controller.getBodypartList().get(1).getPositionX();
		int bodyYAfterMove = controller.getBodypartList().get(1).getPositionY();

		assertEquals(bodyXAfterMove, headX);
		assertEquals(bodyYAfterMove, headY);
		assertEquals(headX, headXAfterMove);
		assertEquals(headY - 1, headYAfterMove);
	}

	@Test
	public void testMoveSnakeDown() {
		// current direction is direction.up, moving down is not possible -->
		// continue moving up
		int headX = controller.getBodypartList().get(0).getPositionX();
		int headY = controller.getBodypartList().get(0).getPositionY();
		controller.moveSnake(Direction.DOWN);
		int headXAfterMove = controller.getBodypartList().get(0).getPositionX();
		int headYAfterMove = controller.getBodypartList().get(0).getPositionY();
		int bodyXAfterMove = controller.getBodypartList().get(1).getPositionX();
		int bodyYAfterMove = controller.getBodypartList().get(1).getPositionY();

		assertEquals(bodyXAfterMove, headX);
		assertEquals(bodyYAfterMove, headY);
		assertEquals(headX, headXAfterMove);
		assertEquals(headY - 1, headYAfterMove);
	}

	@Test
	public void testBorderIntersection() {

		controller.moveSnake(Direction.LEFT);
		assertFalse(controller.checkBorderIntersection());

		for (int i = 0; i <10; i++) {
			controller.moveSnake(Direction.LEFT);
		}
		assertTrue(controller.checkBorderIntersection());
	}

}
