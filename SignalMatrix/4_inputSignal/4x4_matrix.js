// Global boolean variables
let values = {
    o_1 : false, o_2 : false, o_3 : false, o_4 : false,
    i_1 : false, i_2 : false, i_3 : false, i_4 : false,

    o_1_ind : false, o_2_ind : false, o_3_ind : false, o_4_ind : false,
    i_1_ind : false, i_2_ind : false, i_3_ind : false, i_4_ind : false
};

let i1_o1 = false, i1_o2 = false, i1_o3 = false, i1_o4 = false;
let i2_o1 = false, i2_o2 = false, i2_o3 = false, i2_o4 = false;
let i3_o1 = false, i3_o2 = false, i3_o3 = false, i3_o4 = false;
let i4_o1 = false, i4_o2 = false, i4_o3 = false, i4_o4 = false;

var logicData = {
    "i1_o1": ["(values.i_1 && values.o_1) || internalBits.i1_o1", "!internalBits.i2_o1", "!internalBits.i3_o1", "!internalBits.i4_o1", "!internalBits.i1_o2", "!internalBits.i1_o3", "!internalBits.i1_o4"],
    "i1_o2": ["(values.i_1 && values.o_2) || internalBits.i1_o2", "!internalBits.i2_o2", "!internalBits.i3_o2", "!internalBits.i4_o2", "!internalBits.i1_o1", "!internalBits.i1_o3", "!internalBits.i1_o4"]
};

var i1 = document.getElementById("i1");
var i2 = document.getElementById("i2");
var i3 = document.getElementById("i3");
var i4 = document.getElementById("i4");
var o1 = document.getElementById("o1");
var o2 = document.getElementById("o2");
var o3 = document.getElementById("o3");
var o4 = document.getElementById("o4");
var i1o1 = document.getElementById("i1o1");
var i1o2 = document.getElementById("i1o2");
var i1o3 = document.getElementById("i1o3");
var i1o4 = document.getElementById("i1o4");
var i2o1 = document.getElementById("i2o1");
var i2o2 = document.getElementById("i2o2");
var i2o3 = document.getElementById("i2o3");
var i2o4 = document.getElementById("i2o4");
var i3o1 = document.getElementById("i3o1");
var i3o2 = document.getElementById("i3o2");
var i3o3 = document.getElementById("i3o3");
var i3o4 = document.getElementById("i3o4");
var i4o1 = document.getElementById("i4o1");
var i4o2 = document.getElementById("i4o2");
var i4o3 = document.getElementById("i4o3");
var i4o4 = document.getElementById("i4o4");

var internalBits = {
    o_1: false, o_2: false, o_3: false, o_4: false,
    i_1: false, i_2: false, i_3: false, i_4: false,
    i1_o1: false, i1_o2: false, i1_o3: false, i1_o4: false,
    i2_o1: false, i2_o2: false, i2_o3: false, i2_o4: false,
    i3_o1: false, i3_o2: false, i3_o3: false, i3_o4: false,
    i4_o1: false, i4_o2: false, i4_o3: false, i4_o4: false,
};

var keys = ["i1_o1", "i1_o2"];

function findValues(key) {

    var temp = logicData[key];
    var bits = Array.isArray(temp) ? temp : [temp];
    var result = true;

    for (let i = 0; i < bits.length; i++) {
        const bit = bits[i];
        try {
            var evaluated = new Function('values', 'internalBits', `return ${bit}`)(values, internalBits);
            result = result && evaluated;
        } catch (error) {
            console.error(`Error evaluating expression for key "${key}", expression "${bit}":`, error);
            return false;
        }
    }
    return result;
}

