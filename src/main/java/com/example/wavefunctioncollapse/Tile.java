package com.example.wavefunctioncollapse;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Tile {
    ImageView image;
    Integer[] edges;
    int rotation;

    ArrayList<Integer> up = new ArrayList<>();
    ArrayList<Integer> down = new ArrayList<>();
    ArrayList<Integer> left = new ArrayList<>();
    ArrayList<Integer> right = new ArrayList<>();

    public Tile(ImageView image, Integer[] edges, int rotation) {
        this.image = image;
        this.rotation = rotation;
        this.edges = new Integer[edges.length];
        int length = edges.length;
        for (int i = 0; i < length; i++) {
            this.edges[i] = edges[(i - rotation + length) % 4];
        }
    }

    public void analyze(ArrayList<Tile> tiles) {
        //up
        for (int i = 0; i < tiles.size(); i++) {
            //up
            if (tiles.get(i).edges[2] == this.edges[0]) {
                this.up.add(i);
            }
            //right
            if (tiles.get(i).edges[3] == this.edges[1]) {
                this.right.add(i);
            }
            //down
            if (tiles.get(i).edges[0] == this.edges[2]) {
                this.down.add(i);
            }
            //left
            if (tiles.get(i).edges[1] == this.edges[3]) {
                this.left.add(i);
            }
        }
    }

    public Tile rotate(int rotation){
        ImageView newImage = new ImageView(image.getImage());
        newImage.setRotate(90 * rotation);
        
        Integer[] newEdges = new Integer[edges.length];
        int length = edges.length;
        for (int i = 0; i < length; i++) {
            newEdges[i] = edges[(i - rotation + length) % 4];
        }

        return new Tile(newImage, newEdges, rotation);
    }


}
