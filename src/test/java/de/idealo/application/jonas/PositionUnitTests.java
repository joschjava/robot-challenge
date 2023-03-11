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

}
