package com.lcsmobileapps.rssteam.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
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
 * Created by Leandro on 3/12/2015.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{
    private List<Feed> mDataset;
    private Team team;
    Context ctx;

    public void setTeam(Team team) {
        this.team = team;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((CardView)v);

        return vh;
    }
    public FeedAdapter(List<Feed> feeds, Context ctx) {

        mDataset = feeds;
        if (feeds != null && !feeds.isEmpty()) {
            team = ContentController.getInstance().getTeamFromFeed(feeds.get(0), ctx);
        }
        this.ctx = ctx;
    }
    @Override
    public void onViewAttachedToWindow(ViewHolder vh) {
        setAnimation(vh.itemView,true);
    }
    @Override
    public void onViewDetachedFromWindow(ViewHolder vh) {
        setAnimation(vh.itemView,false);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (team != null) {


            ImageView imgView = (ImageView)holder.itemView.findViewById(R.id.team_icon);
            TextView title = (TextView)holder.itemView.findViewById(R.id.feed_title);
            TextView date = (TextView)holder.itemView.findViewById(R.id.feed_date);
            ImageHelper.loadImage(imgView, team.flag,ctx);
            title.setText(mDataset.get(position).getTitle());
            date.setText(Utils.convertToString(mDataset.get(position).getDate()));
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = mDataset.get(position).getLink();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    ctx.startActivity(i);
                }
            });
        }

    }
    public void updateFeeds () {
        team = ContentController.getInstance().getTeam(Utils.getPrefTeamName(ctx), ctx);
        if (team == null)
            return;
        mDataset = ContentController.getInstance().getNews(team.name,ctx);
        notifyDataSetChanged();
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
    private void setAnimation(final View viewToAnimate, boolean appear)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        int cx = (viewToAnimate.getLeft() + viewToAnimate.getRight()) / 2;
        int cy = (viewToAnimate.getTop() + viewToAnimate.getBottom()) / 2;

// get the final radius for the clipping circle
        int finalRadius = Math.max(viewToAnimate.getWidth(), viewToAnimate.getHeight());
        Animator animator;
        if (appear) {
            animator = ViewAnimationUtils.createCircularReveal(viewToAnimate, cx, cy, 0, finalRadius);

            } else {
            animator = ViewAnimationUtils.createCircularReveal(viewToAnimate, cx, cy, finalRadius, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                }
            });


            }
        animator.start();
    }
}
