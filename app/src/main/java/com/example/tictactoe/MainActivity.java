package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    boolean isTurnX;
    HashMap<View, Integer> viewToModel;
    int[] model;
    boolean[] isEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTurnX = true;
        isEnabled = new boolean[9];
        viewToModel = new HashMap();
        viewToModel.put(findViewById(R.id.btn1), 0);
        viewToModel.put(findViewById(R.id.btn2), 1);
        viewToModel.put(findViewById(R.id.btn3), 2);
        viewToModel.put(findViewById(R.id.btn4), 3);
        viewToModel.put(findViewById(R.id.btn5), 4);
        viewToModel.put(findViewById(R.id.btn6), 5);
        viewToModel.put(findViewById(R.id.btn7), 6);
        viewToModel.put(findViewById(R.id.btn8), 7);
        viewToModel.put(findViewById(R.id.btn9), 8);
        model = new int[9];
        for(int i = 0; i < 9; ++i) {
            model[i] = 0;
        }
        resetBoard();
    }

    public void resetBoard() {
        // Reset board
        for (int i = 0; i < 9; i++) {
            model[i] = 0;
        }
        // Re-enable all squares
        for (int i = 0; i < 9; ++i) {
            isEnabled[i] = true;
        }
        // Set all squares to blank
        findViewById(R.id.btn1).setBackground(getResources().getDrawable(R.drawable.blank));
        findViewById(R.id.btn2).setBackground(getResources().getDrawable(R.drawable.blank));
        findViewById(R.id.btn3).setBackground(getResources().getDrawable(R.drawable.blank));
        findViewById(R.id.btn4).setBackground(getResources().getDrawable(R.drawable.blank));
        findViewById(R.id.btn5).setBackground(getResources().getDrawable(R.drawable.blank));
        findViewById(R.id.btn6).setBackground(getResources().getDrawable(R.drawable.blank));
        findViewById(R.id.btn7).setBackground(getResources().getDrawable(R.drawable.blank));
        findViewById(R.id.btn8).setBackground(getResources().getDrawable(R.drawable.blank));
        findViewById(R.id.btn9).setBackground(getResources().getDrawable(R.drawable.blank));
    }

    public boolean checkWinnerRow(int row) {
        switch(row){
            case 0:
                if (model[0] != 0 &&
                    model[0] == model[1] && model[1] == model[2]) {
                    return true;
                }
                break;
            case 1:
                if (model[3] != 0 &&
                        model[3] == model[4] && model[4] == model[5]) {
                    return true;
                }
                break;
            case 2:
                if (model[6] != 0 &&
                        model[6] == model[7] && model[7] == model[8]) {
                    return true;
                }
                break;
        }
        return false;
    }
    public boolean checkWinnerCol(int col) {
        switch(col){
            case 0:
                if (model[0] != 0 &&
                        model[0] == model[3] && model[3] == model[6]) {
                    return true;
                }
                break;
            case 1:
                if (model[1] != 0 &&
                        model[1] == model[4] && model[4] == model[7]) {
                    return true;
                }
                break;
            case 2:
                if (model[2] != 0 &&
                        model[2] == model[5] && model[5] == model[8]) {
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean checkWinnerDiag(boolean isDownDiag) {
        if (isDownDiag) {
            if (model[0] != 0 &&
                model[0] == model[4] && model[4] == model[8]) {
                return true;
            }
        } else {
            if (model [2] != 0 &&
                model [2] == model[4] && model[4] == model[6]) {
                return true;
            }
        }
        return false;
    }

    public void checkTiesIfNoWinner() {
        for (int i = 0; i < 9; ++i) {
            if (model[i] == 0) {
                return;
            }
        }
        Toast.makeText(getApplicationContext(), "Tie Game :(", Toast.LENGTH_LONG).show();
        resetBoard();
    }

    public void displayWinner(int winner) {
        if (winner == 1) {
            Toast.makeText(getApplicationContext(), "X Wins!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "O Wins!", Toast.LENGTH_LONG).show();
        }
        resetBoard();
    }

    public void btnClick(View view)
    {
        int row = viewToModel.get(view) / 3;
        int col = viewToModel.get(view) % 3;
        if (isEnabled[row*3 + col]) {
            if (isTurnX) {
                view.setBackground(getResources().getDrawable(R.drawable.tic_tac_toe_x));
                model[viewToModel.get(view)] =  1;
            } else {
                view.setBackground(getResources().getDrawable(R.drawable.tic_tac_toe_o));
                model[viewToModel.get(view)] =  2;
            }
            isEnabled[row*3 + col] = false;
            if (checkWinnerRow(row) || checkWinnerCol(col)) {
                displayWinner(isTurnX ? 1 : 2);
            } else {
                if (row == col && checkWinnerDiag(true)) {
                    displayWinner(isTurnX ? 1 : 2);
                } else if (row + col == 2 && checkWinnerDiag(false)) {
                    displayWinner(isTurnX ? 1 : 2);
                }
            }
            checkTiesIfNoWinner();
            isTurnX = !isTurnX;
        }
    }
}
