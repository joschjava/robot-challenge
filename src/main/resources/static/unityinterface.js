import {myGameInstance} from "./unity_script.js";

let history;
let historyCount = 0;
let interval;

function startAnimation() {
    historyCount = 0;
    SetNextPosition();
    interval = setInterval(SetNextPosition, 1100);
}

function SetNextPosition() {
    if (myGameInstance != null) {
        myGameInstance.SendMessage('rover', 'SetPosition', history[historyCount++]);
    }
    if (historyCount >= history.length) {
        console.log('Clearing interval');
        clearInterval(interval)
    }
}

function SendCommands() {
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

document.getElementById("commandButton").addEventListener("click", SendCommands)