package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {

    public ArrayList<User> userList;
    private DBHandler dbHandler;

    private static final String TAG = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Log.v(TAG, "onCreate: Starting activity");

        // Initialize the userList ArrayList
        userList = new ArrayList<>();



        // Instantiate DBHandler
        dbHandler = new DBHandler(this);

        // Check if user records exist in the database
        boolean hasExistingUsers = dbHandler.getAllUsers().size() > 0;
        Log.v(TAG, "onCreate: hasExistingUsers = " + hasExistingUsers);


        // Create a list of User objects if there are no existing users
        if (!hasExistingUsers) {
            Random random = new Random();
            for (int i = 2; i < 22; i++) {
                String name = "User " + random.nextInt(100000);
                String description = "Description " + random.nextInt(1000000);
                boolean followed = random.nextBoolean();
                User user = new User(name, description, i, followed);
                userList.add(user);
                dbHandler.addUser(user);
            }
            Log.v(TAG, "onCreate: Added new users to the database");

        }

        userList = dbHandler.getAllUsers();

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        userAdpter mAdapter = new userAdpter(userList, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        Log.v(TAG, "onCreate: RecyclerView set up successfully");

    }
}