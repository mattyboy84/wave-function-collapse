package com.example.wavefunctioncollapse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

public class HelloApplication extends Application {

    //int WIDTH = 512;
    //int HEIGHT = 512;

    int tileWidth = 64;
    int tileHeight = 64;

    //public final int res = (HEIGHT / tileHeight);
    public final int res = 20;

    int WIDTH = (tileWidth * res);
    int HEIGHT = (tileHeight * res);

    Group group = new Group();
    Scene scene = new Scene(group,WIDTH,HEIGHT);

    final int BLANK = 0;
    final int UP = 1;
    final int RIGHT = 2;
    final int DOWN = 3;
    final int LEFT = 4;

    ArrayList<cell> grid = new ArrayList<>();
    ArrayList<cell> nextGrid = new ArrayList<>();
    ArrayList<cell> copyGrid = new ArrayList<>();
    ArrayList<cell> optionsGrid = new ArrayList<>();

    Random random = new Random();

    public static HashMap<Integer, Integer[][]> rules = new HashMap<>();

    @Override
    public void start(Stage stage) {
        /*
        rules.put(0, new Integer[][]{
                new Integer[]{UP},
                new Integer[]{RIGHT},
                new Integer[]{DOWN},
                new Integer[]{LEFT}
        });
        rules.put(1, new Integer[][]{
                new Integer[]{RIGHT, DOWN, LEFT},
                new Integer[]{UP, DOWN, LEFT},
                new Integer[]{ DOWN},
                new Integer[]{UP, RIGHT, DOWN}
        });
        rules.put(2, new Integer[][]{
                new Integer[]{RIGHT, DOWN, LEFT},
                new Integer[]{UP, DOWN, LEFT},
                new Integer[]{RIGHT, UP, LEFT},
                new Integer[]{ LEFT}
        });
        rules.put(3, new Integer[][]{
                new Integer[]{ UP},
                new Integer[]{UP, LEFT, DOWN},
                new Integer[]{UP, RIGHT, LEFT},
                new Integer[]{UP, RIGHT, DOWN}
        });
        rules.put(4, new Integer[][]{
                new Integer[]{RIGHT, DOWN, LEFT},
                new Integer[]{ RIGHT},
                new Integer[]{UP, RIGHT, LEFT},
                new Integer[]{DOWN, RIGHT, UP}
        });
        */
        rules.put(0, new Integer[][]{
                new Integer[]{BLANK, UP},
                new Integer[]{BLANK, RIGHT},
                new Integer[]{BLANK, DOWN},
                new Integer[]{BLANK, LEFT}
        });
        rules.put(1, new Integer[][]{
                new Integer[]{RIGHT, DOWN, LEFT},
                new Integer[]{UP, DOWN, LEFT},
                new Integer[]{BLANK, DOWN},
                new Integer[]{UP, RIGHT, DOWN}
        });
        rules.put(2, new Integer[][]{
                new Integer[]{RIGHT, DOWN, LEFT},
                new Integer[]{UP, DOWN, LEFT},
                new Integer[]{RIGHT, UP, LEFT},
                new Integer[]{BLANK, LEFT}
        });
        rules.put(3, new Integer[][]{
                new Integer[]{BLANK, UP},
                new Integer[]{UP, LEFT, DOWN},
                new Integer[]{UP, RIGHT, LEFT},
                new Integer[]{UP, RIGHT, DOWN}
        });
        rules.put(4, new Integer[][]{
                new Integer[]{RIGHT, DOWN, LEFT},
                new Integer[]{BLANK, RIGHT},
                new Integer[]{UP, RIGHT, LEFT},
                new Integer[]{DOWN, RIGHT, UP}
        });


            for (int j = 0; j < res * res; j++) {
                grid.add(new cell(false, new Integer[]{ BLANK, UP, RIGHT, DOWN, LEFT}));
            }

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
                    options = new ArrayList<>(Arrays.asList( BLANK, UP, RIGHT, DOWN, LEFT));
                    //ArrayList<Integer> validOptions = new ArrayList<>();
                    //look up
                    if (j > 0){
                        cell up = grid.get(i + (j - 1) * res);
                        ArrayList<Integer> validOptions = new ArrayList<>();
                        for (int k = 0; k <up.options.length ; k++) {
                            Integer[] valid = rules.get(up.options[k])[2];
                            validOptions.addAll(List.of(valid));
                        }
                        checkValid(options, validOptions);
                    }
                    //look right
                    if (i < res - 1){
                        cell right = grid.get(i + 1 + j * res);
                        ArrayList<Integer> validOptions = new ArrayList<>();
                        for (int k = 0; k <right.options.length ; k++) {
                            Integer[] valid = rules.get(right.options[k])[3];
                            validOptions.addAll(List.of(valid));
                        }
                        checkValid(options, validOptions);
                    }
                    //look down
                    if (j < res - 1){
                        cell down = grid.get(i + (j + 1) * res);
                        ArrayList<Integer> validOptions = new ArrayList<>();
                        for (int k = 0; k <down.options.length ; k++) {
                            Integer[] valid = rules.get(down.options[k])[0];
                            validOptions.addAll(List.of(valid));
                        }
                        checkValid(options, validOptions);
                    }
                    //look left
                    if (i > 0){
                        cell left = grid.get(i - 1 + j * res);
                        ArrayList<Integer> validOptions = new ArrayList<>();
                        for (int k = 0; k <left.options.length ; k++) {
                            Integer[] valid = rules.get(left.options[k])[1];
                            validOptions.addAll(List.of(valid));
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
                    cellToCheck.setImage(index, i, j, group);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

