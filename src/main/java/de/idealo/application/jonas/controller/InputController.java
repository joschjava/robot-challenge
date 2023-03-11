package de.idealo.application.jonas.controller;

import de.idealo.application.jonas.model.Position;
import de.idealo.application.jonas.service.RobotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class InputController {

    //TODO favicon?

    RobotService robotService;

    @PostMapping(path = "/input") //TODO think about name
    public @ResponseBody String postTextInput(@RequestParam String textInput, Model model) {
        Position finalPosition = robotService.processInput(textInput);
        String history = robotService.getHistoryInUnityFormat();
//        model.addAttribute("finalPosition", finalPosition);
//        model.addAttribute("history", history);
        addDefaultText(model);
        return history;
    }

    @GetMapping(path = "/")
    public String mainPage(Model model) {
        addDefaultText(model);
        return "index";
    }

    @GetMapping(path = "/legacy")
    public String legacyMainPage(Model model) {
        addDefaultText(model);
        return "indexold";
    }

    private void addDefaultText(Model model){
        model.addAttribute("defaultText", "POSITION 1 3 EAST //sets the initial position for the robot\n" +
                "FORWARD 3 //lets the robot do 3 steps forward\n" +
                "WAIT //lets the robot do nothing\n" +
                "TURNAROUND //lets the robot turn around\n" +
                "FORWARD 1 //lets the robot do 1 step forward\n" +
                "RIGHT //lets the robot turn right\n" +
                "FORWARD 2 //lets the robot do 2 steps forward");
    }

}
