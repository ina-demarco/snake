package tests;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import snake.Food;

public class FoodTest {

	private Food food;
	
	@Before
	public void setup(){
		food=new Food(1, 1);
	}
	
	@Test
	public void testCoodinates(){
		assertEquals(food.getPositionX(), 1);
		assertEquals(food.getPositionY(), 1);
	}
}
