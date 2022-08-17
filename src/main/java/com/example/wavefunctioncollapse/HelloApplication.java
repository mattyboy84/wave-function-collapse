package com.example.wavefunctioncollapse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

public class HelloApplication extends Application {

    //int WIDTH = 512;
    //int HEIGHT = 512;

    public static int tileWidth = 56;
    public static int tileHeight = 56;

    //public final int res = (HEIGHT / tileHeight);
    public final int res = 16;

    int WIDTH = (tileWidth * res);
    int HEIGHT = (tileHeight * res);

    Group group = new Group();
    Scene scene = new Scene(group,WIDTH,HEIGHT);

    ArrayList<cell> grid = new ArrayList<>();
    ArrayList<cell> nextGrid = new ArrayList<>();
    ArrayList<cell> copyGrid = new ArrayList<>();
    ArrayList<cell> optionsGrid = new ArrayList<>();

    ArrayList<Tile> tiles = new ArrayList<>();

    Random random = new Random();

    public static HashMap<Integer, Integer[][]> rules = new HashMap<>();

    @Override
    public void start(Stage stage) {

        /* basic
        ImageView[] tileImages = {
                new ImageView("file:tiles/basic/blank.png"),
                new ImageView("file:tiles/basic/up.png"),
        };
        tiles.add(new Tile(tileImages[0],new Integer[] {0, 0, 0, 0}, 0));
        tiles.add(new Tile(tileImages[1],new Integer[] {1, 1, 0, 1}, 0));
        tiles.add(new Tile(tileImages[1],new Integer[] {1, 1, 0, 1}, 1));
        tiles.add(new Tile(tileImages[1],new Integer[] {1, 1, 0, 1}, 2));
        tiles.add(new Tile(tileImages[1],new Integer[] {1, 1, 0, 1}, 3));
        */

        ImageView[] tileImages = {
                new ImageView("file:tiles/circuit/0.png"),
                new ImageView("file:tiles/circuit/1.png"),
                new ImageView("file:tiles/circuit/2.png"),
                new ImageView("file:tiles/circuit/3.png"),
                //new ImageView("file:tiles/circuit/4.png"),
                //new ImageView("file:tiles/circuit/5.png"),
                new ImageView("file:tiles/circuit/6.png"),
                new ImageView("file:tiles/circuit/7.png"),
                new ImageView("file:tiles/circuit/8.png"),
                new ImageView("file:tiles/circuit/9.png"),
                new ImageView("file:tiles/circuit/10.png"),
                new ImageView("file:tiles/circuit/11.png"),
                new ImageView("file:tiles/circuit/12.png"),
        };
        tiles.add(new Tile(tileImages[0], new Integer[] {0, 0, 0, 0}, 0));
        tiles.add(new Tile(tileImages[1], new Integer[] {1, 1, 1, 1}, 0));
        tiles.add(new Tile(tileImages[2], new Integer[] {1, 2, 1, 1}, 0));
        tiles.add(new Tile(tileImages[3], new Integer[] {1, 3, 1, 3}, 0));
        tiles.add(new Tile(tileImages[4], new Integer[] {1, 2, 1, 2}, 0));
        tiles.add(new Tile(tileImages[5], new Integer[] {3, 2, 3, 2}, 0));
        tiles.add(new Tile(tileImages[6], new Integer[] {3, 1, 2, 1}, 0));
        tiles.add(new Tile(tileImages[7], new Integer[] {2, 2, 1, 2}, 0));
        tiles.add(new Tile(tileImages[8], new Integer[] {2, 2, 2, 2}, 0));
        tiles.add(new Tile(tileImages[9], new Integer[] {2, 2, 1, 1}, 0));
        tiles.add(new Tile(tileImages[10], new Integer[] {1, 2, 1, 2}, 0));

        for (int i = 2; i < 11; i++) {
            for (int j = 1; j < 4; j++) {
                tiles.add(new Tile(tileImages[i], tiles.get(i).edges, j));
            }
        }
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.analyze(tiles);
        }
        //
        for (int j = 0; j < res * res; j++) {
            grid.add(new cell(false, tiles.size()));
        }
        //


        //grid.get(2).collapsed = true;
        //grid.get(2).options = new int[]{UP};
        //grid.get(1).collapsed = true;
        //grid.get(1).options = new int[]{LEFT};
        //System.out.println(grid);
        //
        //sort
        //draw();
        //for (int i = 0; i < res; i++) {
        //    for (int j = 0; j < res; j++) {
        //        int index = i + j * res;
        //        grid.get(index).updateOptions();
        //    }
        //}

