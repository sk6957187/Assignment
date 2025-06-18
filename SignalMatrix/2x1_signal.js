let input_1 = false;
let input_2 = false;
let locked = false;
let sw1State = false;
let sw2State = false;
let sw3State = false;

const signalStatus = document.getElementById("signalStatus");
const i_1 = document.getElementById("i_1");
const i_2 = document.getElementById("i_2");
const lock = document.getElementById("lock");
const sw_1 = document.getElementById("sw1");
const sw_2 = document.getElementById("sw2");
const sw_3 = document.getElementById("sw3");

let internalBits = {
    "i1-active": false,
    "i2-active": false,
    "sw1-active": false,
    "sw2-active": false,
    "sw3-active": false,
    "lock-status": false,
    "output-status": false,
};

function updateBits() {
    internalBits["i1-active"] = input_1;
    internalBits["i2-active"] = input_2;
    internalBits["sw1-active"] = (sw1State && !sw2State && !sw3State) ;
    internalBits["sw2-active"] = sw2State;
    internalBits["sw3-active"] = !sw1State && !sw2State && sw3State;
    internalBits["output-status"] =
        (internalBits["i1-active"] && internalBits["sw1-active"]) ||
        (internalBits["i2-active"] && internalBits["sw3-active"]);
    internalBits["lock-status"] = internalBits["output-status"];
}

function updateSignal() {
    updateBits();

    if (internalBits["output-status"]) {
        signalStatus.className = "badge text-success";
        signalStatus.innerText = "G";
    } else {
        signalStatus.className = "badge text-danger";
        signalStatus.innerText = "S";
    }

    if(internalBits["sw2-active"]){
        sw1State = false;
        sw3State = false;
        sw_1.className = "badge text-danger";
        sw_3.className = "badge text-danger";
        updateSignal();
    }

    if (internalBits["lock-status"]) {
        lock.className = "badge text-danger";
        lock.innerText = "Locked";
    } else {
        lock.className = "badge text-success";
        lock.innerText = "Unlocked";
    }
}

// Input buttons
i_1.addEventListener("click", () => {
    if (!internalBits["lock-status"]) {
        input_1 = !input_1;
        i_1.className = input_1 ? "badge text-success" : "badge text-danger";
        updateSignal();
    }
});

i_2.addEventListener("click", () => {
    if (!internalBits["lock-status"]) {
        input_2 = !input_2;
        i_2.className = input_2 ? "badge text-success" : "badge text-danger";
        updateSignal();
    }
});

// Switch buttons
sw_1.addEventListener("click", () => {
    if (!internalBits["lock-status"]) {
        sw1State = !sw1State;
        sw_1.className = sw1State ? "badge text-success" : "badge text-danger";
        updateSignal();
    }
});

sw_2.addEventListener("click", () => {
    if (!internalBits["lock-status"]) {
        sw2State = !sw2State;
        sw_2.className = sw2State ? "badge text-success" : "badge text-danger";
        updateSignal();
    }
});

sw_3.addEventListener("click", () => {
    if (!internalBits["lock-status"]) {
        sw3State = !sw3State;
        sw_3.className = sw3State ? "badge text-success" : "badge text-danger";
        updateSignal();
    }
});

// Lock button
lock.addEventListener("click", () => {
    if (!internalBits["lock-status"]) {
        locked = !locked;
        updateSignal();
    } else {
        // Reset all states
        input_1 = false;
        input_2 = false;
        locked = false;
        sw1State = false;
        sw2State = false;
        sw3State = false;
        i_1.className = "badge text-danger";
        i_2.className = "badge text-danger";
        sw_1.className = "badge text-danger";
        sw_2.className = "badge text-danger";
        sw_3.className = "badge text-danger";
        updateSignal();
    }
});
