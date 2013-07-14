
public class Driver {

	public static void main(String[] args){
		Roomba myRoomba = new Roomba();
		
		myRoomba.moveTo(50, 50);
		
		myRoomba.forwardForTime(.4,1);
		myRoomba.turn(90);
		myRoomba.forwardForTime(.2,1);
		myRoomba.turn(270);
		
		myRoomba.forwardSpeed(1);
		myRoomba.waitForBump();
		
		myRoomba.turn(180);
		myRoomba.forwardSpeed(1);
		myRoomba.waitForBump();
	}
}
