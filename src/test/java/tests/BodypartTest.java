package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import snake.Bodypart;
import snake.GameObject;

public class BodypartTest {

	private Bodypart bodypart;
	
	@Before
	public void setup(){
		bodypart = new Bodypart(0, 1);
		bodypart.setNextBodypart(new Bodypart(2, 2));
	}
	
	@Test
	public void testGetNextBodypart(){
		assertNotNull(bodypart.getNextBodypart());
	}
	
	@Test
	public void testInheritance(){
		assertTrue(bodypart instanceof Bodypart);
		assertTrue(bodypart instanceof GameObject);
	}
	
	@Test
	public void testCoordinates(){
		assertEquals(bodypart.getPositionX(), 0);
		assertEquals(bodypart.getPositionY(), 1);
	}
	
	@Test
	public void testCreateHead(){
		Bodypart head = new Bodypart(3,3);
		head.setIsHead(true);
		assertTrue(head.isHead());
	}
	
	
	
}
