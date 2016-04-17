/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deck.gwent.beans;

import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Mengchuan Lin
 */
@ManagedBean (name = "bgImgRandomizer")
@RequestScoped
public class bgImgRandomizer {

    private String imgPath = "cover/";
    private final int UPPERBOUND = 3, LOWERBOUND = 1;
    /**
     * Creates a new instance of bgImgRandomizer
     */
    public bgImgRandomizer() {
        randomizeImg();
    }

    public String getImgPath() {
        return imgPath;
    }

    private void randomizeImg() {
        Random random = new Random();
        int dice = random.nextInt(UPPERBOUND) + LOWERBOUND;
        switch (dice) {
            case 1:
                imgPath += "gwent-deck.jpg";
                break;
            case 2:
                imgPath += "witcher-3-main-characters.jpg";
                break;
            case 3:
                imgPath += "geralt-ciri-triss-yen-portraits.jpg";
                break;
        }
    }
}
