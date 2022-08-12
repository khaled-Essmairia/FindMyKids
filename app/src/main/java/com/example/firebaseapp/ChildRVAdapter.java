package com.example.firebaseapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChildRVAdapter extends RecyclerView.Adapter<ChildRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<ChildRVModal> childRVModalArrayList;
    private Context context;
    private childClickInterface childClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public ChildRVAdapter(ArrayList<ChildRVModal> childRVModalArrayList, Context context, childClickInterface childClickInterface) {
        this.childRVModalArrayList = childRVModalArrayList;
        this.context = context;
        this.childClickInterface = childClickInterface;
    }

    @NonNull
    @Override
    public ChildRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.child_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our recycler view item on below line.
        ChildRVModal childRVModal = childRVModalArrayList.get(position);
        holder.childTV.setText(childRVModal.getchildName());
        holder.childdesc2TV.setText("Rs. " + childRVModal.getchilddesc2());
        Picasso.get().load(childRVModal.getchildImg()).into(holder.childIV);
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.childIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childClickInterface.onchildClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return childRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView childIV;
        private TextView childTV, childdesc2TV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            childIV = itemView.findViewById(R.id.idIVchild);
            childTV = itemView.findViewById(R.id.idTVchildName);
            childdesc2TV = itemView.findViewById(R.id.idTVCousedesc2);
        }
    }

    // creating a interface for on click
    public interface childClickInterface {
        void onchildClick(int position);
    }
}
