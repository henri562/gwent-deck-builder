/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deck.gwent.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Mengchuan Lin
 */
@ManagedBean
@RequestScoped
public class processSearch {

    private String searchKey;
    private final String FOLDER = "cards/";
    private final String JPG = ".jpg";
    private String cardImgPath = FOLDER + "gwent-card-backs" + JPG;

    private String cardName = "-";
    private String cardUnitStrength = "-";
    private String cardAbility = "-";
    private String cardType = "-";

    private final List<GwentCard> gwentCardList = new ArrayList<>(Arrays.asList(
             new GwentCard(cardName, cardUnitStrength, cardAbility, cardType)));

    /**
     * Creates a new instance of processSearch
     */
    public processSearch() {
    }

    public void setSearchKey(String searchKey) {
            this.searchKey = searchKey;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public String getOutput() {
        searchKey = searchKey.substring(0, 1).toUpperCase()
                    + searchKey.substring(1);
        return "Card Details: " + searchKey;
    }

    public String getCardImgPath() {
        return cardImgPath;
    }

    public List<GwentCard> getGwentCardList() {
        return gwentCardList;
    }

    public void runQuery() {
        if (fetchData()) {
            cardImgPath = FOLDER
                          + searchKey.trim().toLowerCase().replace(' ', '-')
                          + JPG;
            generateCardDetails();
        }
        System.out.println("query run");
    }

    private void generateCardDetails() {
        cardName = "Yennefer of Vengerberg";
        cardUnitStrength = "7";
        cardAbility = "Medic";
        cardType = "Hero";
        gwentCardList.clear();
        gwentCardList.add(new GwentCard(cardName, cardUnitStrength, cardAbility,
                                        cardType));
    }

    private boolean fetchData() {
        return true;
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
