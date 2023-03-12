package de.idealo.application.jonas;

import de.idealo.application.jonas.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PositionUnitTests {


    @Test
    public void testForwardNorth() {
        Position pos = new Position(1, 2, Position.Rotation.NORTH);
        Position expectedPos = new Position(1, 0, Position.Rotation.NORTH);
        pos.forward(2);
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testForwardEast() {
        Position pos = new Position(1, 1, Position.Rotation.EAST);
        Position expectedPos = new Position(3, 1, Position.Rotation.EAST);
        pos.forward(2);
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testForwardSouth() {
        Position pos = new Position(3, 1, Position.Rotation.SOUTH);
        Position expectedPos = new Position(3, 4, Position.Rotation.SOUTH);
        pos.forward(3);
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testForwardWest() {
        Position pos = new Position(4, 2, Position.Rotation.WEST);
        Position expectedPos = new Position(0, 2, Position.Rotation.WEST);
        pos.forward(4);
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testRotateRightNorth() {
        Position pos = new Position(0, 0, Position.Rotation.NORTH);
        Position expectedPos = new Position(0, 0, Position.Rotation.EAST);
        pos.rotateRight();
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testRotateRightEast() {
        Position pos = new Position(0, 0, Position.Rotation.EAST);
        Position expectedPos = new Position(0, 0, Position.Rotation.SOUTH);
        pos.rotateRight();
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testRotateRightSouth() {
        Position pos = new Position(0, 0, Position.Rotation.SOUTH);
        Position expectedPos = new Position(0, 0, Position.Rotation.WEST);
        pos.rotateRight();
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testRotateRightWest() {
        Position pos = new Position(0, 0, Position.Rotation.WEST);
        Position expectedPos = new Position(0, 0, Position.Rotation.NORTH);
        pos.rotateRight();
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testTurnAround() {
        Position pos = new Position(0, 0, Position.Rotation.NORTH);
        Position expectedPos = new Position(0, 0, Position.Rotation.SOUTH);
        pos.turnAround();
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testWait(){
        Position pos = new Position(2, 3, Position.Rotation.NORTH);
        Position expectedPos = new Position(2, 3, Position.Rotation.NORTH);
        pos.waitNow();
        assertEquals(expectedPos, pos);
    }

    @Test
    public void testToUnityFormat(){
        Position pos = new Position(2, 3, Position.Rotation.NORTH);
        String unityFormat = pos.toUnityFormat();
        assertEquals("2;3;NORTH", unityFormat);
    }

    @Test
    public void testRobotRoute1() {
        Position robot = new Position(2, 2, Position.Rotation.EAST);

        robot.forward(2); // 4, 2
        robot.rotateRight(); // Rot SOUTH
        robot.forward(2); // 4,4
        robot.rotateRight(); //  ROT WEST
        robot.forward(4); // 0,4
        robot.waitNow();
        robot.rotateRight(); // Rot NORTH
        robot.forward(4); // 0, 0
        robot.turnAround();  // Rot SOUTH

        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
        assertEquals(Position.Rotation.SOUTH, robot.getRotation());
    }

    @Test
    public void testRobotRoute2() {
        Position robot = new Position(0, 0, Position.Rotation.NORTH);

        robot.rotateRight(); // (0, 0, EAST)
        robot.forward(2); // (2, 0, EAST)
        robot.waitNow(); // (2, 0, EAST)
        robot.rotateRight(); // (2, 0, SOUTH)
        robot.forward(3); // (2, 3, SOUTH)
        robot.waitNow(); // (2, 3, SOUTH)
        robot.turnAround(); // (2, 3, NORTH)
        robot.forward(1); // (2, 2, NORTH)
        robot.waitNow(); // (2, 2, NORTH)

        assertEquals(2, robot.getX());
        assertEquals(2, robot.getY());
        assertEquals(Position.Rotation.NORTH, robot.getRotation());
    }

}
