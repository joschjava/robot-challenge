package de.idealo.application.jonas;

import de.idealo.application.jonas.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PositionIntegrationTests {

    @Test
    void roundTripShouldReturnAtSamePosition() {
        Position position = new Position(1,1, Position.Rotation.EAST);
        position.rotateRight();
    }

}
