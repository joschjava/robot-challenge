package de.idealo.application.jonas.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {

    public enum Rotation {
        NORTH, EAST, SOUTH, WEST;

        private static final Rotation[] values = values();

        private Rotation rotateRight() {
            return rotateRight(1);
        }

        /**
         * Rotates position a specified number of times
         *
         * @param times Number of times to rotate
         * @return New Position with changed rotation
         */
        private Rotation rotateRight(int times) {
            return values[(this.ordinal() + times) % values.length];
        }
    }

    private int x;
    private int y;
    private Rotation rotation;

    public void forward(int steps) {
        switch (rotation) {
            case NORTH -> y -= steps;
            case EAST -> x += steps;
            case SOUTH -> y += steps;
            case WEST -> x -= steps;
        }
    }

    public void waitNow() {

    }

    public void rotateRight() {
        rotation = rotation.rotateRight();
    }

    public void turnAround() {
        rotation = rotation.rotateRight(2);
    }

    /**
     * Converts this position to a String that can be read by Unity
     * @return
     */
    public String toUnityFormat() {
        return String.join(";", String.valueOf(x), String.valueOf(y), rotation.toString());
    }

}
