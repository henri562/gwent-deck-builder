/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deck.gwent.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mengchuan Lin
 */
@ManagedBean
@ViewScoped
public class CardSearchBacking implements Serializable {

    private String searchKey;
    private String result;
    private final String FOLDER = "cards/";
    private final String JPG = ".jpg";
    private String cardImgPath = FOLDER + "gwent-card-backs" + JPG;

    private String cardName = "-";
    private String cardUnitStrength = "-";
    private String cardAbility = "-";
    private String cardType = "-";

    private final List<GwentCard> gwentCardList = new ArrayList<>(Arrays.asList(
             new GwentCard(cardName, cardUnitStrength, cardAbility, cardType)));

    /* These Java SQL objects are by default non-serializable attributes and
    therefore made transient to prevent Apache Tomcat from serializing them in
    HTTP sessions */
    private transient Connection conn;
    private transient PreparedStatement pstmt;
    private transient ResultSet rset;
    private static int count = 0;
    private String queryItem;
    private boolean isFound;

    /**
     * Creates a new instance of processSearch
     */
    public CardSearchBacking() {
        initDB();
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public String getOutput() {
        return "Card Details: " + result;
    }

    public String getCardImgPath() {
        return cardImgPath;
    }

    public List<GwentCard> getGwentCardList() {
        return gwentCardList;
    }

    /**
     * Processes user search and update image path
     */
    public void runQuery() {
        searchKey = searchKey.trim();
        queryItem = searchKey;
        if (fetchData()) {
            result = searchKey.substring(0, 1).toUpperCase()
                     + searchKey.substring(1);
            cardImgPath = FOLDER
                          + searchKey.toLowerCase().replace(' ', '-')
                          + JPG;
        }
        else {
            result = "Not found";
            gwentCardList.clear();
            gwentCardList.add(new GwentCard("-", "-", "-", "-"));
            cardImgPath = "error/not-found" + JPG;
        }
        searchKey = null; //clear input value in JSF form
        System.out.println("---Query [" + ++count + "]--- Item: " + queryItem
                            + "\tFound: " + isFound);
    }

    /**
     * Helper method for runQuery() to retrieve data from dB
     * @return
     */
    private boolean fetchData() {
        try {
            pstmt.setString(1, searchKey);
            rset = pstmt.executeQuery();

            //if cursor is before the first row in result set
            if (rset.isBeforeFirst()) {
                while (rset.next()) {
                    cardName = rset.getString("cardName");
                    cardUnitStrength = rset.getString("unitStrength");
                    cardAbility = rset.getString("ability");
                    cardType = rset.getString("cardType");
                    gwentCardList.clear();
                    gwentCardList.add(new GwentCard(cardName, cardUnitStrength,
                                                    cardAbility, cardType));
                }
                return isFound = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return isFound = false;
    }

    /**
     * Establishes connection to Oracle database and create an instance of a
     * SQL preparedStatement
     */
    private void initDB() {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                                               "linm", "rommel");
            System.out.println("Connected to database. Schema: " + conn.getSchema());
            //make preparedStatement
            pstmt = conn.prepareStatement("SELECT * from basic_gwent"
                                          + " WHERE cardName = ?");
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Auxiliary inner class to hold individual card data
     */
    public static class GwentCard implements Serializable {
        private final String name;
        private final String unitStrength;
        private final String ability;
        private final String type;

        public GwentCard(String name, String unitStrength, String ability,
                         String type) {
            this.name = name;
            this.unitStrength = unitStrength;
            this.ability = ability;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getUnitStrength() {
            return unitStrength;
        }

        public String getAbility() {
            return ability;
        }

        public String getType() {
            return type;
        }
    }
}