function updateBits() {
    
    for(let i=0; i<keys.length; i++){
        var key = keys[i];
        internalBits[key] = findValues(key);
    }

    // internalBits["i1_o1"] = ((values["i_1"] && values["o_1"]) || internalBits["i1_o1"]) && !internalBits["i2_o1"] && !internalBits["i3_o1"] && !internalBits["i4_o1"] && !internalBits["i1_o2"] && !internalBits["i1_o3"] && !internalBits["i1_o4"];
    // internalBits["i1_o2"] = ((values["i_1"] && values["o_2"]) || internalBits["i1_o2"]) && !internalBits["i2_o2"] && !internalBits["i3_o2"] && !internalBits["i4_o2"] && !internalBits["i1_o1"] && !internalBits["i1_o3"] && !internalBits["i1_o4"];
    
    internalBits["i1_o3"] = ((values["i_1"] && values["o_3"]) || internalBits["i1_o3"]) && !internalBits["i2_o3"] && !internalBits["i3_o3"] && !internalBits["i4_o3"] && !internalBits["i1_o1"] && !internalBits["i1_o2"] && !internalBits["i1_o4"];
    internalBits["i1_o4"] = ((values["i_1"] && values["o_4"]) || internalBits["i1_o4"]) && !internalBits["i2_o4"] && !internalBits["i3_o4"] && !internalBits["i4_o4"] && !internalBits["i1_o1"] && !internalBits["i1_o2"] && !internalBits["i1_o3"];

    internalBits["i2_o1"] = ((values["i_2"] && values["o_1"]) || internalBits["i2_o1"]) && !internalBits["i1_o1"] && !internalBits["i3_o1"] && !internalBits["i4_o1"] && !internalBits["i2_o2"] && !internalBits["i2_o3"] && !internalBits["i2_o4"];
    internalBits["i2_o2"] = ((values["i_2"] && values["o_2"]) || internalBits["i2_o2"]) && !internalBits["i1_o2"] && !internalBits["i3_o2"] && !internalBits["i4_o2"] && !internalBits["i2_o1"] && !internalBits["i2_o3"] && !internalBits["i2_o4"];
    internalBits["i2_o3"] = ((values["i_2"] && values["o_3"]) || internalBits["i2_o3"]) && !internalBits["i1_o3"] && !internalBits["i3_o3"] && !internalBits["i4_o3"] && !internalBits["i2_o1"] && !internalBits["i2_o2"] && !internalBits["i2_o4"];
    internalBits["i2_o4"] = ((values["i_2"] && values["o_4"]) || internalBits["i2_o4"]) && !internalBits["i1_o4"] && !internalBits["i3_o4"] && !internalBits["i4_o4"] && !internalBits["i2_o1"] && !internalBits["i2_o2"] && !internalBits["i2_o3"];

    internalBits["i3_o1"] = ((values["i_3"] && values["o_1"]) || internalBits["i3_o1"]) && !internalBits["i1_o1"] && !internalBits["i2_o1"] && !internalBits["i4_o1"] && !internalBits["i3_o2"] && !internalBits["i3_o3"] && !internalBits["i3_o4"];
    internalBits["i3_o2"] = ((values["i_3"] && values["o_2"]) || internalBits["i3_o2"]) && !internalBits["i1_o2"] && !internalBits["i2_o2"] && !internalBits["i4_o2"] && !internalBits["i3_o1"] && !internalBits["i3_o3"] && !internalBits["i3_o4"];
    internalBits["i3_o3"] = ((values["i_3"] && values["o_3"]) || internalBits["i3_o3"]) && !internalBits["i1_o3"] && !internalBits["i2_o3"] && !internalBits["i4_o3"] && !internalBits["i3_o1"] && !internalBits["i3_o2"] && !internalBits["i3_o4"];
    internalBits["i3_o4"] = ((values["i_3"] && values["o_4"]) || internalBits["i3_o4"]) && !internalBits["i1_o4"] && !internalBits["i2_o4"] && !internalBits["i4_o4"] && !internalBits["i3_o1"] && !internalBits["i3_o2"] && !internalBits["i3_o3"];

    internalBits["i4_o1"] = ((values["i_4"] && values["o_1"]) || internalBits["i4_o1"]) && !internalBits["i1_o1"] && !internalBits["i2_o1"] && !internalBits["i3_o1"] && !internalBits["i4_o2"] && !internalBits["i4_o3"] && !internalBits["i4_o4"];
    internalBits["i4_o2"] = ((values["i_4"] && values["o_2"]) || internalBits["i4_o2"]) && !internalBits["i1_o2"] && !internalBits["i2_o2"] && !internalBits["i3_o2"] && !internalBits["i4_o1"] && !internalBits["i4_o3"] && !internalBits["i4_o4"];
    internalBits["i4_o3"] = ((values["i_4"] && values["o_3"]) || internalBits["i4_o3"]) && !internalBits["i1_o3"] && !internalBits["i2_o3"] && !internalBits["i3_o3"] && !internalBits["i4_o1"] && !internalBits["i4_o2"] && !internalBits["i4_o4"];
    internalBits["i4_o4"] = ((values["i_4"] && values["o_4"]) || internalBits["i4_o4"]) && !internalBits["i1_o4"] && !internalBits["i2_o4"] && !internalBits["i3_o4"] && !internalBits["i4_o1"] && !internalBits["i4_o2"] && !internalBits["i4_o3"];
    
    values["o_1_ind"] = internalBits["i1_o1"] || internalBits["i2_o1"] || internalBits["i3_o1"] || internalBits["i4_o1"];
    values["o_2_ind"] = internalBits["i1_o2"] || internalBits["i2_o2"] || internalBits["i3_o2"] || internalBits["i4_o2"];
    values["o_3_ind"] = internalBits["i1_o3"] || internalBits["i2_o3"] || internalBits["i3_o3"] || internalBits["i4_o3"];
    values["o_4_ind"] = internalBits["i1_o4"] || internalBits["i2_o4"] || internalBits["i3_o4"] || internalBits["i4_o4"];

    values["i_1_ind"] = internalBits["i1_o1"] || internalBits["i1_o2"] || internalBits["i1_o3"] || internalBits["i1_o4"];
    values["i_2_ind"] = internalBits["i2_o1"] || internalBits["i2_o2"] || internalBits["i2_o3"] || internalBits["i2_o4"];
    values["i_3_ind"] = internalBits["i3_o1"] || internalBits["i3_o2"] || internalBits["i3_o3"] || internalBits["i3_o4"];
    values["i_4_ind"] = internalBits["i4_o1"] || internalBits["i4_o2"] || internalBits["i4_o3"] || internalBits["i4_o4"];
}

