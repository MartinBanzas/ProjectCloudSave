import Dropdown from 'react-bootstrap/Dropdown';
import "../../components/Body/css/Dropdown.css"
import { useState } from 'react';
import { AddGame } from '../Body/modals/AddGame';

export const DropdownActionsMenu = () => {

  const [addGameModal, setAddGameModal]=useState(false)

    return (
        <Dropdown>
      <Dropdown.Toggle variant="success" id="dropdown-basic">
        Acciones
      </Dropdown.Toggle>

      <Dropdown.Menu>
        <Dropdown.Item className='dropdown-item-custom' onClick={()=>setAddGameModal(true)} href="#/action-1">Añadir juego</Dropdown.Item>
        <Dropdown.Item className='dropdown-item-custom' href="#/action-2">Another action</Dropdown.Item>
        <Dropdown.Item className='dropdown-item-custom' href="#/action-3">Something else</Dropdown.Item>
      </Dropdown.Menu>
      <AddGame addGameModal={addGameModal} setAddGameModal={setAddGameModal}/>
    </Dropdown>
  );
    
}