package com.example.wavefunctioncollapse;

import javafx.scene.Group;
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

    public cell(boolean collapsed, Integer[] options/*, int posX, int posY*/) {
        this.index = 0;
        this.collapsed = collapsed;
        this.options = options;
        //this.posX = posX;
        //this.posY = posY;
    }

    public void setImage(int index, int i, int j, Group group) {
        System.out.println("set: I: " + posX + " J: " + posY + " To: " + index);
        this.index = index;
        this.tile = new ImageView(tiles[index].getImage());
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
