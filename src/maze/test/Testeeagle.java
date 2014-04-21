
	package maze.test;


	import maze.logic.*;
import static org.junit.Assert.*;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class Testeeagle.
 */
public class Testeeagle {
	/*UP = 1;
	 LEFT = 2;
	 DOWN = 3;
    RIGHT = 4
	
	{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
	{ 'x', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
	{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
	{ 'x', 'D', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
	{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
	{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', 'S' },
	{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
	{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
	{ 'x', 'E', 'x', 'x', 'D', ' ', ' ', ' ', ' ', 'x' },
	{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } */
	
	private int[] Hpos = {1,1};
	private Hero hero = new Hero(Hpos);
	private Eagle eagle = new Eagle(hero.getPos());
	private int[] Spos = {8,1};
	private Sword sword = new Sword(Spos);
	private int[] Dpos = {3,1};
	private Dragon dragon1 = new Dragon(Dpos);
	private Maze m = new Maze();
	int[] D2pos = {8,4};
	Dragon dragon2 = new Dragon(D2pos);
	
	
	/**
	 * Tests is the user can send the eagle to go and get the sword.
	 */
	@Test
	public void sendEagle()
	{
		eagle.EagleGetSword(sword);
		assertEquals(false, eagle.isAtHero());
		assertEquals(true, eagle.isflying());
		assertEquals(false, eagle.hasSword());
		assertEquals(true, eagle.isAlive());
		
		
	}	
	
	/**
	 * Tests if the eagle moves in the direction of the sword once the hero moves.
	 */
	@Test
	public void moveEagle()
	{

		 
		 for(int i=0;i<4;i++)
		 {

			 eagle.EagleGetSword(sword);
		 }
		
		 assertEquals(4,eagle.getWPos());
		 assertEquals(1,eagle.getHPos());
		
	}
	
	
	
	
	
	/**
	 * Tests if the eagle picks up the sword once it gets on it's position.
	 */
	@Test
	public void eaglePickSword()
	{
		
		
		for(int i=0;i<8;i++)
		 {
			 eagle.EagleGetSword(sword);
		 }
		
		 assertEquals(8,eagle.getWPos());
		 assertEquals(1,eagle.getHPos());
		 assertEquals(true,eagle.hasSword());
	}
	
	/**
	 * Tests if the eagle returns wit the sword, and if the hero becomes armed after he picks 
	 * up the eagle.
	 */
	@Test
	public void eagleReturnSword()
	{
		for(int i=0;i<8;i++)
		 {
			 eagle.EagleGetSword(sword);
		 }
		
		 assertEquals(8,eagle.getWPos());
		 assertEquals(1,eagle.getHPos());
		 assertEquals(true,eagle.hasSword());
		 
		 for(int i=0;i<8;i++)
		 {
			 eagle.retrieveSword(sword);
		 }
		 
		 assertEquals(1,eagle.getWPos());
		 assertEquals(1,eagle.getHPos());
		 assertEquals(1,hero.getWPos());
		 assertEquals(1,hero.getHPos());
		 hero.catchEagle(eagle);
		 assertEquals(true,hero.hasSword());
	}
	
	/**
	 * Test if the eagle dies when a dragon goes near it after it lands on the position
	 * it was sent from.
	 */
	@Test
	public void eagleDie()
	{
		for(int i=0;i<8;i++)
		 {
			 eagle.EagleGetSword(sword);
		 }
		hero.move(4, m);
		 assertEquals(1,hero.getWPos());
		 assertEquals(2,hero.getHPos());
		 assertEquals(8,eagle.getWPos());
		 assertEquals(1,eagle.getHPos());
		 assertEquals(true,eagle.hasSword());
		 
		 for(int i=0;i<8;i++)
		 {
			 eagle.retrieveSword(sword);
		 }
		 assertEquals(1,eagle.getWPos());
		 assertEquals(1,eagle.getHPos());
		 dragon1.setNoSleep(true);
		 dragon1.move(1, m);
		 dragon1.move(1, m);
		 assertEquals(1,dragon1.getWPos());
		 assertEquals(1,dragon1.getHPos());
		 assertEquals(1,hero.getWPos());
		 assertEquals(2,hero.getHPos());
		 assertEquals(1,sword.getWPos());
		 assertEquals(1,sword.getHPos());
		 assertEquals(false,eagle.isAtHero());
		 assertEquals(false,eagle.isflying());
		 assertEquals(true,eagle.EagleatDrag(dragon1, sword));
		 assertEquals(false,eagle.isAlive());
	}
}
