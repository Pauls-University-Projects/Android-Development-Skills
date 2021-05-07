package coursework.paul.mynoughtscrosses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //------------------------------------------------------------------
    //                            SETTINGS:
    //------------------------------------------------------------------
    // User Symbol:
    private String userSide = "X";
    // User Colour:
    private int userColour = Color.parseColor("#263238");
    // Computer Symbol:
    private String computerSide = "O";
    // Computer Colour
    private int computerColour = Color.parseColor("#ECEFF1");
    //------------------------------------------------------------------


    // Variables:
    private static final String TAG = "Game-Message";
    boolean computerTurn = false;
    TextView textBox;
    public static final String FILE_NAME = "savegame.txt";
    private File file;
    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private int gameCounter = 1;

    // GRID-SQUARE GUIDE:
    // Array:        Logcat Messages:
    // +-+-+-+     / +-+-+-+
    // |0|1|2|    /  |1|2|3|
    // |3|4|5|   /   |4|5|6|
    // |3|4|5|  /    |7|8|9|
    // +-+-+-+ /     +-+-+-+

    Square grid[] = new Square[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialising the Array
        for (int i= 0; i<9; i++) {
            grid[i] = new Square();
        }
        // Connecting Activity Buttons with Variables
        grid[0].location = (Button)findViewById(R.id.square1);
        grid[1].location = (Button)findViewById(R.id.square2);
        grid[2].location = (Button)findViewById(R.id.square3);
        grid[3].location = (Button)findViewById(R.id.square4);
        grid[4].location = (Button)findViewById(R.id.square5);
        grid[5].location = (Button)findViewById(R.id.square6);
        grid[6].location = (Button)findViewById(R.id.square7);
        grid[7].location = (Button)findViewById(R.id.square8);
        grid[8].location = (Button)findViewById(R.id.square9);
        // Connecting TextView and File with Variables
        textBox = (TextView)findViewById(R.id.textView);
        file = new File(this.getFilesDir(), FILE_NAME);

        Log.d(TAG, " ");
        Log.d(TAG, "GAME STARTED");
        Log.d(TAG, " ");
    }

    // Automatically Save a Game:
    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    // Automatically Load a Game:
    @Override
    public void onStart() {
        super.onStart();
        load();
    }

    // Players Turn:
    public void onButtonClick(View view) {
        if (computerTurn == false && gameIsOver() == false) {
            int square;
            switch (view.getId()) {
                case R.id.square1:
                    square = 0;
                    break;
                case R.id.square2:
                    square = 1;
                    break;
                case R.id.square3:
                    square = 2;
                    break;
                case R.id.square4:
                    square = 3;
                    break;
                case R.id.square5:
                    square = 4;
                    break;
                case R.id.square6:
                    square = 5;
                    break;
                case R.id.square7:
                    square = 6;
                    break;
                case R.id.square8:
                    square = 7;
                    break;
                case R.id.square9:
                    square = 8;
                    break;
                default:
                    Log.e(TAG, "ERROR: Could Not Find What You Clicked");
                    return;
            }

            if (grid[square].isEmpty == true) {
                grid[square].setValue(userSide, userColour);
                Log.d(TAG, "Player Placed " + userSide + " on Square " + (square+1));
                computerTurn = true;
            } else {
                Log.d(TAG, "Player Pressed Square " + (square+1) + ", but it is full");
                // POP UP THAT SQUARE IS FULL?
            }

        }
        if (gameIsOver() == false) {
            computerGuess();
        } else {
            gameOverMessage();
        }
    }

    // Computers Turn:
    void computerGuess() {
        while (computerTurn == true) {
            Random r = new Random();
            int randomNumber = r.nextInt(10 - 1);

            if (grid[randomNumber].isEmpty) {
                grid[randomNumber].setValue(computerSide, computerColour);
                Log.d(TAG, "Computer Placed " + computerSide + " on Square " + (randomNumber+1));
                computerTurn = false;
            } else {
                Log.d(TAG, "Computer Guessed Square "+ (randomNumber+1) +", but it is full");
            }

        }
        gameIsOver();
    }

    // Game Check:
    boolean gameIsOver () {

        // VICTORY CHECK:
        // First Line:
        if (!grid[0].isEmpty && grid[0].value.equals(grid[1].value) && grid[1].value.equals(grid[2].value)) {
            Log.d(TAG, grid[0].value + " Won on Row 1!");
            textBox.setText(grid[0].value + " Won!");
            return true;
        // Second Line:
        } else if (!grid[3].isEmpty && grid[3].value.equals(grid[4].value) && grid[4].value.equals(grid[5].value)) {
            Log.d(TAG, grid[3].value + " Won on Row 2!");
            textBox.setText(grid[3].value + " Won!");
            return true;
        // Third Line:
        } else if (!grid[6].isEmpty && grid[6].value.equals(grid[7].value) && grid[7].value.equals(grid[8].value)) {
            Log.d(TAG, grid[6].value + " Won on Row 3!");
            textBox.setText(grid[6].value + " Won!");
            return true;
        // First Column:
        } else if (!grid[0].isEmpty && grid[0].value.equals(grid[3].value) && grid[3].value.equals(grid[6].value)) {
            Log.d(TAG, grid[0].value + " Won on Column 1!");
            textBox.setText(grid[0].value + " Won!");
            return true;
        // Second Column:
        } else if (!grid[1].isEmpty && grid[1].value.equals(grid[4].value) && grid[4].value.equals(grid[7].value)) {
            Log.d(TAG, grid[1].value + " Won on Column 2!");
            textBox.setText(grid[1].value + " Won!");
            return true;
        // Third Column:
        } else if (!grid[2].isEmpty && grid[2].value.equals(grid[5].value) && grid[5].value.equals(grid[8].value)) {
            Log.d(TAG, grid[2].value + " Won on Column 3!");
            textBox.setText(grid[2].value + " Won!");
            return true;
        // First Diagonal:
        } else if (!grid[0].isEmpty && grid[0].value.equals(grid[4].value) && grid[4].value.equals(grid[8].value)) {
            Log.d(TAG, grid[0].value + " Won on Diagonal 1-5-9!");
            textBox.setText(grid[0].value + " Won!");
            return true;
        // Second Diagonal:
        } else if (!grid[2].isEmpty && grid[2].value.equals(grid[4].value) && grid[4].value.equals(grid[6].value)) {
            Log.d(TAG, grid[2].value + " Won on Diagonal 5-5-7!");
            textBox.setText(grid[2].value + " Won!");
            return true;

        // TIE CHECK:
        } else if ( !grid[0].isEmpty && !grid[1].isEmpty && !grid[2].isEmpty &&
                    !grid[3].isEmpty && !grid[4].isEmpty && !grid[5].isEmpty &&
                    !grid[6].isEmpty && !grid[7].isEmpty && !grid[8].isEmpty) {
            Log.d(TAG, "Tie!");
            textBox.setText("Tie!");
            return true;
        } else {
            return false;
        }
    }

    // Asks if the player wants to start a new game:
    void gameOverMessage () {
        Context context = this;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context, R.style.MessageStyle);
        builder1.setTitle("Game Over");
        builder1.setMessage("Would You Like to Start a New Game?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        gameReset();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // Reset Game:
    void gameReset () {
        gameCounter++;
        grid[0].setValue(" ",Color.BLACK);
        grid[1].setValue(" ",Color.BLACK);
        grid[2].setValue(" ",Color.BLACK);
        grid[3].setValue(" ",Color.BLACK);
        grid[4].setValue(" ",Color.BLACK);
        grid[5].setValue(" ",Color.BLACK);
        grid[6].setValue(" ",Color.BLACK);
        grid[7].setValue(" ",Color.BLACK);
        grid[8].setValue(" ",Color.BLACK);
        computerTurn = false;
        textBox.setText(" ");
        Log.d(TAG, " ");
        Log.d(TAG, "GAME " + gameCounter + " STARTED");
        Log.d(TAG, " ");
    }

    // Save Game:
    public void save() {
        String data =   grid[0].value.toString() + "|" + grid[1].value.toString() + "|" + grid[2].value.toString() + "|" +
                        grid[3].value.toString() + "|" + grid[4].value.toString() + "|" + grid[5].value.toString() + "|" +
                        grid[6].value.toString() + "|" + grid[7].value.toString() + "|" + grid[8].value.toString();
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
            Toast.makeText(this, "Game Saved!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Game Saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load a Previously Saved Game:
    public void load() {
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            inputStream.close();
            String data = new String(bytes);

            for (int i= 0; i<9; i++) {
                if (data.split("\\|")[i].equals(userSide)) {
                    grid[i].setValue(data.split("\\|")[i],userColour);
                    Log.d(TAG, "Square " + (i+1) + ": " + grid[i].value );
                } else if (data.split("\\|")[i].equals(computerSide)) {
                    grid[i].setValue(data.split("\\|")[i],computerColour);
                    Log.d(TAG, "Square " + (i+1) + ": " + grid[i].value );
                } else {
                    Log.d(TAG, "Square " + (i+1) + ": Empty");
                }
            }
            Toast.makeText(getBaseContext(), "Game Loaded!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Game Loaded.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

