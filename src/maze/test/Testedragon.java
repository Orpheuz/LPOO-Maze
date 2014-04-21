
	package maze.test;


	import maze.logic.*;
import static org.junit.Assert.*;

import org.junit.Test;

	 /**
	 * The Class Testedragon. This class tests the dragons.
	 * @author Vítor Teixeira and David Azevedo
	 * @version 1.0
	 */
	public class Testedragon {

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
		
		private int[] Dpos = {3,1};
		private Dragon dragon1 = new Dragon(Dpos);
		private Maze m = new Maze();
		private	int[] D2pos = {8,4};
		private Dragon dragon2 = new Dragon(D2pos);
		
		
		/**
		 * Tests if the dragon will move to a determined position once prompted to do so.
		 */
		@Test
		public void moveRandomDragon()
		{
				 dragon1.setNoSleep(true);
				 dragon1.move(3, m);
				 dragon1.move(3, m);
				 assertEquals(5,dragon1.getWPos());
				 assertEquals(1,dragon1.getHPos());
				 
				 int[] moves = {4,4,4,1,1,1,1}; 
				 
				 for(int i=0;i<moves.length;i++)
				 {
					 dragon1.move(moves[i], m);
				 }
				 
				 assertEquals(1,dragon1.getWPos());
				 assertEquals(4,dragon1.getHPos());
		}	
		
		
		/**
		 * Tests if the dragon falls asleep, not moving for a set ammount of moves.
		 */
		@Test
		public void sleepDragon()
		{	
			dragon1.setNoSleep(true);
			dragon1.move(3, m);
			dragon1.move(3, m);
			dragon1.move(4, m);
			dragon1.setNoSleep(false);
			assertEquals(5,dragon1.getWPos());
			assertEquals(2,dragon1.getHPos());
			dragon1.setSleeping(3);
			dragon1.move(4, m);
			assertEquals(5,dragon1.getWPos());
			assertEquals(2,dragon1.getHPos());
			dragon1.move(4, m);
			assertEquals(5,dragon1.getWPos());
			assertEquals(2,dragon1.getHPos());
			assertEquals(true, dragon1.isSleeping());
		}
		

		/**
		 * Tests if multiple dragons can exist, move and fall asleep simultaneously. 
		 */
		@Test
		public void multipleDragons()
		{
			 dragon1.setNoSleep(true);
			 dragon1.move(3, m);
			 dragon1.move(3, m);
			 assertEquals(5,dragon1.getWPos());
			 assertEquals(1,dragon1.getHPos());
			 
			 int[] moves = {4,4,4,1,1,1,1}; 
			 
			 for(int i=0;i<moves.length;i++)
			 {
				 dragon1.move(moves[i], m);
			 }
			 
			 assertEquals(1,dragon1.getWPos());
			 assertEquals(4,dragon1.getHPos());
			 
			 dragon2.setNoSleep(true);
             int[] moves2 = {4,4,4,4,1,1,1}; 
			 
			 for(int i=0;i<moves2.length;i++)
			 {
				 dragon2.move(moves2[i], m);
			 }
			
			 assertEquals(5,dragon2.getWPos());
			 assertEquals(8,dragon2.getHPos());
			
			dragon1.setSleeping(3);
			dragon1.move(1, m);
			dragon2.move(1, m);
			
			 assertEquals(1,dragon1.getWPos());
			 assertEquals(4,dragon1.getHPos());
			 assertEquals(true, dragon1.isSleeping());
			 assertEquals(4,dragon2.getWPos());
			 assertEquals(8,dragon2.getHPos());
			
		}
		
		
		
		}

