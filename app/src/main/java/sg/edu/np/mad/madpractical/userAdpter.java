package sg.edu.np.mad.madpractical;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class userAdpter extends RecyclerView.Adapter<viewHolder>{
    private ArrayList<User> userList;
    private Context context;


    public userAdpter(ArrayList<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        User user = userList.get(position);
        if (user.getName().endsWith("7")) {
            Log.v("userAdapter", "getItemViewType: View type 1 selected");
            return 1;
        } else {
            Log.v("userAdapter", "getItemViewType: View type 0 selected");
            return 0;
        }
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int layoutRes = (viewType == 1) ? R.layout.alt_userrv : R.layout.userrv;
        Log.v("userAdapter", "onCreateViewHolder: Inflating layout with resource ID: " + layoutRes);
        View itemView = inflater.inflate(layoutRes, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.descriptionTextView.setText(user.getDescription());

        holder.profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Profile");
                builder.setMessage(user.getName());
                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("User", new User(user.getName(), user.getDescription(), user.getId(), user.isFollowed()));
                        v.getContext().startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
