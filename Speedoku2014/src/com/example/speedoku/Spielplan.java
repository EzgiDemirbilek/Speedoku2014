package com.example.speedoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Spielplan extends Activity implements OnClickListener {
	

//  Timer
  private int nr = 80;
  private TextView textfield;
  private Handler handler;
  private boolean runbl = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.spiel);
	  
	  	  
	  //setContentView(R.id.sudokuview);
	  // R.id.sudokuview
	  
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	

}
