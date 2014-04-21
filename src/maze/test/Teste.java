package maze.test;



import maze.logic.*;
import static org.junit.Assert.*;

import org.junit.Test;


/**
 * The Class Teste. This class tests the hero and the winning conditions.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class Teste {
	
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
	private int[] Spos = {8,1};
	private Sword sword = new Sword(Spos);
	private int[] Dpos = {3,1};
	private Dragon dragon = new Dragon(Dpos);
	private Maze m = new Maze();
	private int [] saida = m.getExitPos();
	

	
	
	/**
	 * Tests if the hero moves to the chosen location.
	 */
	@Test
	public void testMove()
	{
		hero.move(4, m);
		assertEquals(1, hero.getWPos());
		assertEquals(2, hero.getHPos());
		hero.move(4, m);
		assertEquals(1, hero.getWPos());
		assertEquals(3, hero.getHPos());
		hero.move(4, m);
		assertEquals(1, hero.getWPos());
		assertEquals(4, hero.getHPos());
		hero.move(3, m);
		assertEquals(2, hero.getWPos());
		assertEquals(4, hero.getHPos());
	}
	
	/**
	 * Tests if the user moves if he goes in a direction occupied by a wall.
	 */
	@Test
	public void testMoveWall()
	{
		hero.move(4, m);
		assertEquals(1, hero.getWPos());
		assertEquals(2, hero.getHPos());
		hero.move(4, m);
		assertEquals(1, hero.getWPos());
		assertEquals(3, hero.getHPos());
		hero.move(3, m);
		assertEquals(1, hero.getWPos());
		assertEquals(3, hero.getHPos());
	}
	
	/**
	 * Tests if the hero picks up the sword once he moves into the same position that the sword
	 * is in.
	 */
	@Test
	public void testSword()
	{
		 int[] moves = {4,4,4,3,3,3,3,2,2,2,3,3,3}; 
		 
		 for(int i=0;i<moves.length;i++)
		 {
			 hero.move(moves[i], m);
		 }
		 assertEquals(true,hero.pickedUpSword(sword));
		 assertEquals(true,hero.hasSword());
	}
	
	/**
	 * Tests if the hero is killed by the dragon when the hero goes near the dragon without the
	 * sword.
	 */
	@Test
	public void dieDragon()
	{
		 int[] moves = {3}; 
		 
		 for(int i=0;i<moves.length;i++)
		 {
			 hero.move(moves[i], m);
		 }
		 assertEquals(true,hero.isNearDragon(dragon));
		 assertEquals(false,hero.isAlive());
	}
	
	/**
	 * Tests if the dragon is killed by the hero when the hero goes near the dragon with the
	 * sword.
	 */
	@Test
	public void killDragon()
	{
		 int[] moves = {4,4,4,3,3,3,3,2,2,2,3,3,3}; 
		 
		 for(int i=0;i<moves.length;i++)
		 {
			 hero.move(moves[i], m);
		 }
		 assertEquals(true,hero.pickedUpSword(sword));
		 assertEquals(true,hero.hasSword());
		 
		 int[] moremoves = {1,1,1,1,1}; 
		 for(int i=0;i<moremoves.length;i++)
		 {
			 hero.move(moremoves[i], m);
		 }
		 
		 assertEquals(true,hero.isNearDragon(dragon));
		 assertEquals(false,dragon.isAlive());
	}
	
	/**
	 * Tests if the hero can go to the exit position and win the game after he gets the
	 * sword and kills the dragon.
	 */
	@Test
	public void winGame()
	{
		assertEquals('S',m.getPosElem(m.getExitPos()));
		 int[] moves = {4,4,4,3,3,3,3,2,2,2,3,3,}; 
		 
		 for(int i=0;i<moves.length;i++)
		 {
			 hero.move(moves[i], m);
		 }
		 assertEquals('S',m.getPosElem(m.getExitPos()));
		 hero.move(3, m);
		 assertEquals('S',m.getPosElem(m.getExitPos()));
		 assertEquals(true,hero.pickedUpSword(sword));
		 assertEquals(true,hero.hasSword());
		 //saida continua com S, nao fica espaço em branco dps de apanhar a espada 	
		assertEquals (5, m.getExitPos()[0]);
		assertEquals (9, m.getExitPos()[1]);
		 m.setPosElem(m.getExitPos(), ' ');
		 assertEquals(' ',m.getPosElem(saida));
		 
		 int[] moremoves = {1,1,1,1,1}; 
		 for(int i=0;i<moremoves.length;i++)
		 {
			 hero.move(moremoves[i], m);
		 }
		 
		 assertEquals(true,hero.isNearDragon(dragon));
		 assertEquals(false,dragon.isAlive());
		 
		 int[] more_moves = {1,1,4,4,4,4,4,4,4,3,3,3,3}; 
		 for(int i=0;i<more_moves.length;i++)
		 {
			 hero.move(more_moves[i], m);
		 }
		 
		 assertEquals(8, hero.getHPos());
		 assertEquals(5, hero.getWPos());
		 
		 hero.move(4, m);
		 
		 
		 assertEquals(true,hero.isAlive());
	}
	
	/**
	 * Tests if the hero can't go to the exit position if he doesn't have the sword-
	 */
	@Test
	public void cantWinGame()
	{
		 int[] moves = {4,4,4,4,4,4,4,3,3,3,3,4}; 
		 
		 for(int i=0;i<moves.length;i++)
		 {
			 hero.move(moves[i], m);
		 }
		 
		 assertEquals(8, hero.getHPos());
		 assertEquals(5, hero.getWPos());
		 assertEquals(false,hero.hasSword());
		 assertEquals('S', m.getMaze()[5][9] );
		 
		 hero.move(4, m);
		 
		 assertEquals(8, hero.getHPos());
		 assertEquals(5, hero.getWPos());
	}
}
