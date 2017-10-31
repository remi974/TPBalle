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

    /**
     * The List of Items on the screen.
     */
    private ListView mListView;

    /**
     * The scores of previous players.
     */
    private List<Score> scores = null;

    /**
     * The adapter to personnalize how you manage the elements of each row.
     */
    private ScoresAdapter adapter;

    /**
     * Called when starting activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores); //Link the activity with the layout you want to show.

        scores = new ArrayList<Score>();
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);

        //Show all items in the List on the screen.
        afficherListeScores();
    }

    /**
     * Used to manage your menu linked with a layout you designed.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_scores, menu);
        return true;
    }

    /**
     * Called when you click an item on the menu.
     * @param item The Selected Item.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {

            //Sort the List from best to lowest score.
            case R.id.score_desc:

                Collections.sort(scores, Sort.ORDER_BY_SCORE_DESC);
                adapter.notifyDataSetChanged(); //Send a notification to let the listeners know that data have been updated.

                return true;

            //Sort the List in alphabetical order.
            case R.id.name_asc:

                Collections.sort(scores, Sort.ORDER_BY_JOUEUR_ASC);
                adapter.notifyDataSetChanged(); //Send a notification to let the listeners know that data have been updated.

                return true;

            //Sort the List from newest to oldest game.
            case R.id.date_desc:

                Collections.sort(scores, Sort.ORDER_BY_DATE_DESC);
                adapter.notifyDataSetChanged();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Parse data from XML to get the Scores and set the adapter to the list to show details of each player's game.
     */
    private void afficherListeScores(){

        //Read scores XML holding previous games stored in ExternalCache.
        ScoresTPBalleXMLParser parser = new ScoresTPBalleXMLParser();
        try {
            scores = parser.parse(new FileInputStream(new File(this.getExternalCacheDir() + "scores.xml")));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Default sorting set to best scores to lowest.
        Collections.sort(scores, Sort.ORDER_BY_SCORE_DESC);

        adapter = new ScoresAdapter(ScoresActivity.this, scores);
        adapter.notifyDataSetChanged();

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has been clicked.
     * Implementers can call getItemAtPosition(position) if they need to access the data associated with the selected item.
     *
     * This will start a new MapActivity using the item that was clicked.
     *
     * @param adapterView The AdapterView where the click happened.
     * @param view The view within the AdapterView that was clicked (this will be a view provided by the adapter).
     * @param i The position of the view in the adapter.
     * @param l The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        startScoresActivity(i);
    }

    /**
     * Launching new MapsActivity storing the position of the item in the list in an intent.
     * @param position The position of the item in the list.
     * */
    private void startScoresActivity(int position) {
        Intent i = new Intent(ScoresActivity.this, MapsActivity.class);
        i.putExtra("position", position);
        startActivity(i);
    }
}
