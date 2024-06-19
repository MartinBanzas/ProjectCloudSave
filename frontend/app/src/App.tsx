import React from 'react';
import './App.css';
import { NavBar } from './components/NavBar/NavBar';
import { GameList } from './components/Body/GameList';


function App() {
  return (
    <div className="App">
      <NavBar/>
     <GameList/>
      
    </div>
  );
}

export default App;
