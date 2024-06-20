import React from 'react';
import './App.css';
import { NavBar } from './components/NavBar/NavBar';
import { GameList } from './components/Body/GameList';
import "@fontsource/pacifico"; // Defaults to weight 400
import "@fontsource/roboto";


function App() {
  return (
    <div className="App">
      <NavBar/>
     <GameList/>
    </div>
  );
}

export default App;
