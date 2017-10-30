package com.remi.tp1.tpballe;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Created by Rémi on 27/10/2017.
 */

public class Sort {

    public static final Comparator<Score> ORDER_BY_SCORE_DESC = new Comparator<Score>() {

        public int compare(Score tag1, Score tag2) {
            return - tag1.getScore().compareTo(tag2.getScore());
        }

    };

    public static final Comparator<Score> ORDER_BY_DATE_DESC = new Comparator<Score>() {

        public int compare(Score tag1, Score tag2) {

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date parsedDate1 = null;
            Date parsedDate2 = null;

            try {
                parsedDate1 = formatter.parse(tag1.getDate());
                parsedDate2 = formatter.parse(tag2.getDate());

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return - parsedDate1.compareTo(parsedDate2);
        }

    };

    public static final Comparator<Score> ORDER_BY_JOUEUR_ASC = new Comparator<Score>() {

        public int compare(Score tag1, Score tag2) {
            return tag1.getJoueur().compareTo(tag2.getJoueur());
        }

    };
}
