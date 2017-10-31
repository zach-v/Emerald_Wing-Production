package com.game.engine;

public class GameLoop implements Runnable{

	private final Game game;
	private final GameComponents components;
	private final GameWindow window;
	private final int framesPerSecond = 120;
	
	public GameLoop(Game game){
		this.game = game;
		
		game.setFrameRate(framesPerSecond);
		
		window = new GameWindow(game);
		components = window.getComponents();
	}
	
	// This is the heart of the game loop
	/* This implements Runnable which is a Java class
	 * that looks at run() and continuously runs it
	 */
	
	@Override
	public void run() {
		
		long timer = System.currentTimeMillis();
		int ups = 0, fps = 0;
		
		long lastTime = System.nanoTime();
		long currentTime;
		double nextUpdate = 0;
		
		final double frameToRate = 1000000000.0 / framesPerSecond;
		
		// Looking for the game to be declared "over"
		while(!game.gameOver){
			
			// Looking for the game to be declared "paused"
			// so the game can still run
			while(!game.paused){
				
				currentTime = System.nanoTime();
				
				// This will limit the game to only updating every 120 frames per second
				nextUpdate += ((currentTime - lastTime) / frameToRate);
				
				lastTime = currentTime;
				
				if(nextUpdate >= 1){
					ups++;
					fps++;
					nextUpdate--;
					game.update();
					components.repaint();
				}
				
				if((System.currentTimeMillis()-timer) >= 1000){
					timer = System.currentTimeMillis();
					System.out.println("Ups = " + ups);
					System.out.println("Fps = " + fps + "\n");
					
					fps = 0;
					ups = 0;
				}
			}
		}
		
	}
}
