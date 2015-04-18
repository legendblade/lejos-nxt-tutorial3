/*

    Author(s): Joe, Marc
    Assignment: Wall Follower
    Date: 4/6/2015
    Description:  Follows a wall.

    WallFollower Template

    The zip file contains a Java program for you to use as a template for your program

*/

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Stopwatch;

public class WallFollower implements ButtonListener // Make sure the classname matches the filename (case-sensitive)
{
    private DifferentialPilot bot; // Field declaration for a Pilot object
	private boolean isRunning = true;

    //the code in the main method will not change (except for the classname)
    public static void main(String[] args)
    {

        WallFollower program = new WallFollower(); // Matches classname (case-sensitive)
        Button.ESCAPE.addButtonListener(program); // Register this object as a listener
        program.run();

    } // main()

    /*

        The constructor generally instantiates field objects and
        sets other initial state information

    */
    public WallFollower()   //matches classname (case-sensitive)
    {

        bot = new DifferentialPilot(56, 108, Motor.B, Motor.C); // Instantiate a Pilot object named "bot"
		bot.setRotateSpeed(250);
		bot.setTravelSpeed(250);
    } //constructor

    /*

        Your code goes in this method

    */
    public void run()
    {

        // Create a TouchSensor that monitors sensor port S1
        TouchSensor touch = new TouchSensor(SensorPort.S1);

        // Create a Stopwatch that monitors elapsed time in milliseconds
        Stopwatch sw = new Stopwatch();

        long time_in_milliseconds = 10000L;
		int distIncrement = getTravelDistanceFromInches(5);
		double angleIncrement = getCalibratedAngle(10);

		// Since we're going backwards, technically:
		distIncrement *= -1;

		pause(500);

        // Reset the stop watch to 0, before entering the loop
        sw.reset();

        while ( sw.elapsed() < time_in_milliseconds && isRunning )
        {
			bot.backward();

            if (touch.isPressed())
            {
				bot.stop();

				// Reverse and slightly rotate
				bot.travel(0-distIncrement);
				bot.rotate(angleIncrement);

				// If we tapped the wall, reset our timer:
				sw.reset();
			}
        }
    }

    /*

        The following method allows the bot to do what it was doing,
        but suspends execution of the next statement.

        When this method returns, the program continues executing
        where it left off.

    */
    public void pause(int milli)
    {

        try
        {
            Thread.sleep(milli);
        }

        catch(InterruptedException e)
        {

        }

    }

    /*

        This method is required when a class is a ButtonListener
        it responds to the action of the button being pressed.

    */
    public void buttonPressed(Button b)
    {
        isRunning = false;
        bot.stop();
    }

    /*

        This is also required, but we aren't using it, so it is empty.

    */
    public void buttonReleased(Button b)
    {

        // Empty method

    }
	
	public double getCalibratedAngle(double angle)
	{
		// Adjust according to calibration
		return angle * 1.25;
	}

	public int getTravelDistanceFromInches(int inches)
	{
		// 300 = 11.5 inches
		// ~26 = 1 inch
		return inches * 26;
	}
}