package com.example.speedoku;

import java.util.Random;

import com.example.speedoku.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Sudoku extends Activity implements OnClickListener {
   private static final String TAG = "Speedoku";

   int next = 1;
   
   // Main Methode von Android
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      // Alle Buttons werden hinzugefügt
      View neuButton = findViewById(R.id.new_button);
      neuButton.setOnClickListener(this);
      View ueberUnsButton = findViewById(R.id.about_button);
      ueberUnsButton.setOnClickListener(this);
      View exitButton = findViewById(R.id.statistik_button);
      exitButton.setOnClickListener(this);
   }
   
//   public void setNext(int i){
//	   next = i;
//   }
//   
//   public int getNext(){
//	   return next;
//   }
   
   public void onClick(View v) {
      switch (v.getId()) {
         
      case R.id.about_button:
         Intent i = new Intent(this, About.class);
         startActivity(i);
         break;
     
      case R.id.new_button:
    	 Intent spielI = new Intent(this, Spielplan.class);
//     	 startSpiel();
     	 //neuesSpiel();
     	 startActivity(spielI); 
         break;
         
      case R.id.statistik_button:
     	 Intent statistik = new Intent(this, Statistiken.class);
         startActivity(statistik); 
    	  break;
         
      }
   }
   
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.settings:
         startActivity(new Intent(this, Prefs.class));
         return true;
      
      }
      return false;
   }

   //Schwierigkeitsgrad auswählen
   private void neuesSpiel() {
      new AlertDialog.Builder(this)
           .setTitle(R.string.new_game_title)
           .setItems(R.array.difficulty,
            new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialoginterface,
                     int i) {
                  startSpiel();
               }
            })
           .show();
   }

   //neues Spiel starten, nachdem man eine Schwierigkeit
   private void startSpiel() {
  	  Random zufall = new Random();
 	  next = zufall.nextInt(2)+1;
      Log.d(TAG, "Zufallskarte " + next);
//      Intent intent = new Intent(Sudoku.this, Game.class);
//      intent.putExtra(Game.KEY_DIFFICULTY, i);
//      startActivity(intent);
      
   }
}