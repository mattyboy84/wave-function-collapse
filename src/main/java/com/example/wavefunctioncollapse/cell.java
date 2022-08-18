package com.example.wavefunctioncollapse;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;

public class cell {

    int tileWidth = HelloApplication.tileWidth;
    int tileHeight = HelloApplication.tileHeight;

    public boolean collapsed;
    public Integer[] options;
    public ImageView tile;
    public int index;

    public cell(boolean collapsed, int num/*, int posX, int posY*/) {
        this.index = 0;
        this.collapsed = collapsed;
        this.options = new Integer[num];
        for (int i = 0; i < num; i++) {
            this.options[i] = i;
        }
    }

    public void setImage(Image image, int rotation, int i, int j, Group group) {
        //System.out.println("set: I: " + posX + " J: " + posY + " To: " + index);
        //this.index = index;
        this.tile = new ImageView(image);
        this.tile.setRotate(90 * rotation);
        this.tile.relocate((i*tileWidth),(j*tileHeight));
        group.getChildren().add(this.tile);
    }

    public String toString() {
        return ("options: " + options.length);
    }

    public void updateOptions(Integer[] options) {
        this.options = options;
    }
}
