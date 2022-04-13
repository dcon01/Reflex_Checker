package com.example.reflexchecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private long startTime;
    private ViewGroup game_rows;
    private final Random random = new Random();
    private static final int[] drawables = {
            R.drawable.baseline_android_black_48,
            R.drawable.baseline_flutter_dash_black_48,
            R.drawable.baseline_nights_stay_black_48,
            R.drawable.baseline_pan_tool_black_48,
            R.drawable.baseline_rocket_launch_black_48,
            R.drawable.baseline_sports_kabaddi_black_48
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        System.out.println("Game activity started");

        game_rows = findViewById(R.id.game_rows);

        setupDescription(R.id.task1, R.array.task1_descriptions);
        setupDescription(R.id.task2, R.array.task2_descriptions);


        for (int i = 0; i < 5; ++i) {
            addRandomImage();
            addRandomCheckboxes(R.array.drinks);
            addRandomImage();
            addRandomCheckboxes(R.array.fruits);
        }

        startTime = System.nanoTime();
    }

    private void setupDescription(int taskID, int arrayID){
        TextView task = findViewById(taskID);
        String[] descriptions = getResources().getStringArray(arrayID);

        int i = random.nextInt(descriptions.length);
        task.setText(descriptions[i]);
    }

    private void addRandomImage() {
        ViewGroup gameRows = findViewById(R.id.game_rows);
        getLayoutInflater().inflate(R.layout.image, game_rows);

        View lastChild = gameRows.getChildAt(gameRows.getChildCount() - 1);
        ImageView image = (ImageView) lastChild;

        int index = random.nextInt(drawables.length);
        image.setImageDrawable(getResources().getDrawableForDensity(drawables[index], 0));
    }

    private void addRandomCheckboxes(int arrayID) {
        getLayoutInflater().inflate(R.layout.checkboxes, game_rows);
        TableRow tableRow = (TableRow) getGameRowsLastChild();

        for (int i = 0; i < tableRow.getChildCount(); ++i) {
            View child = tableRow.getChildAt(i);
            CheckBox checkBox = (CheckBox) child;

            boolean isChecked = random.nextBoolean();
            checkBox.setChecked(isChecked);

            String[] descriptions = getResources().getStringArray(arrayID);
            int j = random.nextInt(descriptions.length);

            checkBox.setText(descriptions[j]);
        }
    }

    private View getGameRowsLastChild() {
        return game_rows.getChildAt(game_rows.getChildCount() - 1);
    }

    public void doneClicked(View view) {
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        double seconds = duration * 1.0 / 1e9;

        Intent data = new Intent();
        data.putExtra("seconds", seconds);

        setResult(RESULT_OK, data);
        finish();
    }
}