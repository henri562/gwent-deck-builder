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
@ManagedBean
@RequestScoped
public class ImgBacking {

    private final String FOLDER = "cover/";
    private String imgPath = FOLDER;
    private final int UPPERBOUND = 2, LOWERBOUND = 1;
    /**
     * Creates a new instance of ImgBacking
     */
    public ImgBacking() {
        randomizeImg();
    }

    public String getImgPath() {
        return imgPath;
    }

    /**
     * Randomizes the image displayed on the welcome page
     */
    private void randomizeImg() {
        Random random = new Random();
        int dice = random.nextInt(UPPERBOUND) + LOWERBOUND;
        switch (dice) {
            case 1:
                imgPath += "witcher-3-main-characters.jpg";
                break;
            case 2:
                imgPath += "geralt-ciri-triss-yen-portraits.jpg";
                break;
        }
    }
}
