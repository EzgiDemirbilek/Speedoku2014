package com.example.speedoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Spielplan extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.spiel);
	  
	  //View spielview = findViewById(R.id.sudokuview);
	  
	  //setContentView(R.id.sudokuview);
	  // R.id.sudokuview
	  Log.d("Speedoku", "clicked on ");
      Intent intent = new Intent(this, Game.class);
      intent.putExtra(Game.KEY_DIFFICULTY, true);
      startActivity(intent);

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
