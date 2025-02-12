import React, { useState } from 'react';

import './App.css';

function App() {
  const [log, setLog] = useState("");

  const startBattle = async () => {
      const response = await fetch("http://localhost:8080/battle", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify([
              { name: "Car A", health: 50, attackPower: 10, defenseStrength: 5 },
              { name: "Car B", health: 100, attackPower: 5, defenseStrength: 10 },
          ])
      });
      const data = await response.text();
      setLog(data);
  };

  return (
      <div>
          <h1>Turbo Clash Derby</h1>
          <button onClick={startBattle}>Start Battle</button>
          <pre>{log}</pre>
      </div>
  );
}

export default App;


