package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import snake.Arena;
import snake.Direction;
import snake.GameController;

public class GameControllerTest {

	private GameController controller;
	private Arena arena;

	@Before
	public void setup() {
		arena = new Arena();
		controller = new GameController(arena);
		controller.initGameObjects();
	}

	@Test
	public void testBodypartList() {
		assertEquals(2, controller.getBodypartList().size());
	}

	@Test
	public void testFoodList() {
		assertEquals(1, controller.getFoodList().size());
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
		assertFalse(controller.isBorderIntersecting());

		for (int i = 0; i < 10; i++) {
			controller.moveSnake(Direction.LEFT);
		}
		assertTrue(controller.isBorderIntersecting());
	}

	@Test
	public void testAddBodypart() {
		int bodypartCount = controller.getBodypartList().size();
		controller.addBodypart();
		assertEquals(bodypartCount + 1, controller.getBodypartList().size());

	}

	@Test
	public void testSelfIntersection() {
		for (int i = 0; i < 3; i++) {
			controller.addBodypart();
		}
		controller.moveSnake(Direction.LEFT);
		assertFalse(controller.isSelfIntersecting());
		controller.moveSnake(Direction.DOWN);
		assertFalse(controller.isSelfIntersecting());
		controller.moveSnake(Direction.RIGHT);
		assertTrue(controller.isSelfIntersecting());
	}

	@Test
	public void testCheckGameOverWithBorderIntersection() {
		controller.moveSnake(Direction.LEFT);
		assertFalse(controller.isGameOver());

		for (int i = 0; i < 10; i++) {
			controller.moveSnake(Direction.LEFT);
		}
		assertTrue(controller.isGameOver());
	}

	@Test
	public void testCheckGameOverWithSelfIntersection() {
		for (int i = 0; i < 3; i++) {
			controller.addBodypart();
		}
		controller.moveSnake(Direction.LEFT);
		assertFalse(controller.isGameOver());
		controller.moveSnake(Direction.DOWN);
		assertFalse(controller.isGameOver());
		controller.moveSnake(Direction.RIGHT);
		assertTrue(controller.isGameOver());
	}

	@Test
	public void testCreateFood() {
		assertEquals(1, controller.getFoodList().size());
		controller.createRandomFood();
		assertEquals(2, controller.getFoodList().size());
	}

	@Test
	public void testFoodOnPosition() {
		assertTrue(controller.isFoodOnThisPosition(4, 4));
		assertFalse(controller.isFoodOnThisPosition(20, 20));
		assertFalse(controller.isFoodOnThisPosition(10, 10));

	}

	@Test
	public void testSnakeOnPosition() {
		assertTrue(controller.isSnakeOnThisPosition(10, 10));
		assertFalse(controller.isSnakeOnThisPosition(20, 10));
	}

	@Test
	public void testIsSnakeheadOnFood() {
		assertNull(controller.getFoodOnSnakehead());
		controller.createFood(9, 10);
		assertNull(controller.getFoodOnSnakehead());
		controller.moveSnake(Direction.LEFT);
		assertNotNull(controller.getFoodOnSnakehead());
		controller.moveSnake(Direction.LEFT);
		assertNull(controller.getFoodOnSnakehead());
	}

	@Test
	public void testEatFood() {
		controller.createFood(9, 10);
		assertEquals(2, controller.getFoodList().size());
		controller.moveSnake(Direction.LEFT);
		controller.eatFood();
		assertEquals(1, controller.getFoodList().size());
		assertEquals(3, controller.getBodypartList().size());
		controller.moveSnake(Direction.LEFT);
		controller.eatFood();
		assertEquals(1, controller.getFoodList().size());
		assertEquals(3, controller.getBodypartList().size());
	}

	@Test
	public void testWin() {
		assertFalse(controller.isWin());
		// 398 because the default arena is 20*20 and we already have two snake
		// bodyparts
		for (int i = 0; i < 398; i++) {
			controller.addBodypart();
		}
		assertTrue(controller.isWin());
	}

	@Test
	public void testReadUserInput() {
		// TODO Keybindings ?
	}

	@Test
	public void testCalculateFrame() {
		for (int i = 0; i < 11; i++) {
			controller.calculateFrame();
		}
		// only with food max size = 3
		assertEquals(3, controller.getFoodList().size());
		assertTrue(controller.isGameOver());

	}

}
