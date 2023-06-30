package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String TITLE = "Main Activity";
    private Button followButton;
    private boolean isFollowed;
    private User user;
    private TextView nameTextView;
    private TextView descTextView;
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TITLE, "On Create!");

        Intent receivingEnd = getIntent();
        user = receivingEnd.getParcelableExtra("User");

        // Initialize views
        nameTextView = findViewById(R.id.textView2);
        nameTextView.setText(user.name);

        descTextView = findViewById(R.id.textView3);
        descTextView.setText(user.description);

        followButton = findViewById(R.id.button);

        // Instantiate DBHandler
        dbHandler = new DBHandler(this);

        // Set initial values
        updateNameTextView();
        isFollowed = false;
        updateFollowButton();

        // Button click listeners
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFollowStatus();
                updateFollowButton();
            }
        });

        Button messageButton = findViewById(R.id.button2);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch MessageGroup activity
                Intent intent = new Intent(MainActivity.this, MessageGroup.class);
                startActivity(intent);
            }
        });

        // Check if the activity was launched from ListActivity with a random integer
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("randomInt")) {
            int randomInt = extras.getInt("randomInt", 0);
            updateNameTextViewWithRandomInt(randomInt);
        }
    }

    private void toggleFollowStatus() {
        isFollowed = !isFollowed;
        user.setFollowed(isFollowed);
        dbHandler.updateUser(user);
    }

    private void updateFollowButton() {
        if (!user.followed) {
            Log.v(TITLE, "Followed");
            followButton.setText("UNFOLLOW");
            Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();

        } else {
            Log.v(TITLE, "Unfollowed");
            followButton.setText("FOLLOW");
            Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNameTextView() {
        nameTextView.setText(user.getName());
    }

    private void updateNameTextViewWithRandomInt(int randomInt) {
        String updatedName = user.getName() + randomInt;
        nameTextView.setText(updatedName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TITLE, "On Start!");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.v(TITLE, "On Pause!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TITLE, "On Resume!");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.v(TITLE, "On Stop!");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.v(TITLE, "On Destroy!");
    }
}