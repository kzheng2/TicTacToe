package com.kai.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = first player, 1 = second player
    private int activePlayer;

    // 2 = unplayed
    private int[] gameState = new int[] {2, 2, 2, 2, 2, 2, 2 ,2, 2};

    // Combination of winner with same color position
    private int[][] winState = new int[][] {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    private int numOfStep = 0;

    private boolean gameOver = true;

    private boolean choosePlayer = false;


    public void placeChase(View view) {

        ImageView imageView = (ImageView) view;

        int currentPosition = Integer.parseInt(imageView.getTag().toString());
        System.out.println(imageView.getTag().toString());

        if(gameState[currentPosition] == 2 && gameOver == false && choosePlayer == true) {

            imageView.setTranslationY(-1000f);

            if(activePlayer == 0) {
                imageView.setImageResource(R.drawable.red);
                activePlayer = 1;
                // Mark current position with the player tag
                gameState[currentPosition] = 0;
            } else {
                imageView.setImageResource(R.drawable.yellow);
                activePlayer = 0;
                gameState[currentPosition] = 1;
            }

            imageView.animate().translationYBy(1000f).rotation(180).setDuration(200);
            Log.i("Info", "Place chase on the board");

            numOfStep++;

            if(numOfStep >= 5) {

                LinearLayout linearLayout = findViewById(R.id.gameOverLayout);
                TextView textView = findViewById(R.id.gameOverMessage);
                String winner;

                for(int i = 0; i < winState.length; i++) {
                    if(gameState[winState[i][0]] == gameState[winState[i][1]] && gameState[winState[i][1]] == gameState[winState[i][2]] && gameState[winState[i][0]] != 2) {
                        if(gameState[winState[i][0]] == 0) {
                            System.out.println("Winner is Red!");
                            winner = "RED";
                        } else {
                            System.out.println("Winner is Yellow");
                            winner = "YELLOW";
                        }
                        gameOver = true;
                        textView.setText("Winner is: " + winner);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }

                if(gameOver == false && numOfStep == 9) {
                    gameOver = true;
                    textView.setText("Draw Game!");
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void restartGame(View view) {

        gameState = new int[] {2, 2, 2, 2, 2, 2, 2 ,2, 2};
        numOfStep = 0;
        choosePlayer = false;

        ImageView imageView;

        GridLayout gridLayout = findViewById(R.id.gameBoard);

        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageResource(android.R.color.transparent);
        }

        LinearLayout linearLayout = findViewById(R.id.gameOverLayout);
        linearLayout.setVisibility(View.INVISIBLE);
    }

    public void startWithRed(View view) {
        if(choosePlayer == false) {
            activePlayer = 0;
            choosePlayer = true;
            gameOver = false;
        }
    }

    public void startWithYellow(View view) {
        if(choosePlayer == false) {
            activePlayer = 1;
            choosePlayer = true;
            gameOver = false;
        }
    }

    public void exitGame(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
