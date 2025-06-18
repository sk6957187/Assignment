let input_2 = false;
let input_1 = false;
let input_3 = false;
let switch_1 = false;
let switch_2 = false;
let switch_3 = false;
let locked = false;

let i_1 = document.getElementById("i_1");
let i_2 = document.getElementById("i_2");
let i_3 = document.getElementById("i_3");
let sw_1 = document.getElementById("sw_1");
let sw_2 = document.getElementById("sw_2");
let sw_3 = document.getElementById("sw_3");
let lock = document.getElementById("lock");
let o_2 = document.getElementById("o_1");

let internalBits = {
    "i_sw_1" : false,
    "i_sw_2" : false,
    "i_sw_3" : false,
    "s_sw_1" : false,
    "s_sw_2" : false,
    "s_sw_3" : false,
    "signal_2-status": false,
    "lock-status" : false,
}

function updateBits(){
    internalBits["i_sw_1"] = input_1;
    internalBits["i_sw_2"] = input_2;
    internalBits["i_sw_3"] = input_3;
    internalBits["s_sw_1"] = switch_1;
    internalBits["s_sw_2"] = switch_2;
    internalBits["s_sw_3"] = switch_3;
    internalBits["signal_2-status"] = 
        (input_1 && switch_1) || 
        (input_2 && switch_2) || 
        (input_3 && switch_3);
    if((switch_1 && switch_2) || (switch_1 && switch_3) || (switch_2 && switch_3)){
        internalBits["signal_2-status"] = false;
    }
    if((input_1 && input_2) || (input_1 && input_3) || (input_2 && input_3)){
        internalBits["signal_2-status"] = false;
    }
    // internalBits["signal_2-status"] = !(switch_1 && switch_2) || !(switch_1 && switch_3) || !(switch_2 && switch_3) || !(switch_1 && switch_2 && switch_3);
    internalBits["lock-status"] = internalBits["signal_2-status"];
}


function updateSignal(){
    updateBits();
    
    if(internalBits["signal_2-status"]){
        o_2.className = "btn btn-success";
        o_2.innerText = "G";
    } else {
        o_2.className = "btn btn-danger";
        o_2.innerText = "O"
    }
    if(internalBits["lock-status"]){
        lock.className = "btn btn-danger";
        lock.innerText = "Locked"
    } else {
        lock.className = "btn btn-success";
        lock.innerText = "Unlocked"
    }
}

i_1.addEventListener("click", () => {
    if(!internalBits["lock-status"]){
        input_1 = !input_1;
        i_1.className= input_1 ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

i_2.addEventListener("click", () => {
    if(!internalBits["lock-status"]){
        input_2 = !input_2;
        i_2.className= input_2 ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

i_3.addEventListener("click", () => {
    if(!internalBits["lock-status"]){
        input_3 = !input_3;
        i_3.className= input_3 ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

sw_1.addEventListener("click", () => {
    if(!internalBits["lock-status"]){
        switch_1 = !switch_1;
        sw_1.className= switch_1 ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

sw_2.addEventListener("click", () => {
    if(!internalBits["lock-status"]){
        switch_2 = !switch_2;
        sw_2.className= switch_2 ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

sw_3.addEventListener("click", () => {
    if(!internalBits["lock-status"]){
        switch_3 = !switch_3;
        sw_3.className= switch_3 ? "btn btn-success" : "btn btn-danger";
        updateSignal();
    }
})

lock.addEventListener("click", () => {
    if (internalBits["lock-status"]) {
        locked = !locked;

        input_1 = false;
        input_2 = false;
        input_3 = false;
        switch_1 = false;
        switch_2 = false;
        switch_3 = false;

        // Reset classes
        i_1.className = "btn btn-danger";
        i_2.className = "btn btn-danger";
        i_3.className = "btn btn-danger";
        sw_1.className = "btn btn-danger";
        sw_2.className = "btn btn-danger";
        sw_3.className = "btn btn-danger";

        updateSignal();
    }
});


