package com.example.mercury.hockey_data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamViewHolder> {

    private final Context context;
    private ArrayList<Team> teamList;

    TeamsAdapter(Context context, ArrayList<Team> data) {
        teamList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.team_item, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teamList.get(position);
        holder.teamName.setText(team.getName());
        holder.siteLink.setText(team.getWebsiteLink());
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    static class TeamViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.team_name)
        TextView teamName;

        @BindView(R.id.website_link)
        TextView siteLink;

        TeamViewHolder(View itemView) {
            super(itemView);
        }


    }
}
