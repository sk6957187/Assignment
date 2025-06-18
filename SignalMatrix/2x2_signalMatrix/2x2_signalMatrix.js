let input1 = false;
let input2 = false;
let sw1 = false;
let sw2 = false;
let locked = false;

let left_b = document.getElementById("left_b");
let right_b = document.getElementById("right_b");
let switch_l = document.getElementById("sw_l");
let switch_r = document.getElementById("sw_r");
let lock = document.getElementById("lock");
let signal_l = document.getElementById("signal_l");
let signal_r = document.getElementById("signal_r");

let internalBits = {
    "input_l" : false,
    "input_r" : false,
    "sw_l" : false,
    "sw_r" : false,
    "lock_status": false,
    "signal_l_status" : false,
    "signal_r_status": false
}

function updateBits() {
    internalBits["input_l"] = input1;
    internalBits["input_r"] = input2;
    internalBits["sw_l"] = sw1 && !sw2;
    internalBits["sw_r"] = sw2 && !sw1;
    internalBits["signal_l_status"] = (internalBits["input_l"] || internalBits["input_r"]) && internalBits["sw_l"] && !internalBits["lock_status"];
    internalBits["signal_r_status"] = (internalBits["input_l"] || internalBits["input_r"]) && internalBits["sw_r"] && !internalBits["lock_status"];


    internalBits["lock_status"] = internalBits["signal_l_status"] || internalBits["signal_r_status"];
}

function updateSignal(){
    updateBits();
    if(internalBits["signal_l_status"]){
        signal_l.className = "btn btn-success";
        signal_l.innerText = "G_L";
    } else {
        signal_l.className = "btn btn-danger";
        signal_l.innerText = "O_L";
    }
    if(internalBits["signal_r_status"]){
        signal_r.className = "btn btn-success";
        signal_r.innerText = "G_R";
    } else {
        signal_r.className = "btn btn-danger";
        signal_r.innerText = "O_R";
    }
    if (internalBits["lock_status"]) {
        lock.className = "badge text-danger";
        lock.innerText = "Locked";
    } else {
        lock.className = "badge text-success";
        lock.innerText = "Unlocked";
    }
}

left_b.addEventListener("click", () => {
    if(!internalBits["lock_status"]){
        input1 = !input1;
        updateSignal();
        left_b.className = input1 ? "btn btn-success" : "btn btn-danger";
    }
})

right_b.addEventListener("click", ()=>{
    if(!internalBits["lock_status"]){
        input2 = !input2;
        updateSignal();
        right_b.className = input2 ? "btn btn-success" : "btn btn-danger";
    }
})

switch_l.addEventListener("click", ()=>{
    if(!internalBits["lock_status"]){
        sw1 = !sw1;
        updateSignal();
        switch_l.className = sw1 ? "btn btn-success" : "btn btn-danger";
    }
})

switch_r.addEventListener("click", ()=>{
    if(!internalBits["lock_status"]){
        sw2 = !sw2;
        updateSignal();
        switch_r.className = sw2 ? "btn btn-success" : "btn btn-danger";
    }
})

lock.addEventListener("click", () => {
    if(internalBits["lock_status"]){
        input1 = false;
        input2 = false;
        sw1 = false;
        sw2 = false;
        updateSignal();

        left_b.className = "btn btn-danger";
        right_b.className = "btn btn-danger";
        switch_l.className = "btn btn-danger";
        switch_r.className = "btn btn-danger";
    }
});
