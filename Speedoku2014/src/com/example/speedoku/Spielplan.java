package com.example.speedoku;

import java.util.EventListener;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
//import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodSession.EventCallback;
import android.widget.Button;
import android.widget.TextView;

public class Spielplan extends Activity implements OnClickListener{
	
//  Timer
  private int nr = 300;
  private TextView textfield;
  private Handler handler;
  private boolean runbl = true;
  private static final String TAG = "Speedoku";

  public int zahl=0;
  private final PuzzleView pv = null;
  private Game game = new Game();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.spiel);
	  
//	  pv = new PuzzleView(this, null);
	  
//	  setContentView(R.id.sudokuview);
//	   R.id.sudokuview
	  
	  //TODO
	  Button button1 = (Button) findViewById(R.id.button1);
	  button1.setOnClickListener(this); 
	  
	  Button button2 = (Button) findViewById(R.id.button2);
	  button2.setOnClickListener(this); 
	  
	  Button button3 = (Button) findViewById(R.id.button3);
	  button3.setOnClickListener(this); 
	  
	  Button button4 = (Button) findViewById(R.id.button4);
	  button4.setOnClickListener(this); 
	  
	  Button button5 = (Button) findViewById(R.id.button5);
	  button5.setOnClickListener(this); 
	  
	  Button button6 = (Button) findViewById(R.id.button6);
	  button6.setOnClickListener(this); 
	  
	  Button button7 = (Button) findViewById(R.id.button7);
	  button7.setOnClickListener(this); 
	  
	  Button button8 = (Button) findViewById(R.id.button8);
	  button8.setOnClickListener(this); 
	  
	  Button button9 = (Button) findViewById(R.id.button9);
	  button9.setOnClickListener(this); 
		
//	  button1.setOnClickListener(new View.OnClickListener() {
//		       public void onClick(View v) {
//		           // Perform action on click
////		    	   pv.setSelectedTile(3);
////		    	   pv.onTouchEvent(MotionEvent event);
//		    	  
//		    	   pv.setOnTouchListener(new OnTouchListener() {
//					
//					@Override
//					public boolean onTouch(View v, MotionEvent event) {
//						
//					      pv.select((int) (event.getX() / pv.getWidth()),
//					              (int) (event.getY() / pv.getHeight()));
//					        
//					        //TODO kommt vielleicht raus
//					        
//					        Log.d(TAG+" Testlauf", "Spielpnan onTouchEvent: x " + pv.getAuswahlx() + ", y " + pv.getAuswahly());
//						return true;
//					}
//				});
//		    	   
//		    	   Log.d(TAG, "Button 1");
//		       }
//	  });	
	  
	  textfield = (TextView) findViewById(R.id.timer);
      handler = new Handler();
      Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			while (runbl) {
				try{

				Thread.sleep(1000);

				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						if(nr==0){
							finish();
						}
						else{
						nr --;
						if(nr<=10){
							textfield.setTextColor(Color.parseColor("#FF0000"));
						}
						else{
							textfield.setTextColor(Color.parseColor("#00FF00"));
						}
						textfield.setText("Zeit: "+String.valueOf(nr));
						}
						
					}
				});
			
			}
		}
	};
	
	new Thread(runnable).start();
	  
	  Log.d("Speedoku", "clicked on ");
//      Intent intent = new Intent(this, Game.class);
//      intent.putExtra(Game.KEY_DIFFICULTY, true);
//      startActivity(intent);

	}
	
	public void setZahl(int z){
		zahl = z;
	}
	
	public int getZahl(){
		return zahl;
	}
	
	
	@Override
	public void onClick(View v) {
		
	      switch (v.getId()) {
	         
	      case R.id.button1:
	    	  Log.d(TAG, "Button 1 betätigt");
//	    	  pv.setSelectedTile(1);
//	    	  game.setTileIfValid(3, 3, 4);
	    	  setZahl(1);
	    	  Log.d(TAG, "Button 1 betätigt und die Zahl ist: "+getZahl());
	         break;
	      case R.id.button2:
	    	  Log.d(TAG, "Button 2 betätigt");
//	    	  pv.setSelectedTile(2);
	    	  break;
	      case R.id.button3:
	    	  Log.d(TAG, "Button 3 betätigt");
//	    	  pv.setSelectedTile(3);
	         break;
	      case R.id.button4:
	    	  Log.d(TAG, "Button 4 betätigt");
//	    	  pv.setSelectedTile(4);
	    	  break;
	      case R.id.button5:
	    	  Log.d(TAG, "Button 5 betätigt");
//	    	  pv.setSelectedTile(5);
	    	  break;
	      case R.id.button6:
	    	  Log.d(TAG, "Button 6 betätigt");
//	    	  pv.setSelectedTile(6);
	    	  break;
	      case R.id.button7:
	    	  Log.d(TAG, "Button 7 betätigt");
//	    	  pv.setSelectedTile(7);
	    	  break;
	      case R.id.button8:
	    	  Log.d(TAG, "Button 8 betätigt");
//	    	  pv.setSelectedTile(8);
	    	  break;
	      case R.id.button9:
	    	  Log.d(TAG, "Button 9 betätigt");
//	    	  pv.setSelectedTile(9);
	    	  break;
	      }	
	}

}