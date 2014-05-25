package com.example.speedoku;

import java.util.Random;

import android.app.Activity;

import com.example.speedoku.R;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Game extends Activity {
	private static final String TAG = "Speedoku";

	public static final String KEY_DIFFICULTY = "com.example.speedoku.difficulty";

	private static final String PUZZLE = "puzzle";

	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;

	protected static final int DIFFICULTY_CONTINUE = -1;

	private int puzzle[] = new int[9 * 9];

	private final String easyPuzzle = "360000000004230800000004200"
			+ "070460003820000014500013020" + "001900000007048300000000045";

	private final String testPuzzle = "400010000001875030050020"
			+ "1070000019702057006031" + "7940000096008034003010"
			+ "6500004900006";

	// private final String mediumPuzzle =
	// "650000070000506000014000005" +
	// "007009000002314700000700800" +
	// "500000630000201000030000097";
	// private final String hardPuzzle =
	// "009000000080605020501078000" +
	// "000000700706040102004000000" +
	// "000720903090301080000000600";

	private String zufallPuzzle;
	// private int zufallsZahl;
	private PuzzleView puzzleView;
	// private final int used[][][] = new int[9][9][];

	// private Button cbutton;

	// ImageView spielview = (ImageView) findViewById(R.id.sudokuview);
	// LinearLayout ll = (LinearLayout) findViewById(R.id.ll_spiel);
	// mBoard = (SudokuBoardView) findViewById(R.id.sudoku_board);

	public final int used[][][] = new int[9][9][];

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		// int diff = getIntent().getIntExtra(KEY_DIFFICULTY,
		// DIFFICULTY_EASY);
		
//		TODO puzzle erhält nicht die Array aus fromPuzzleString
//		puzzleView.setZufallsZahl();
//		Log.d(TAG, "zufallszahl: "+puzzleView.getZufallsZahl());
//		zufallSudoku(puzzleView.getZufallsZahl());
		
		
//		puzzle = fromPuzzleString(getZufallSudoku());//getPuzzle();
		Log.d(TAG, "on Create Variable puzzle: "+puzzle.toString());
		calculateUsedTiles();

		puzzleView = new PuzzleView(this, null);
		// Random zufall = new Random();
		// int next = zufall.nextInt(2)+1;
		// Log.d(TAG, "Zufallsgenerierte Zahl in der Klasse Sudoku: "+next);
		// zufallSudoku();
		// spielview.draw(puzzleView.getOnDraw());
		// ll.addView(puzzleView, 0);
		// used[][][] = puzzleView.used;
		setContentView(puzzleView);
//		puzzleView.requestFocus();

		// ...
		// Wenn App neu startet wird es nächstes mal weiterlaufen
		getIntent().putExtra(KEY_DIFFICULTY, DIFFICULTY_CONTINUE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");

		// Puzzle speichern
		getPreferences(MODE_PRIVATE).edit()
				.putString(PUZZLE, toPuzzleString(puzzle)).commit();
	}

	// public void setZufallsZahl(){
	// Random zufall = new Random();
	// zufallsZahl = zufall.nextInt(2)+1;
	// }
	//
	// public int getZufallsZahl(){
	// return zufallsZahl;
	// }
	public void setPuzzle(){
		puzzle = fromPuzzleString(getZufallSudoku());
	}

	/** Schwierigkeit bestimmen */
	private int[] getPuzzle() {
		String puz;
		puz = getZufallSudoku();

		Log.d(TAG, "Zufallspuzzel in der Klasse Game: " + puz);
		return fromPuzzleString(zufallPuzzle);
	}

	/** Array auf String ändern */
	static private String toPuzzleString(int[] puz) {
		StringBuilder buf = new StringBuilder();
		for (int element : puz) {
			buf.append(element);
		}
		return buf.toString();
	}

	/** String in eine Array ändern */
	static protected int[] fromPuzzleString(String string) {
		int[] puz = new int[string.length()];
		for (int i = 0; i < puz.length; i++) {
			puz[i] = string.charAt(i) - '0';
			 Log.d(TAG, "String von dem Puzzle von fromPuzzleString: "+String.valueOf(puz[i]));
		}
		return puz;
	}

	public void zufallSudoku(int next) {

		switch (next) {
		case 1:
			zufallPuzzle = easyPuzzle;
			break;

		case 2:
			zufallPuzzle = testPuzzle;
			break;

		}
		Log.d(TAG, "Der Zufallsgenerator entscheidet sich für: "+zufallPuzzle);

//		fromPuzzleString(zufallPuzzle);
	}

	public String getZufallSudoku() {
		return zufallPuzzle;
	}

	/** Kacheln zurückgeben */
	public int getTile(int x, int y) {
//		puzzle = getPuzzle();
//		setTile(2, 0, 3);
		Log.d(TAG, "Puzzle getTile " + String.valueOf(puzzle[y * 9 + x]));
		
//		for(int i=0; i<81; i++){
//		Log.d(TAG, "Puzzle getTile die Array: "+puzzle[i]+" Wert i: "+i);
//		}
		
		return puzzle[y * 9 + x];
	}

	/** Kacheln ändern */
	public void setTile(int x, int y, int wert) {
		if(puzzle[y * 9 + x] ==0){
		puzzle[y * 9 + x] = wert;
		Log.d(TAG, "Puzzle in setTile: "+puzzle[y * 9 + x]);
		
		Log.d(TAG, "showKeypad: used=" + toPuzzleString(puzzle)
				+ " X und Y " + x + " " + y);
		}
	}

	/** Return a string for the tile at the given coordinates */
	protected String getTileString(int x, int y) {
		Log.d(TAG, "zufallsPuzzle in Game in onCreate: "+zufallPuzzle);

		int v = getTile(x, y);
		if (v == 0)
			return "";
		else
			return String.valueOf(v);
	}

	/** Change the tile only if it's a valid move */
	protected boolean setTileIfValid(int x, int y, int wert) {
//		x = 3;
//		y = 3;
//		wert = 4;
		int tiles[] = getUsedTiles(x, y);

//		for (int i = 0; i < 9; i++) {
//			Log.d("TILES setTileIfValid", "Tiles Array im setTileIfValid: "+ tiles[0]);
//		}

		if (wert != 0) {
			for (int tile : tiles) {
				if (tile == wert)
					return false;
			}
		}
		setTile(x, y, wert);
		calculateUsedTiles();
		return true;
	}

	/** Ziffer Pad reagiert auf validen Feld */
	protected void showKeypadOrError(int x, int y) {
		int tiles[] = getUsedTiles(x, y);
		Log.d("TILES", "showKeypadOrError: x und y: " + x + " " + y);
		// int tiles[] = used[x][y];

		try {
			for (int i = 0; i < 9; i++) {
				Log.d("TILES", "Tiles Array " + tiles[i]);
			}
			if (tiles.length == 9) {
				Toast toast = Toast.makeText(this, R.string.no_moves_label,
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
			} else {
				Log.d(TAG, "showKeypad: used=" + toPuzzleString(tiles)
						+ " X und Y " + x + " " + y);
				 Dialog v = new Keypad(this, tiles, puzzleView);
//				 View v = new Keypad(this, tiles, puzzleView);
//				 v.callOnClick();

				// v.getWindow().setGravity(Gravity.BOTTOM);
				// v.getWindow().setLayout(300, 100);
				 v.show();

			}
			// }

		} catch (Exception e) {
			Log.d("TILES", "Tiles Array Geht nicht");
		}

		Log.d(TAG, " showkeypadorerror: x und y " + x + " " + y);
		// cbutton = (Button) findViewById(R.id.button1);

	}

	/** Cache of used tiles */
	// protected final int used[][][] = new int[9][9][];

	/** Return cached used tiles visible from the given coords */
	protected int[] getUsedTiles(int x, int y) {
		Log.d(TAG + " Testlauf", "getUserTiles x und y " + x + " " + y);
		return used[x][y];
	}

	/** Compute the two dimensional array of used tiles */
	private void calculateUsedTiles() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				used[x][y] = calculateUsedTiles(x, y);
				 Log.d(TAG, "used[" + x + "][" + y + "] = "
				 + toPuzzleString(used[x][y]));
			}
		}
	}

	/** Compute the used tiles visible from this position */
	private int[] calculateUsedTiles(int x, int y) {
		int c[] = new int[9];
		// horizontal
		for (int i = 0; i < 9; i++) {
			if (i == y)
				continue;
			int t = getTile(x, i);
			if (t != 0)
				c[t - 1] = t;
		}
		// vertical
		for (int i = 0; i < 9; i++) {
			if (i == x)
				continue;
			int t = getTile(i, y);
			if (t != 0)
				c[t - 1] = t;
		}
		// same cell block
		int startx = (x / 3) * 3;
		int starty = (y / 3) * 3;
		for (int i = startx; i < startx + 3; i++) {
			for (int j = starty; j < starty + 3; j++) {
				if (i == x && j == y)
					continue;
				int t = getTile(i, j);
				if (t != 0)
					c[t - 1] = t;
			}
		}
		// compress
		int nused = 0;
		for (int t : c) {
			if (t != 0)
				nused++;
		}
		int c1[] = new int[nused];
		nused = 0;
		for (int t : c) {
			if (t != 0)
				c1[nused++] = t;
		}
		return c1;
	}

}
