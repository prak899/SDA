package com.example.testing.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testing.R;
import com.example.testing.activity.BaseActivity;
import com.example.testing.fragments.BaseFragment;

import java.util.List;
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewAdapter> {
    private Context mContext;
    private List<String> mlist;
    private FragmentManager mfragmentManager;
    private boolean misActivate;
    public RecyclerAdapter(Context context, List<String > list,
                           FragmentManager fragmentManager, boolean isActivate){
        this.mContext = context;
        this.mlist = list;
        this.mfragmentManager = fragmentManager;
        this.misActivate = isActivate;

    }
    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view, parent, false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {

        holder.textView.setText(mlist.get(position));
        if (misActivate)
            holder.textView.setVisibility(View.VISIBLE);
        else {
            holder.textView.setVisibility(View.GONE);
            holder.notApplicable.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
       return mlist.size();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView, notApplicable;
        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text);
            notApplicable = itemView.findViewById(R.id.not_applicable);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String clickedItem = mlist.get(position);
                        Toast.makeText(mContext, "Clicked: " + clickedItem, Toast.LENGTH_SHORT).show();
                    }*/
                    int position = getAdapterPosition();
                    String sendingData = mlist.get(position);
                    Intent intent =new Intent(mContext, BaseActivity.class);
                    intent.putExtra("carData", sendingData);

                    if (sendingData.equalsIgnoreCase("dummyData to check whether it's showing or not ->15")) {
                        intent.putExtra("whichFragment", "one");
                    } else if (sendingData.equalsIgnoreCase("dummyData to check whether it's showing or not ->10")) {
                        intent.putExtra("whichFragment", "two");
                    } else {
                        intent.putExtra("whichFragment", "xxx");
                    }
                    mContext.startActivity(intent);

                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
