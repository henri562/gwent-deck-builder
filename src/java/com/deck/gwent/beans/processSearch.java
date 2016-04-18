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
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mengchuan Lin
 */
@ManagedBean (name = "processSearch", eager = false)
@SessionScoped
public class processSearch implements Serializable {

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

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rset;
    private static int count = 0;

    /**
     * Creates a new instance of processSearch
     */
    public processSearch() {
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

    public void runQuery() {
        if (fetchData()) {
            result = searchKey.substring(0, 1).toUpperCase()
                     + searchKey.substring(1);
            cardImgPath = FOLDER
                          + searchKey.trim().toLowerCase().replace(' ', '-')
                          + JPG;
        }
        else {
            result = "Not found";
            gwentCardList.clear();
            gwentCardList.add(new GwentCard("-", "-", "-", "-"));
            cardImgPath = "error/not-found" + JPG;
        }
        searchKey = null; //clear input value in JSF form
        System.out.println("query #" + ++count + " run");
    }

    private boolean fetchData() {
        try {
            pstmt.setString(1, searchKey);
            rset = pstmt.executeQuery();

            if (rset.isBeforeFirst()) {
                //if cursor is before the first row in result set
                while (rset.next()) {
                    cardName = rset.getString("cardName");
                    cardUnitStrength = rset.getString("unitStrength");
                    cardAbility = rset.getString("ability");
                    cardType = rset.getString("cardType");
                    gwentCardList.clear();
                    gwentCardList.add(new GwentCard(cardName, cardUnitStrength,
                                                    cardAbility, cardType));
                }
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return false;
    }

    private void initDB() {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                                               "linm", "rommel");
            System.out.println("Database connection established.");
            //make preparedStatement
            pstmt = conn.prepareStatement("SELECT * from basic_gwent"
                                          + " WHERE cardName = ?");
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public static class GwentCard {
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
