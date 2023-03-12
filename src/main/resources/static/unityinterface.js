import {myGameInstance} from "./unityscript.js";

let history;
let historyCount = 0;
let interval;

function startAnimation() {
    historyCount = 0;
    setNextPosition();
    interval = setInterval(setNextPosition, 1100);
}

function setNextPosition() {
    if (myGameInstance != null) {
        myGameInstance.SendMessage('rover', 'SetPosition', history[historyCount++]);
    }
    if (historyCount >= history.length) {
        console.log('Clearing interval');
        clearInterval(interval)
    }
}

function sendCommands() {
    let commandInput = document.getElementById('textInput').value;
    let errorTextDOM = document.getElementById("error-text");
    errorTextDOM.innerText = "";
    fetch("http://localhost:8080/input", {
        "headers": {
            "content-type": "application/x-www-form-urlencoded",
        },
        "body": "textInput=" + encodeURI(commandInput),
        "method": "POST",
    }).then(res => {
        if (res.ok) {
            return res.text()
        }
        let message;
        if(res.status === 400){
            message = 'Command is empty or leads robot out of boundaries';
        } else {
            message = 'Unknown Error sending data, check your satellites';
        }
        errorTextDOM.innerText = message;
        throw new Error(message);
    }).then(data => {
        if (typeof data === 'string') {
            history = data.split('$');
            startAnimation();
        } else {
            throw new Error('Invalid data format received');
        }
    });
}

function quickCommand(command){
    document.getElementById("textInput").value = command;
    sendCommands();
}

function initQuickCommandButton(buttonId){
    document.getElementById(buttonId).addEventListener("click", function() {
        quickCommand(this.value);
    });
}

document.getElementById("commandButton").addEventListener("click", sendCommands)
initQuickCommandButton("qc-forward");
initQuickCommandButton("qc-right");
initQuickCommandButton("qc-turnaround");
initQuickCommandButton("qc-wait");
initQuickCommandButton("qc-position");
