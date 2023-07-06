package com.uas.lokerapps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> jobList;

    public JobAdapter(List<Job> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);

        holder.logoImageView.setImageResource(job.getLogo());
        holder.positionTextView.setText(job.getPosition());
        holder.companyTextView.setText(job.getCompany());
        holder.descriptionTextView.setText(job.getDescription());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        ImageView logoImageView;
        TextView positionTextView;
        TextView companyTextView;
        TextView descriptionTextView;

        JobViewHolder(@NonNull View itemView) {
            super(itemView);
            logoImageView = itemView.findViewById(R.id.logoImageView);
            positionTextView = itemView.findViewById(R.id.positionTextView);
            companyTextView = itemView.findViewById(R.id.companyTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
