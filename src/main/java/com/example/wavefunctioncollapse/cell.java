package com.example.wavefunctioncollapse;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class cell {

    final int BLANK = 0;
    final int UP = 1;
    final int RIGHT = 2;
    final int DOWN = 3;
    final int LEFT = 4;

    int tileWidth = 64;
    int tileHeight = 64;

    ImageView[] tiles = {
            new ImageView("file:tiles/blank.png"),
            new ImageView("file:tiles/up.png"),
            new ImageView("file:tiles/right.png"),
            new ImageView("file:tiles/down.png"),
            new ImageView("file:tiles/left.png")
    };

    public boolean collapsed;
    public Integer[] options;
    public ImageView tile;
    public int index;
    int posX, posY;
    Integer[] options2;

    public cell(boolean collapsed, Integer[] options, int num/*, int posX, int posY*/) {
        this.index = 0;
        this.collapsed = collapsed;
        this.options = options;
        //this.posX = posX;
        //this.posY = posY;
        options2 = new Integer[num];
        for (int i = 0; i < num; i++) {
            options2[i] = num;
        }

    }

    public void setImage(Image image, int rotation, int i, int j, Group group) {
        System.out.println("set: I: " + posX + " J: " + posY + " To: " + index);
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
