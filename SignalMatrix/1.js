let inputSw = false;
let leftSw = false;
let rightSw = false;

const signal1 = document.getElementById("signalStatus1");
const signal2 = document.getElementById("signalStatus2");
const sw1 = document.getElementById("sw1");

const inputSwitch = document.getElementById("inputSwitch");
const leftSwitch = document.getElementById("leftSwitch");
const rightSwitch = document.getElementById("rightSwitch");

let internalBits = {
  "s1": false,
  "sw-lhs": false,
  "sw-rhs": false,
  "s1-lock": false,
  "s1-lhs-status": false,
  "s1-rhs-status": false
};

function isLocked() {
  return internalBits["s1-lock"];
}

function updateBits() {
  internalBits["s1"] = inputSw;
  internalBits["sw-lhs"] = (!internalBits["s1-lock"] && leftSw) || (internalBits["s1-lock"] && internalBits["sw-lhs"]);
  internalBits["sw-rhs"] = (!internalBits["s1-lock"] && rightSw) || (internalBits["s1-lock"] && internalBits["sw-rhs"]);

  internalBits["s1-rhs-status"] = internalBits["s1"] && internalBits["sw-rhs"] && !internalBits["sw-lhs"];
  internalBits["s1-lhs-status"] = internalBits["s1"] && internalBits["sw-lhs"] && !internalBits["sw-rhs"];

  internalBits["s1-lock"] = internalBits["s1-lhs-status"] || internalBits["s1-rhs-status"];
}

function updateSignal() {
  updateBits();

  if (internalBits["s1-lhs-status"]) {
    signal1.className = "status green";
    signal1.innerText = "GREEN";
  } else {
    signal1.className = "status red";
    signal1.innerText = "RED";
  }

  if (internalBits["s1-rhs-status"]) {
    signal2.className = "status green";
    signal2.innerText = "GREEN";
  } else {
    signal2.className = "status red";
    signal2.innerText = "RED";
  }

  if (internalBits["s1-lock"]) {
    sw1.className = "badge text-danger";
    sw1.innerText = "Locked";
  } else {
    sw1.className = "badge text-warning";
    sw1.innerText = "Unlocked";
  }
}

// Event Listeners
inputSwitch.addEventListener("click", () => {
  if (!internalBits["s1-lock"]) {
    inputSw = !inputSw;
    inputSwitch.className = inputSw ? "badge text-success" : "badge text-danger";
    updateSignal();
  }
});

leftSwitch.addEventListener("click", () => {
  if (!internalBits["s1-lock"]) {
    leftSw = !leftSw;
    leftSwitch.className = leftSw ? "badge text-success" : "badge text-danger";
    updateSignal();
  }
});

rightSwitch.addEventListener("click", () => {
  if (!internalBits["s1-lock"]) {
    rightSw = !rightSw;
    rightSwitch.className = rightSw ? "badge text-success" : "badge text-danger";
    updateSignal();
  }
});

// Unlock on sw1 click
sw1.addEventListener("click", () => {
    inputSw = false;
  leftSw = false;
  rightSw = false;
  internalBits["s1-lock"] = false;
  updateSignal();
  inputSwitch.className = "badge text-danger";
  leftSwitch.className = "badge text-danger";
  rightSwitch.className = "badge text-danger";
});
