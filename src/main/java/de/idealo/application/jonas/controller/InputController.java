package de.idealo.application.jonas.controller;

import de.idealo.application.jonas.service.RobotService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class InputController {

    RobotService robotService;

    /**
     * Receives robot control text from the UI. Returns 400 if robot is out of boundaries or commands are empty
     * @param textInput Robot commands
     * @return If input is valid:<br>
     *  - String of taken path of Robot in format X1;Y1;ROTATION$X2;Y2;ROTATION <br>
     *  If input is invalid:<br>
     *      *  Empty string with bad request status code (400)
     */
    @PostMapping(path = "/input")
    public @ResponseBody ResponseEntity<String> postTextInput(@RequestParam String textInput) {
        boolean success = robotService.processInput(textInput);
        if(success) {
            String history = robotService.getHistoryInUnityFormat();
            return ResponseEntity.ok(history);
        } else {
            return ResponseEntity.badRequest().body("Input is empty or rover would drive outside the boundaries");
        }
    }

    /**
     * Handles request to main page
     * @param model Injected by Thymleaf
     * @return Html page
     */
    @GetMapping(path = "/")
    public String mainPage(Model model) {
        addDefaultText(model);
        return "index";
    }

    /**
     * Adds default text from the pdf to the model in attribute "defaultText"
     * @param model Model including default text
     */
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
