package com.example.wavefunctioncollapse;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Tile {
    ImageView image;
    String[] edges;
    int rotation;

    ArrayList<Integer> up = new ArrayList<>();
    ArrayList<Integer> down = new ArrayList<>();
    ArrayList<Integer> left = new ArrayList<>();
    ArrayList<Integer> right = new ArrayList<>();

    public Tile(ImageView image, String[] edges, int rotation) {
        this.image = image;
        this.rotation = rotation;
        this.edges = new String[edges.length];
        int length = edges.length;
        for (int i = 0; i < length; i++) {
            this.edges[i] = edges[(i - rotation + length) % 4];
        }
    }

    private Boolean compareEdge(String edge1, String edge2){
        return edge1.equals(new StringBuilder(edge2).reverse().toString());

    }

    //creates the adjacency rules
    public void analyze(ArrayList<Tile> tiles) {
        //up
        for (int i = 0; i < tiles.size(); i++) {
            //up
            if (compareEdge(tiles.get(i).edges[2], this.edges[0])) {
                this.up.add(i);
            }
            //right
            if (compareEdge(tiles.get(i).edges[3], this.edges[1])) {
                this.right.add(i);
            }
            //down
            if (compareEdge(tiles.get(i).edges[0], this.edges[2])) {
                this.down.add(i);
            }
            //left
            if (compareEdge(tiles.get(i).edges[1], this.edges[3])) {
                this.left.add(i);
            }
        }
    }
}