function inputControl(){
    i1.className = values["i_1_ind"] ? "btn btn-success" : "btn btn-danger";
    i2.className = values["i_2_ind"] ? "btn btn-success" : "btn btn-danger";
    i3.className = values["i_3_ind"] ? "btn btn-success" : "btn btn-danger";
    i4.className = values["i_4_ind"] ? "btn btn-success" : "btn btn-danger";
    o1.className = values["o_1_ind"] ? "btn btn-success" : "btn btn-danger";
    o2.className = values["o_2_ind"] ? "btn btn-success" : "btn btn-danger";
    o3.className = values["o_3_ind"] ? "btn btn-success" : "btn btn-danger";
    o4.className = values["o_4_ind"] ? "btn btn-success" : "btn btn-danger";
}

function updateSignal() {
    updateBits();
    inputControl();
    if(internalBits["i1_o1"]){
        i1o1.className = "btn btn-success";
        i1o1.innerText = "G";
    } else {
        i1o1.className = "btn btn-danger";
        i1o1.innerText = "R";
    }
    if(internalBits["i2_o1"]) {
        i1o1.className = "btn btn-success"
        i2o1.className = "btn btn-info";
        i2o1.innerText = "G";
    } else {
        i2o1.className = "btn btn-danger";
        i2o1.innerText = "R";
    }
    if(internalBits["i3_o1"]) {
        i1o1.className = "btn btn-info"
        i2o1.className = "btn btn-info";
        i3o1.className = "btn btn-success";
        i3o1.innerText = "G";
    } else {
        i3o1.className = "btn btn-danger";
        i3o1.innerText = "R";
    }
    if(internalBits["i4_o1"]) {
        i1o1.className = "btn btn-info";
        i2o1.className = "btn btn-info";
        i3o1.className = "btn btn-info";
        i4o1.className = "btn btn-success";
        i4o1.innerText = "G";
    } else {
        i4o1.className = "btn btn-danger";
        i4o1.innerText = "R";
    }
    if(internalBits["i1_o2"]) {
        i1o1.className = "btn btn-info"
        i1o2.className = "btn btn-success";
        i1o2.innerText = "G";
    } else {
        i1o2.className = "btn btn-danger";
        i1o2.innerText = "R";
    }
    if(internalBits["i2_o2"]) {
        i1o2.className = "btn btn-info";
        i2o1.className = "btn btn-info";
        i2o2.className = "btn btn-success";
        i2o2.innerText = "G";
    } else {
        i2o2.className = "btn btn-danger";
        i2o2.innerText = "R";
    }
    if(internalBits["i3_o2"]) {
        i1o2.className = "btn btn-info";
        i3o1.className = "btn btn-info";
        i2o2.className = "btn btn-info";
        i3o2.className = "btn btn-success";
        i3o2.innerText = "G";
    } else {
        i3o2.className = "btn btn-danger";
        i3o2.innerText = "R";
    }
    if(internalBits["i4_o2"]) {
        i1o2.className = "btn btn-info";
        i2o2.className = "btn btn-info";
        i3o2.className = "btn btn-info";
        i4o1.className = "btn btn-info";
        i4o2.className = "btn btn-success";
        i4o2.innerText = "G";
    } else {
        i4o2.className = "btn btn-danger";
        i4o2.innerText = "R";
    }
    if(internalBits["i1_o3"]) {
        i1o1.className = "btn btn-info"
        i1o2.className = "btn btn-info";
        i1o3.className = "btn btn-success";
        i1o3.innerText = "G";
    } else {
        i1o3.className = "btn btn-danger";
        i1o3.innerText = "R";
    }
    if(internalBits["i2_o3"]) {
        i1o3.className = "btn btn-info";
        i2o1.className = "btn btn-info";
        i2o2.className = "btn btn-info";
        i2o3.className = "btn btn-success";
        i2o3.innerText = "G";
    } else {
        i2o3.className = "btn btn-danger";
        i2o3.innerText = "R";
    }
    if(internalBits["i3_o3"]) {
        i1o3.className = "btn btn-info";
        i3o1.className = "btn btn-info";
        i2o3.className = "btn btn-info";
        i3o2.className = "btn btn-info";
        i3o3.className = "btn btn-success";
        i3o3.innerText = "G";
    } else {
        i3o3.className = "btn btn-danger";
        i3o3.innerText = "R";
    }
    if(internalBits["i4_o3"]) {
        i1o3.className = "btn btn-info";
        i2o3.className = "btn btn-info";
        i3o3.className = "btn btn-info";
        i4o1.className = "btn btn-info";
        i4o2.className = "btn btn-info";
        i4o3.className = "btn btn-success";
        i4o3.innerText = "G";
    } else {
        i4o3.className = "btn btn-danger";
        i4o3.innerText = "R";
    }
    if(internalBits["i1_o4"]) {
        i1o1.className = "btn btn-info";
        i1o2.className = "btn btn-info";
        i1o3.className = "btn btn-info";
        i1o4.className = "btn btn-success";
        i1o4.innerText = "G";
    } else {
        i1o4.className = "btn btn-danger";
        i1o4.innerText = "R";
    }
    if(internalBits["i2_o4"]) {
        i1o4.className = "btn btn-info";
        i2o1.className = "btn btn-info";
        i2o2.className = "btn btn-info";
        i2o3.className = "btn btn-info";
        i2o4.className = "btn btn-success";
        i2o4.innerText = "G";
    } else {
        i2o4.className = "btn btn-danger";
        i2o4.innerText = "R";
    }
    if(internalBits["i3_o4"]) {
        i1o4.className = "btn btn-info";
        i2o4.className = "btn btn-info";
        i3o1.className = "btn btn-info";
        i3o3.className = "btn btn-info";
        i3o2.className = "btn btn-info";
        i3o4.className = "btn btn-success";
        i3o4.innerText = "G";
    } else {
        i3o4.className = "btn btn-danger";
        i3o4.innerText = "R";
    }
    if(internalBits["i4_o4"]) {
        i1o4.className = "btn btn-info";
        i2o4.className = "btn btn-info";
        i3o4.className = "btn btn-info";
        i4o1.className = "btn btn-info";
        i4o2.className = "btn btn-info";
        i4o3.className = "btn btn-info";
        i4o4.className = "btn btn-success";
        i4o4.innerText = "G";
    } else {
        i4o4.className = "btn btn-danger";
        i4o4.innerText = "R";
    }
}


