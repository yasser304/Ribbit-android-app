package com.ysr.ribbit;

import android.app.Application;

import com.parse.Parse;

public class RibbitApplication extends Application{
	public void onCreate() {
		  Parse.initialize(this, "f9exsBFk1hxkVWkckyr75sMMXvhmRa0iiOWDLVZw", "rNdg3zEFKqQpPaKaxupuy306p5QigmtMZYCbTPyY");
		  
		}
}
