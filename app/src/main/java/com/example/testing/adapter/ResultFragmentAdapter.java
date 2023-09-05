package com.example.testing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.model.DataModel;

import java.util.ArrayList;

public class ResultFragmentAdapter extends RecyclerView.Adapter<ResultFragmentAdapter.ResultViewHolder> {
    private boolean misApplicable;
    ArrayList<String> marrayList;
    private Context mContext;

    public ResultFragmentAdapter(Context context, ArrayList<String> arrayList, boolean isApplicable){
        this.mContext = context;
        this.marrayList = arrayList;
        this.misApplicable = isApplicable;

    }
    @NonNull
    @Override
    public ResultFragmentAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view, parent, false);
        return new ResultFragmentAdapter.ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultFragmentAdapter.ResultViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
