package com.remi.tp1.tpballe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoresActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);

        afficherListeScores();
    }

    private void afficherListeScores(){
        //List<Tweet> tweets = genererTweets();

        ScoresTPBalleXMLParser parser = new ScoresTPBalleXMLParser();
        List<Score> alScores = null;
        try {
            alScores = parser.parse(getResources().openRawResource(R.raw.scores));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(alScores, Sort.ORDER_BY_SCORE_DESC);

        ScoresAdapter adapter = new ScoresAdapter(ScoresActivity.this, alScores);
        adapter.notifyDataSetChanged();

        mListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


    }
}
