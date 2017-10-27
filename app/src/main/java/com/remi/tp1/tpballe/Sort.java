package com.remi.tp1.tpballe;

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
            return - tag1.getDate().compareTo(tag2.getDate());
        }

    };

    public static final Comparator<Score> ORDER_BY_JOUEUR_ASC = new Comparator<Score>() {

        public int compare(Score tag1, Score tag2) {
            return tag1.getJoueur().compareTo(tag2.getJoueur());
        }

    };
}
