package de.idealo.application.jonas.service;

import de.idealo.application.jonas.model.Position;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.idealo.application.jonas.model.Position.Rotation;

@Service
@Getter
/**
 * RobotService provides a set of methods for processing and parsing robot commands
 * and maintaining a record of the robot's movements.
 */
public class RobotService {

    private enum Action {POSITION, FORWARD, WAIT, TURNAROUND, RIGHT};

    private Position position = new Position(0,0, Rotation.EAST);

    private final List<Position> history = new ArrayList<>();

    /**
     * Processes the input from robot command.
     * @param input
     * @return true if robot command was inside of defined boundaries, false otherwise
     */
    public boolean processInput(String input) {
        Position backupPos = position.clone();
        history.clear();
        boolean success = parseInput(input);
        if(!success){
            position = backupPos;
        }
        return success;
    }

    /**
     * Returns a concatinated string of positions that can be read by unity script in the format
     * X1;Y1;ROTATION1$X2;Y2;ROTATION2
     * @return Concatinated string of robot positions for Unity
     */
    public String getHistoryInUnityFormat() {
        return history.stream().map(Position::toUnityFormat).collect(Collectors.joining("$"));
    }

    /**
     * Parses the input from robot command string
     * @param input Robot commmands
     * @return true if position is in boundaries, false otherwise
     */
    private boolean parseInput(String input) {
        if (input == null || input.isBlank()) {
            return false;
        }
        String[] lines = input.replace("\r", "").split("\n");
        for (String line : lines) {
            line = removeComment(line);
            position = parseLine(line);
            if (position.getX() < 0 || position.getX() > 4
                    || position.getY() < 0 || position.getY() > 4) {
                return false;
            }
            history.add(new Position(position.getX(), position.getY(), position.getRotation()));
        }
        return true;
    }

    /**
     * Removes comment from robot command string
     * @param line The line to remove the comment from
     */
    private String removeComment(String line) {
        int commentIndex = line.indexOf("//"); // TODO put this into a variable
        if (commentIndex >= 0) {
            line = line.substring(0, commentIndex);
        }
        return line.trim();
    }

    /**
     * Parses a single string containing a robot command and returns robot
     * @param line Robot command line
     * @return New position after executing robot command
     */
    private Position parseLine(String line) {
        String[] parameters = line.split(" ");
        Action action = Action.valueOf(parameters[0]); // TODO handle exception?
        switch (action) {
            case POSITION -> {
                int x = Integer.parseInt(parameters[1]);
                int y = Integer.parseInt(parameters[2]);
                Rotation rotation = Rotation.valueOf(parameters[3]);
                return new Position(x, y, rotation);
            }
            case FORWARD -> {
                int steps = Integer.parseInt(parameters[1]); // TODO handle exception
                position.forward(steps);
            }
            case WAIT -> position.waitNow();
            case TURNAROUND -> position.turnAround();
            case RIGHT -> position.rotateRight();
        }
        return position;
    }


}
