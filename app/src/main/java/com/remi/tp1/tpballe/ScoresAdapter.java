package com.remi.tp1.tpballe;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.remi.tp1.tpballe.Score;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Rémi on 27/10/2017.
 */

public class ScoresAdapter extends ArrayAdapter<Score> {
    public ScoresAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ScoresAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ScoresAdapter(@NonNull Context context, int resource, @NonNull Score[] objects) {
        super(context, resource, objects);
    }

    public ScoresAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Score[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ScoresAdapter(@NonNull Context context, int resource, @NonNull List<Score> objects) {
        super(context, resource, objects);
    }

    public ScoresAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Score> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ScoresAdapter(@NonNull Context context, @NonNull List<Score> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_score,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.joueur);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.score = (TextView) convertView.findViewById(R.id.score);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Score> scores
        Score score = getItem(position);
        viewHolder.pseudo.setText(score.getJoueur());
        viewHolder.date.setText(score.getDate().toString());
        viewHolder.score.setText(score.getScore());
        viewHolder.avatar.setImageDrawable(new ColorDrawable(score.getColor()));

        return convertView;
    }

    private class TweetViewHolder{
        public TextView pseudo;
        public TextView date;
        public TextView score;
        public ImageView avatar;

    }
}
