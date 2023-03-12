package de.idealo.application.jonas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * The <b>Position</b> class represents the position of the robot in the grid. It contains methods for manipulating the robot's
 * position by moving it forward, rotating it, and converting its position to a Unity-readable string format.
 *
 * The <b>Position</b> class has a constructor that takes in the initial x and y coordinates of the robot and its orientation.
 *
 * The <b>Position</b> class contains the following public methods:
 *
 * <ul>
 *     <li><code>forward(int steps)</code>: Moves the robot forward a specified number of steps in the direction it is facing.</li>
 *     <li><code>waitNow()</code>: Makes no changes to the robot's position.</li>
 *     <li><code>rotateRight()</code>: Rotates the robot clockwise by 90 degrees (in the right direction).</li>
 *     <li><code>turnAround()</code>: Rotates the robot by 180 degrees.</li>
 *     <li><code>toUnityFormat()</code>: Converts the current position of the robot to a string in Unity-compatible format (X;Y;ROTATION).</li>
 *     <li><code>clone()</code>: Returns a new instance of the <b>Position</b> class with the same position and orientation as the original.</li>
 * </ul>
 */
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

    /**
     * Moves position forward by the defined steps
     */
    public void forward(int steps) {
        switch (rotation) {
            case NORTH -> y -= steps;
            case EAST -> x += steps;
            case SOUTH -> y += steps;
            case WEST -> x -= steps;
        }
    }

    /**
     * No change to robots position
     */
    public void waitNow() {

    }

    /**
     * Rotates robot clockwise / in the right direction.
     */
    public void rotateRight() {
        rotation = rotation.rotateRight();
    }

    /**
     * Rotate position by 180°
     */
    public void turnAround() {
        rotation = rotation.rotateRight(2);
    }

    /**
     * Converts this position to a String that can be read by Unity in the format
     * X;Y;ROTATION
     * @return Position as string readable by Unity
     */
    public String toUnityFormat() {
        return String.join(";", String.valueOf(x), String.valueOf(y), rotation.toString());
    }

    /**
     * Creates a instance of this position
     * @return New instance of position
     */
    public Position clone() {
        return new Position(x, y, rotation);
    }

}
