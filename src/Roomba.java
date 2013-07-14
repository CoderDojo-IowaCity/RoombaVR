import javax.swing.JFrame;


public class Roomba {
	private MyApplet applet;
	public Roomba(){
		applet = new MyApplet();
	    JFrame frame = new JFrame("Applet1c");
	    // To close the application:
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(applet);
	    frame.setSize(500,500);
	    applet.init();
	    applet.start();
	    frame.setVisible(true);
	}
	public void forwardForTime( double speed, double time ){
		//applet.roombaSpeedX = (int)(speed*10);
		this.applet.roombaSpeedX = (int) ((Math.cos(this.applet.roombaDirection * Math.PI / 180) * speed * 10)); 
		this.applet.roombaSpeedY = (int) ((Math.sin(this.applet.roombaDirection * Math.PI / 180) * speed * 10)); 
		
//		System.out.println("Math.sin("+(this.applet.roombaDirection * Math.PI / 180)+ ") = " + (Math.sin(this.applet.roombaDirection * Math.PI / 180)));
//		
//		System.out.println("setting speedx to:" + applet.roombaSpeedX);
//		System.out.println("setting speedy to:" + applet.roombaSpeedY);
		this.applet.runForTime(time);
	}
	public void drive( double time){
		this.forwardForTime(1, time);
	}
	public void turn(int deg){
		this.applet.roombaDirection = ((this.applet.roombaDirection + deg) % 360);
		System.out.println("Changing deg to "+this.applet.roombaDirection);
	}
	public void forwardSpeed ( double speed){
		this.applet.roombaSpeedX = (int) (Math.cos(this.applet.roombaDirection * Math.PI / 180) * speed * 10); 
		this.applet.roombaSpeedY = (int) (Math.sin(this.applet.roombaDirection * Math.PI / 180) * speed * 10); 
	}
	public void waitForBump(){
		this.applet.waitingForBump = true;
		this.applet.runForTime(1000);
	}
	public void moveTo(int x, int y){
		this.applet.roombaPosX = x;
		this.applet.roombaPosY = y;
	}
}
