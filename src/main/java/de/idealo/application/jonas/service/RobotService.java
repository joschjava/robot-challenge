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
public class RobotService {

    private enum Action {POSITION, FORWARD, WAIT, TURNAROUND, RIGHT}

    ;

    private Position position;

    private List<Position> history = new ArrayList<>();

    public Position processInput(String input) {
        position = new Position(0, 0, Rotation.EAST);
        history.clear();
        history.add(position);
        parseInput(input);
        return position;
    }

    public String getHistoryInUnityFormat() {
        return history.stream().map(Position::toUnityFormat).collect(Collectors.joining("$"));
    }

    private void parseInput(String input) {
        if (input == null || input.isBlank()) {
            return;
        }
        String[] lines = input.replace("\r", "").split("\n"); //TODO Check if this is everywhere, and not that there is only \n somewhere
        for (String line : lines) {
            line = removeComment(line);
            position = parseLine(line);
            history.add(new Position(position.getX(), position.getY(), position.getRotation()));
        }
    }

    /**
     * Removes comment from string
     *
     * @param line The line to remove the comment from
     */
    private String removeComment(String line) {
        int commentIndex = line.indexOf("//"); // TODO put this into a variable
        if (commentIndex >= 0) {
            line = line.substring(0, commentIndex);
        }
        return line.trim();
    }


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
