var inputSwitch = document.getElementById('inputSwitch');
var outputSwitch = document.getElementById('outputSwitch');
var leftSwitch = document.getElementById('leftSwitch');
var rightSwitch = document.getElementById('rightSwitch');
var signalStatus1 = document.getElementById('signalStatus1');
var signalStatus2 = document.getElementById('signalStatus2');
var sw1 = document.getElementById("sw1");

let isLocked = false;
let frozenStatus1 = {};
let frozenStatus2 = {};

var input = ["s1","sw-lhs", "sw-rhs", "o1"];
var output = ["s1-lock", "s1-lhs-status", "s1-rhs-status"];
var internalBits = {};
internalBits["s1"] = false;
internalBits["sw-lhs"] = false;
internalBits["sw-rhs"] = false;
internalBits["o1"] = false;
internalBits["s1-lock"] = false;
internalBits["s1-lhs-status"] = false;
internalBits["s1-rhs-status"] = false;

function updateBits() {
  internalBits["s1"] = inputSwitch.checked;
  internalBits["o1"] = outputSwitch.checked;
  internalBits["sw-lhs"] = (!internalBits["s1-lock"] && leftSwitch.checked) || (internalBits["s1-lock"] && internalBits["sw-lhs"]);

  internalBits["sw-rhs"] = (!internalBits["s1-lock"] && rightSwitch.checked) || (internalBits["s1-lock"] && internalBits["sw-rhs"]);

  internalBits["s1-lhs-status"] = internalBits["s1"] && internalBits["o1"] && internalBits["sw-lhs"] && !internalBits["sw-rhs"];

  internalBits["s1-rhs-status"] = internalBits["s1"] && internalBits["o1"] && internalBits["sw-rhs"] && !internalBits["sw-lhs"];
  
  internalBits["s1-lock"] = internalBits["s1-lhs-status"] || internalBits["s1-rhs-status"];
}

function updateSignal() {
  updateBits();
  if (internalBits["s1-lhs-status"]) {
    signalStatus1.className = "status green";
    signalStatus1.textContent = "GREEN";
  } else {
    signalStatus1.className = "status red";
    signalStatus1.textContent = "RED";
  }
  if (internalBits["s1-rhs-status"]) {
    signalStatus2.className = "status green";
    signalStatus2.textContent = "GREEN";
  } else {
    signalStatus2.className = "status red";
    signalStatus2.textContent = "RED";
  }
  if (internalBits["s1-lock"]) {
    sw1.className = "badge text-danger";
  } else {
    sw1.className = "badge text-warning";
  }
}

// Register change listeners for all other switches
[inputSwitch, outputSwitch, leftSwitch, rightSwitch].forEach(sw =>
  sw.addEventListener('change', updateSignal)
);

// Initial signal update
updateSignal();
