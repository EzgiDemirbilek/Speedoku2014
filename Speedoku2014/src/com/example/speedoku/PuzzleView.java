package com.example.speedoku;

import java.util.Random;

import com.example.speedoku.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;


public class PuzzleView extends View{
   
   private static final String TAG = "Speedoku";

   private View v;
   private static final String AUSWAHLX = "auswahlx"; 
   private static final String AUSWAHLY = "auswahly";
   private static final String VIEW_STATE = "viewState";
   private static final int ID = 42; 

   public Canvas c;
   
   private float width;    //Titelbreite 
   private float height;   // Titelhöhe
   private int auswahlx;       // X Index
   private int auswahly;       // Y Index
   private final Rect selRect = new Rect();
//   public final int used[][][] = new int[9][9][];
   private Game game = new Game();
   private Spielplan sp = new Spielplan();
   
   private int zufall = 0;
   
   public PuzzleView(Context context, AttributeSet attr) {
      
      super(context,attr);
      
//      if(!isInEditMode()){
//      this.game = (Game) context;     
//      }
      
      init(game);
      setFocusable(true);
      setFocusableInTouchMode(true);
   	 
//    Zufallszahl setzen
      setZufallsZahl();
      game.zufallSudoku(getZufallsZahl());
      game.setPuzzle();
      Log.d(TAG,"Spiel zufall Sudoku in der Klasse PuzzleView "+game.getZufallSudoku());
      
      setId(ID); 
   }
   
   private void init(Context context) {
	    //do stuff that was in your original constructor...
	      if(!isInEditMode()){
	        
	        this.game = (Game) context;
	      }
	}

   @Override
   protected Parcelable onSaveInstanceState() { 
      Parcelable p = super.onSaveInstanceState();
      Log.d(TAG, "onSaveInstanceState");
      Bundle bundle = new Bundle();
      bundle.putInt(AUSWAHLX, auswahlx);
      bundle.putInt(AUSWAHLY, auswahly);
      bundle.putParcelable(VIEW_STATE, p);
      return bundle;
   }
   @Override
   protected void onRestoreInstanceState(Parcelable state) { 
      Log.d(TAG, "onRestoreInstanceState");
      Bundle bundle = (Bundle) state;
      select(bundle.getInt(AUSWAHLX), bundle.getInt(AUSWAHLY));
      super.onRestoreInstanceState(bundle.getParcelable(VIEW_STATE));
      return;
   }
   

   @Override
   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	   //TODO 9f
      width = (w / 10f)+4;
      height = (h / 10f)+4;
      getRect(auswahlx, auswahly, selRect);
      Log.d(TAG, "onSizeChanged: width " + width + ", height "
            + height);
      Log.d(TAG, "w: "+w+" h: "+h+" oldw: "+oldw+" oldh: "+oldh);
      super.onSizeChanged(w, h, oldw, oldh);
   }
   
   public Canvas getOnDraw(){
	   return c;
   }
   
   public void setZufallsZahl(){
	   Random r = new Random();
	   zufall = r.nextInt(2)+1;
   }
   
   public int getZufallsZahl(){
	   return zufall;
   }

   @Override
   protected void onDraw(Canvas canvas) {
      
	  
	  // Hintergrund erstellen

      Paint background = new Paint();
      background.setColor(getResources().getColor(
            R.color.puzzle_background));
      canvas.drawRect(0, 0, getWidth(), getHeight(), background);
      
      
      // Draw the board...
      
      // Gitter Farben festlegen
      Paint dark = new Paint();
      dark.setColor(getResources().getColor(R.color.puzzle_dark));

      Paint hilite = new Paint();
      hilite.setColor(getResources().getColor(R.color.puzzle_hilite));

      Paint light = new Paint();
      light.setColor(getResources().getColor(R.color.puzzle_light));

      //TODO Gitter zeichnen
      for (int i = 0; i < 9; i++) {
         canvas.drawLine(0, i * height, getWidth(), i * height,
               light);
         canvas.drawLine(0, i * height + 1, getWidth(), i * height
               + 1, hilite);
         canvas.drawLine(i * width, 0, i * width, getHeight(),
               light);
         canvas.drawLine(i * width + 1, 0, i * width + 1,
               getHeight(), hilite);
      }

      // Hauptgitterachsen zeichnen
      for (int i = 0; i < 9; i++) {
         if (i % 3 != 0)
            continue;
         canvas.drawLine(0, i * height, getWidth(), i * height,
               dark);
         canvas.drawLine(0, i * height + 1, getWidth(), i * height
               + 1, hilite);
         canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
         canvas.drawLine(i * width + 1, 0, i * width + 1,
               getHeight(), hilite);
      }
      
      // Zahlendesign festlegen
      Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
      foreground.setColor(getResources().getColor(
            R.color.puzzle_foreground));
      foreground.setStyle(Style.FILL);
      //TODO 0.75f Zahlgröße
      foreground.setTextSize(height * 0.4f);
      foreground.setTextScaleX(width / height);
      foreground.setTextAlign(Paint.Align.CENTER);

      // Zahl in die Mitte der Kachel einfügen
      FontMetrics fm = foreground.getFontMetrics();
      // X zentrieren
      float x = width / 2;
      // Y zentrieren: measure ascent/descent first TODO nocheinmal anschauen
      float y = height / 2 - (fm.ascent + fm.descent) / 2;

//      Zufälliges sudoku wird reingeschriebens
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            canvas.drawText(this.game.getTileString(i, j), i * width + x, j * height + y, foreground);

         }
      }

      
