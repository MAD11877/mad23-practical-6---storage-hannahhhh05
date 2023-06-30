package sg.edu.np.mad.madpractical;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView;
    TextView descriptionTextView;
    ImageView profileImageView;

    public viewHolder(@NonNull View itemView) {
        super(itemView);
        profileImageView = itemView.findViewById(R.id.icon);
        nameTextView = itemView.findViewById(R.id.textView3);
        descriptionTextView = itemView.findViewById(R.id.textView4);
    }
}