// $("#i2").on("click", function (){
//     i_2 = !i_2;
//     $(this).attr("class", i_2 ? "btn btn-success" : "btn btn-danger");
// })

function logActivity(button){
    var table  = document.getElementById("logTable").getElementsByTagName("tbody")[0];
    var row = table.insertRow(0);
    row.insertCell(0).innerText = button;
    row.insertCell(1).innerText = internalBits[button] ? "On" : "Off";
}
function activateTimer(i, o) {
    setTimeout(function() {
        values[`i_${i}`] = false;
        values[`o_${o}`] = false;
        updateSignal();
    }, 500);
}
$(document).ready(function () {
    for (let i = 1; i <= 4; i++) {
        const id = `i${i}`;
        $(`#${id}`).on("click", function () {
            values[`i_${i}`] = !values[`i_${i}`];
            $(this).attr("class", window[`i_${i}`] ? "btn btn-success" : "btn btn-danger");
            updateSignal();
        });
    }

    

    for (let i = 1; i <= 4; i++) {
        for (let o = 1; o <= 4; o++) {
            const id = `i${i}o${o}`;
            const inBit = `i${i}_o${o}`;
            $(`#${id}`).on("click", function () {
                if(internalBits[inBit]){
                    internalBits[inBit] = false;
                } else {
                    values[`i_${i}`] = true;
                    values[`o_${o}`] = true;
                }
                activateTimer(i,o);
                updateSignal();
                logActivity(inBit);
            });
        }
    }
});
