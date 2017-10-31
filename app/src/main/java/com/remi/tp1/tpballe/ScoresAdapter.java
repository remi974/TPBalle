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

    /**
     * Get a View that displays the data at the specified position in the data set.
     * @param position The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using. If it is not possible to convert this view to display the correct data, this method can create a new view. Heterogeneous lists can specify their number of view types, so that this View is always of the right type (see getViewTypeCount() and getItemViewType(int)).
     * @param parent The parent that this view will eventually be attached to    This value must never be null.
     * @return A View corresponding to the data at the specified position.
    This value will never be null.
     */
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
            //viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Score> scores
        Score score = getItem(position);
        viewHolder.pseudo.setText(score.getJoueur());
        viewHolder.date.setText(score.getDate().toString());
        viewHolder.score.setText(score.getScore());
        //viewHolder.avatar.setImageDrawable(new ColorDrawable(score.getColor()));

        return convertView;
    }


    private class TweetViewHolder{
        public TextView pseudo;
        public TextView date;
        public TextView score;
        //public ImageView avatar;

    }
}
