package au.edu.jcu.reflexchecker;

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

    private ViewGroup gameRows;
    private final Random random = new Random();

    private static final int[] drawables = {
            R.drawable.baseline_pan_tool_black_48,
            R.drawable.baseline_people_black_48,
            R.drawable.baseline_settings_accessibility_black_48,
            R.drawable.baseline_shop_2_black_48,
            R.drawable.baseline_thumb_up_black_48
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameRows = findViewById(R.id.game_rows);

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

    private void setupDescription(int taskID, int arrayID) {
        TextView task = findViewById(taskID);
        String[] descriptions = getResources().getStringArray(arrayID);

        int i = random.nextInt(descriptions.length);
        task.setText(descriptions[i]);
    }

    private void addRandomImage() {
        getLayoutInflater().inflate(R.layout.image, gameRows);
        ImageView image = (ImageView) getGameRowsLastChild();

        int index = random.nextInt(drawables.length);
        image.setImageDrawable(getResources().getDrawableForDensity(drawables[index], 0));
    }

    private void addRandomCheckboxes(int arrayID) {
        getLayoutInflater().inflate(R.layout.checkboxes, gameRows);
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
        return gameRows.getChildAt(gameRows.getChildCount() - 1);
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