//      if (Prefs.getHints(getContext())) {
//         // Draw the hints...
//         
//         // Pick a hint color based on #moves left TODO kommt vielleicht raus
//         Paint hint = new Paint();
//         int c[] = { getResources().getColor(R.color.puzzle_hint_0),
//               getResources().getColor(R.color.puzzle_hint_1),
//               getResources().getColor(R.color.puzzle_hint_2), };
//         Rect r = new Rect();
//         for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//               int movesleft = 9 - game.getUsedTiles(i, j).length;
//               if (movesleft < c.length) {
//                  getRect(i, j, r);
//                  hint.setColor(c[movesleft]);
//                  canvas.drawRect(r, hint);
//               }
//            }
//         }
//         
//      }
      
      Log.d(TAG, "Canvwas Breite: "+canvas.getWidth()+" Canvas höhe: "+canvas.getHeight());

      // Auswahl zeichnen
//      Log.d(TAG, "selRect=" + selRect);
      Paint selected = new Paint();
      selected.setColor(getResources().getColor(
            R.color.puzzle_selected));
      
      canvas.drawRect(selRect, selected);
      
//      Log.d(TAG, "selRect: "+selRect +" getRect: "+getRect(4, 3, selRect));

//      canvas.drawText("1", getRect(), rect);, y, paint);f
//      canvas.drawText("9", getRect().centerX(), getRect().centerY(), selected); 
      c = canvas;
   }
   
//TODO bei onTouchEvent
   @Override
   public boolean onTouchEvent(MotionEvent event) {
      if (event.getAction() != MotionEvent.ACTION_DOWN)
         return super.onTouchEvent(event);

      select((int) (event.getX() / width),
            (int) (event.getY() / height));
      
      //TODO kommt vielleicht raus
      
      Log.d(TAG+" Testlauf", "onTouchEvent: x " + auswahlx + ", y " + auswahly);

      Log.d(TAG, "Spielplan Zahl: "+sp.getZahl());
      int zahl = sp.getZahl();
      game.setTile(auswahlx, auswahly, zahl);

      return true;
   }

   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
      Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event="
            + event);
      switch (keyCode) {
      case KeyEvent.KEYCODE_DPAD_UP:
         select(auswahlx, auswahly - 1);
         break;
      case KeyEvent.KEYCODE_DPAD_DOWN:
         select(auswahlx, auswahly + 1);
         break;
      case KeyEvent.KEYCODE_DPAD_LEFT:
         select(auswahlx - 1, auswahly);
         break;
      case KeyEvent.KEYCODE_DPAD_RIGHT:
         select(auswahlx + 1, auswahly);
         break;
      case KeyEvent.KEYCODE_0:
      case KeyEvent.KEYCODE_SPACE: setSelectedTile(0); break;
      case KeyEvent.KEYCODE_1:     setSelectedTile(1); break;
      case KeyEvent.KEYCODE_2:     setSelectedTile(2); break;
      case KeyEvent.KEYCODE_3:     setSelectedTile(3); break;
      case KeyEvent.KEYCODE_4:     setSelectedTile(4); break;
      case KeyEvent.KEYCODE_5:     setSelectedTile(5); break;
      case KeyEvent.KEYCODE_6:     setSelectedTile(6); break;
      case KeyEvent.KEYCODE_7:     setSelectedTile(7); break;
      case KeyEvent.KEYCODE_8:     setSelectedTile(8); break;
      case KeyEvent.KEYCODE_9:     setSelectedTile(9); break;
      case KeyEvent.KEYCODE_ENTER:
      case KeyEvent.KEYCODE_DPAD_CENTER:
//    	 TODO kommt vielleich raus
         game.showKeypadOrError(auswahlx, auswahly);
         break;
      default:
         return super.onKeyDown(keyCode, event);
      }
      return true;
   }

   public void setSelectedTile(int tile) {
      if (game.setTileIfValid(auswahlx, auswahly, tile)) {
    	  Log.d(TAG, "Auswahl X: "+auswahlx +", Auswahl Y: "+auswahly +", Tile: " + tile);
         invalidate();// Spur abändern...
      } else {
         // Zahl ist für die Kachel ungültig
         Log.d(TAG, "setSelectedTile: invalid: " + tile);
         startAnimation(AnimationUtils.loadAnimation(game,
               R.anim.shake));
      }
   }

   public void select(int x, int y) {
      invalidate(selRect);
      auswahlx = Math.min(Math.max(x, 0), 8);
      auswahly = Math.min(Math.max(y, 0), 8);
      getRect(auswahlx, auswahly, selRect);
      invalidate(selRect);
   }

   public Rect rect;
   private void getRect(int x, int y, Rect rect) {
     rect.set((int) (x * width), (int) (y * height), (int) (x
            * width + width), (int) (y * height + height));
     this.rect = rect;
      Log.d(TAG, "getRect: x und y "+x+" "+y+" Rect: "+rect);
   }
   
   public Rect getRect(){
	   return rect;
   }

public int getAuswahlx() {
	return auswahlx;
}

public void setAuswahlx(int auswahlx) {
	this.auswahlx = auswahlx;
}

public int getAuswahly() {
	return auswahly;
}

public void setAuswahly(int auswahly) {
	this.auswahly = auswahly;
}
   
}

