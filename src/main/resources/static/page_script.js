import {myGameInstance} from "./unity_script.js";

let history;
let historyCount = 0;
let interval;

function startAnimation() {
    historyCount = 0;
    interval = setInterval(SetNextPosition, 1100);
}

function SetNextPosition() {
    console.log(historyCount);
    if (myGameInstance != null) {
        myGameInstance.SendMessage('rover', 'SetPosition', history[historyCount++]);
    }
    if (historyCount >= history.length) {
        console.log('Clearing interval');
        clearInterval(interval)
    }
}

function SendCommands() {
    let commandInput = document.getElementById('textInput').textContent;
    fetch("http://localhost:8080/input", {
        "headers": {
            "content-type": "application/x-www-form-urlencoded",
        },
        "body": "textInput=" + encodeURI(commandInput),
        "method": "POST",
    }).then(res => {
        if (!res.ok) {
            throw new Error('Error fetching data');
        }
        return res.text()
    }).then(data => {
        history = data.split('$');
        startAnimation();
    });
}

document.getElementById("commandButton").addEventListener("click", SendCommands)