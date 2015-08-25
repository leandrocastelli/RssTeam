package com.lcsmobileapps.rssteam.ui;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcsmobileapps.rssteam.R;
import com.lcsmobileapps.rssteam.feed.Feed;
import com.lcsmobileapps.rssteam.feed.Team;
import com.lcsmobileapps.rssteam.provider.ContentController;
import com.lcsmobileapps.rssteam.util.ImageHelper;
import com.lcsmobileapps.rssteam.util.Utils;

import java.util.List;

/**
 * Created by leandro.silverio on 25/08/2015.
 */
public class TeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Team> mDataset;
    private DialogFragment fragment;
    public TeamAdapter(Context ctx, DialogFragment fragment) {
        this.ctx = ctx;
        mDataset = ContentController.getInstance().getAllTeams(ctx);
        this.fragment = fragment;
    }
    Context ctx;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_view_holder, parent, false);
        ViewHolder vh = new ViewHolder((CardView)v);

        vh.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtView = (TextView)view.findViewById(R.id.team_name);
                Utils.setPrefTeamName(parent.getContext(), txtView.getText().toString());
                FeedFragment.MyHandler myHandler = new FeedFragment.MyHandler();
                myHandler.sendEmptyMessage(FeedFragment.WHAT_REFRESH_TEAM);
                fragment.dismiss();
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ImageView imgView = (ImageView)holder.itemView.findViewById(R.id.team_flag);
        TextView title = (TextView)holder.itemView.findViewById(R.id.team_name);
        ImageHelper.loadImage(imgView, mDataset.get(position).flag, ctx);
        title.setText(mDataset.get(position).name);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }
}
