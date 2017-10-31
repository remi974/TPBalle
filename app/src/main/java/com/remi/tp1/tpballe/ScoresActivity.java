package com.remi.tp1.tpballe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoresActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;

    private List<Score> scores = null;

    private ScoresAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        scores = new ArrayList<Score>();

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);

        afficherListeScores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_scores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.score_desc:

                Collections.sort(scores, Sort.ORDER_BY_SCORE_DESC);
                adapter.notifyDataSetChanged();

                return true;

            case R.id.name_asc:

                Collections.sort(scores, Sort.ORDER_BY_JOUEUR_ASC);
                adapter.notifyDataSetChanged();

                return true;

            case R.id.date_desc:

                Collections.sort(scores, Sort.ORDER_BY_DATE_DESC);
                adapter.notifyDataSetChanged();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void afficherListeScores(){
        //List<Tweet> tweets = genererTweets();

        ScoresTPBalleXMLParser parser = new ScoresTPBalleXMLParser();
        try {
            scores = parser.parse(new FileInputStream(new File(this.getExternalCacheDir() + "scores.xml")));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(scores, Sort.ORDER_BY_SCORE_DESC);

        adapter = new ScoresAdapter(ScoresActivity.this, scores);
        adapter.notifyDataSetChanged();

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        startScoresActivity(i);
    }

    /**
     * Launching new ScoresActivity
     * */
    private void startScoresActivity(int position) {
        Intent i = new Intent(ScoresActivity.this, MapsActivity.class);
        i.putExtra("position", position);
        startActivity(i);
    }
}
