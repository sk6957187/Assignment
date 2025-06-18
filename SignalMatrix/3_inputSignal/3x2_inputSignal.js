let input_1 = false;
let input_2 = false;
let input_3 = false;
let switch_1 = false;
let switch_2 = false;
let switch_3 = false;
let signal_L = false;
let signal_R = false;
let locked = false;

let i_1 = document.getElementById("i_1");
let i_2 = document.getElementById("i_2");
let i_3 = document.getElementById("i_3");
let sw_1 = document.getElementById("sw_1");
let sw_2 = document.getElementById("sw_2");
let sw_3 = document.getElementById("sw_3");
let signal_1 = document.getElementById("signal_1");
let signal_2 = document.getElementById("signal_2");
let lock = document.getElementById("lock");
let o_1 = document.getElementById("o_1");
let o_2 = document.getElementById("o_2");

let internalBits = {
    "input_1" : false,
    "input_2" : false,
    "input_3" : false,
    "switch_1" : false,
    "switch_2" : false,
    "switch_3" : false,
    "signal_L" : false,
    "signal_R" : false,
    "locked" : false,
    "output_L": false,
    "output_R" : false,
}

function updateBits(){
    internalBits["input_1"] = input_1;
    internalBits["input_2"] = input_2;
    internalBits["input_3"] = input_3;
    internalBits["switch_1"] = switch_1 && !switch_2 && !switch_3;
    internalBits["switch_2"] = switch_2 && !switch_1 && !switch_3;
    internalBits["switch_3"] = switch_3 && !switch_1 && !switch_2;
    internalBits["signal_L"] = signal_L && !signal_R;
    internalBits["signal_R"] = signal_R && !signal_L;
    internalBits["output_L"] = internalBits["signal_L"] && ((internalBits["input_1"] && internalBits["switch_1"]) || (internalBits["input_2"] && internalBits["switch_2"]) || (internalBits["input_3"] && internalBits["switch_3"]));
    internalBits["output_R"] = internalBits["signal_R"] && ((internalBits["input_1"] && internalBits["switch_1"]) || (internalBits["input_2"] && internalBits["switch_2"]) || (internalBits["input_3"] && internalBits["switch_3"]));
    internalBits["locked"] = internalBits["output_L"] || internalBits["output_R"];
};

function updateSignal(){
    if(internalBits["signal_2-status"]){
        o_2.className = "btn btn-success";
        o_2.innerText = "G";
    } else {
        o_2.className = "btn btn-danger";
        o_2.innerText = "O"
    }
    updateBits();
    if(internalBits["output_L"]){
        o_1.className = "btn btn-success";
        o_1.innerText = "G";
    }else {
        o_1.className = "btn btn-danger";
        o_1.innerText = "O_L";
    }
    if(internalBits["output_R"]){
        o_2.className = "btn btn-success";
        o_2.innerText = "G";
    } else {
        o_2.className = "btn btn-danger";
        o_2.innerText = "O_L";
    }
    if(internalBits["locked"]){
        lock.className = "btn btn-danger";
        lock.innerText = "Locked";
    } else {
        lock.className = "btn btn-success";
        lock.innerText = "Unlocked";
    }
};

i_1.addEventListener("click", () => {
    input_1 = !input_1;
    i_1.className= input_1 ? "btn btn-success" : "btn btn-danger";
    updateSignal();
})
i_2.addEventListener("click", () => {
    input_2 = !input_2;
    i_2.className= input_2 ? "btn btn-success" : "btn btn-danger";
    updateSignal();
})
i_3.addEventListener("click", () => {
    input_3 = !input_3;
    i_3.className= input_3 ? "btn btn-success" : "btn btn-danger";
    updateSignal();
})
sw_1.addEventListener("click", () => {
    if(!internalBits["locked"]){
        switch_1 = !switch_1;
        sw_1.className= switch_1 ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

sw_2.addEventListener("click", () => {
    if(!internalBits["locked"]){
        switch_2 = !switch_2;
        sw_2.className= switch_2 ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

sw_3.addEventListener("click", () => {
    if(!internalBits["locked"]){
        switch_3 = !switch_3;
        sw_3.className= switch_3 ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

signal_1.addEventListener("click", () => {
    if(!internalBits["locked"]){
        signal_L = !signal_L;
        signal_1.className = signal_L ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

signal_2.addEventListener("click", () => {
    if(!internalBits["locked"]){
        signal_R = !signal_R;
        signal_2.className = signal_R ? "btn btn-success" : "btn btn-danger";
    }
})

lock.addEventListener("click", () => {
    if(internalBits["locked"]){
        locked = !locked;
        lock.className = locked ? "btn btn-success" : "btn btn-danger";
        input_1 = false;
        input_2 = false;
        input_3 = false;
        switch_1 = false;
        switch_2 = false;
        switch_3 = false;
        signal_L = false;
        signal_R = false;

        i_1.className = "btn btn-danger";
        i_2.className = "btn btn-danger";
        i_3.className = "btn btn-danger"; 
        sw_1.className = "btn btn-danger"; 
        sw_2.className = "btn btn-danger";
        sw_3.className = "btn btn-danger";
        signal_1.className = "btn btn-danger";
        signal_2.className = "btn btn-danger";
    
        updateSignal();

    }
})