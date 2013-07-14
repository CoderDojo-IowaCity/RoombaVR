import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class MyApplet extends Applet implements Runnable, KeyListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//roomba data
	public int roombaPosX, roombaPosY;
	public int roombaSpeedX, roombaSpeedY;
	public int oldRoombaPosX, oldRoombaPosY;
	public int roombaDirection = 0;
	boolean roombaMoving = false;
	public boolean waitingForBump = false;
	public long endTime = -1;
	
	ArrayList<Rectangle> myWalls;
	

	public void init()
	{
		
    	Color grasscolor = new Color( 60, 170, 50);
		setBackground(grasscolor );
		roombaPosX = 0;
		roombaPosY = 50;
		this.setSize(500,500);
		addKeyListener( this );
	    addMouseListener( this );
	    Thread move = new Thread(this);
        move.start();
        
        //create starting wall
        myWalls = new <Rectangle>ArrayList();
        Rectangle myWall = new Rectangle(200,100, 25, 200);
        myWalls.add(myWall);
	}
	public void paint( Graphics g ) {
		this.checkSides();
		Graphics bufferGraphics; 
		Image offscreen; 
		offscreen = createImage(this.getWidth(),this.getWidth());
	    bufferGraphics = offscreen.getGraphics(); 
		
	    bufferGraphics.setColor(this.getBackground());
	    bufferGraphics.fillOval(oldRoombaPosX-2, oldRoombaPosY-2, 55, 55);
	    bufferGraphics.setColor(Color.gray);
	    bufferGraphics.fillOval(roombaPosX, roombaPosY, 50, 50);
	    bufferGraphics.setColor(Color.red);
	    
	    for(int i = 0; i < myWalls.size(); i++)
	    {
	    	Rectangle myWall = myWalls.get(i);
		    bufferGraphics.setColor(Color.black);
		    bufferGraphics.fillRect(myWall.x, myWall.y, myWall.width, myWall.height);
	    }
	    
	    int vectorX = (int)( Math.cos(this.roombaDirection * Math.PI / 180) * 25 );
	    //System.out.println("Cos is: " + Math.cos(this.roombaDirection * Math.PI / 180) );
	    //System.out.println("vectorX is:" + vectorX);
	    int vectorY = (int)( Math.sin(this.roombaDirection * Math.PI / 180) * 25 );
	    //System.out.println("sin is: " + Math.sin(this.roombaDirection * Math.PI / 180) );
	    //System.out.println("vectorY is:" + vectorY);
	    bufferGraphics.drawLine(roombaPosX+25, roombaPosY+25, roombaPosX+25+ vectorX, roombaPosY+25 + vectorY);
		

		g.drawImage(offscreen,0,0,this);
	}
	public void update(Graphics g){

	   paint(g);

	}
	
	public void runForTime(double time){
		
		endTime = System.currentTimeMillis() + (long)time * 1000;
		
		// some debug code to make our current thread wait, so that the next command doesn't
		// stack up ontop of our previous command
		int counter = 0;
		while(System.currentTimeMillis() <= endTime)
		{
			//System.out.println("sleeping for " + .1*counter + "seconds");
			counter++;
			try{
				Thread.sleep(100);
			}
			catch(Exception e)
			{
				System.out.println("Error! not sleeping in runForTime!");
			}
		}
	}
	
	public void run() {
		while(true)
		{
			//System.out.println("running with curtime: " + System.currentTimeMillis() + "\n and end time " + this.endTime);
			if(System.currentTimeMillis() <= endTime)
			{
				//System.out.println("trying to operate");
				
			roombaMoving = true;
			oldRoombaPosX = roombaPosX;
			oldRoombaPosY = roombaPosY;
			
			this.roombaPosX = this.roombaPosX + this.roombaSpeedX;
			this.roombaPosY = this.roombaPosY + this.roombaSpeedY;
			this.checkSides();
			this.checkWalls();
			

			}
			else
			{
				this.roombaMoving = false;
			}
			
			this.repaint();
			try
			{
				 Thread.sleep(50);
			}
			catch(Exception e){}

		}
	}  


private void checkSides(){
	if (this.roombaPosX >= this.getWidth() - 50){
		this.roombaPosX = this.oldRoombaPosX;
		this.oldRoombaPosX = this.roombaPosX;
		System.out.println("bump on right");
		this.endTime = 0;
	}
	if (this.roombaPosX <= -1){
		this.roombaPosX = this.oldRoombaPosX;
		this.oldRoombaPosX = this.roombaPosX;
		System.out.println("bump on left");
		this.endTime = 0;
	}
	if(this.roombaPosY <= -1){
		this.roombaPosY = this.oldRoombaPosY;
		this.oldRoombaPosY = this.roombaPosY;
		System.out.println("Bump on top");
		this.endTime = 0;
	}
	if(this.roombaPosY >= this.getHeight() - 50){
		this.roombaPosY = this.oldRoombaPosY;
		this.oldRoombaPosY = this.roombaPosY;
		System.out.println("Bump on bottom");
		this.endTime = 0;
	}
}
private void checkWalls(){
	for(int i = 0; i < myWalls.size(); i++)
    {
    	Rectangle myWall = myWalls.get(i);
    	
    	Rectangle Roomba = new Rectangle(this.roombaPosX, this.roombaPosY, 50,50);
    	
    	if (myWall.intersects(Roomba)){
    		this.roombaPosY = this.oldRoombaPosY;
    		this.oldRoombaPosY = this.roombaPosY;
    		this.roombaPosX = this.oldRoombaPosX;
    		this.oldRoombaPosX = this.roombaPosX;
    		System.out.println("Bump on wall");
    		this.endTime = 0;
    	}
    }
}
		   
		   

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		 char c = e.getKeyChar();
		 this.oldRoombaPosX = this.roombaPosX;
		 this.oldRoombaPosY = this.roombaPosY;
		 if( e.equals( KeyEvent.VK_UP ) || c == 'w')
		 {
			 //System.out.println("up pressed");
			 this.roombaPosY -= 5;
		 }
		 if( e.equals( KeyEvent.VK_DOWN ) || c == 's')
		 {
			 //System.out.println("down pressed");

			 this.roombaPosY += 5;
		 }
		 if( e.equals( KeyEvent.VK_LEFT ) || c == 'a')
		 {
			 //System.out.println("left pressed");

			 this.roombaPosX -= 5;
		 }
		 if( e.equals( KeyEvent.VK_RIGHT ) || c == 'd')
		 {
			 //System.out.println("right pressed");
			 this.roombaPosX += 5;
		 }
		 this.repaint();
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
	
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