        scene.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                draw();
            }
        });

        stage.setScene(scene);
        stage.show();
        //
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            draw();
        }));
        timeline.setCycleCount(res * res);
        timeline.play();
    }

    ArrayList<Integer> options;

    private void draw() {
        System.out.println("drawing");
        copyGrid = (ArrayList<cell>) new ArrayList<>(grid).stream().filter(i -> !i.collapsed).collect(Collectors.toList());
        copyGrid.sort((o1, o2) -> {
            if (o1.options.length == o2.options.length)
                return 0;
            return o1.options.length < o2.options.length ? -1 : 1;
        });
        System.out.println("copied grid: " + copyGrid);
        if (copyGrid.size() == 0) return;

        //get least options
        optionsGrid = new ArrayList<>(grid);
        int leastOptions = copyGrid.get(0).options.length;
        optionsGrid = (ArrayList<cell>) optionsGrid.stream().filter(i -> i.options.length == leastOptions).collect(Collectors.toList());
        optionsGrid = (ArrayList<cell>) optionsGrid.stream().filter(i -> !i.collapsed).collect(Collectors.toList());

        System.out.println("options: " + optionsGrid);
        //
        cell cellToCollapse = optionsGrid.get(random.nextInt(optionsGrid.size()));
        System.out.println(cellToCollapse);
        cellToCollapse.collapsed = true;
        int option = cellToCollapse.options[random.nextInt(cellToCollapse.options.length)];
        cellToCollapse.options = new Integer[]{option};

        collapseCheck();
        nextGrid = new ArrayList<>();
        ///*
        for (int j = 0; j < res; j++) {
            for (int i = 0; i < res; i++) {
                int index = i + (j * res);
                if (grid.get(index).collapsed){
                    nextGrid.add(grid.get(index));
                }else {
                    options = new ArrayList<>();
                    for (int k = 0; k < tiles.size(); k++) {
                        options.add(k);
                    }
                    //ArrayList<Integer> validOptions = new ArrayList<>();
                    //look up
                    if (j > 0){
                        cell up = grid.get(i + (j - 1) * res);
                        ArrayList<Integer> validOptions = new ArrayList<>();
                        for (int k = 0; k <up.options.length ; k++) {
                            ArrayList<Integer> valid = tiles.get(up.options[k]).down;
                            validOptions.addAll(valid);
                        }
                        checkValid(options, validOptions);
                    }
                    //look right
                    if (i < res - 1){
                        cell right = grid.get(i + 1 + j * res);
                        ArrayList<Integer> validOptions = new ArrayList<>();
                        for (int k = 0; k <right.options.length ; k++) {
                            ArrayList<Integer> valid = tiles.get(right.options[k]).left;
                            validOptions.addAll(valid);
                        }
                        checkValid(options, validOptions);
                    }
                    //look down
                    if (j < res - 1){
                        cell down = grid.get(i + (j + 1) * res);
                        ArrayList<Integer> validOptions = new ArrayList<>();
                        for (int k = 0; k <down.options.length ; k++) {
                            ArrayList<Integer> valid = tiles.get(down.options[k]).up;
                            validOptions.addAll(valid);
                        }
                        checkValid(options, validOptions);
                    }
                    //look left
                    if (i > 0){
                        cell left = grid.get(i - 1 + j * res);
                        ArrayList<Integer> validOptions = new ArrayList<>();
                        for (int k = 0; k <left.options.length ; k++) {
                            ArrayList<Integer> valid = tiles.get(left.options[k]).right;
                            validOptions.addAll(valid);
                        }
                        checkValid(options, validOptions);
                    }
                    //Integer[] newOptions = checkValid(options, validOptions);
                    System.out.println(options + " for: I: " + i + " J: " + j);
                    grid.get(index).updateOptions(options.toArray(new Integer[0]));
                    nextGrid.add(grid.get(index));
                }
            }
        }
        grid = nextGrid;
        //*/
    }


    private void checkValid(ArrayList<Integer> options, ArrayList<Integer> valid) {
        for (int i = options.size() - 1; i >= 0; i--) {
            if (!valid.contains(options.get(i))) {
                options.remove(options.get(i));
            }
        }
    }

    private void collapseCheck() {
        for (int j = 0; j < res; j++) {
            for (int i = 0; i < res; i++) {
                cell cellToCheck = grid.get(i + j * res);
                if (cellToCheck.collapsed) {
                    int index = cellToCheck.options[0];
                    cellToCheck.setImage(tiles.get(index).image.getImage(),tiles.get(index).rotation, i, j, group);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

