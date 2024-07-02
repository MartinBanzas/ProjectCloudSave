import React, { useState } from "react"
import { Modal } from "react-bootstrap"
import { addGame, hltbRequest } from "../../../requests/Requests";

interface AddGameProps {
    addGameModal: boolean | undefined;
    setAddGameModal: React.Dispatch<React.SetStateAction<boolean>>;
}

export const AddGame: React.FC<AddGameProps> = ({addGameModal, setAddGameModal}) => {

    const [gameName, setGameName]=useState("")
    const [system, setSystem]=useState("")


    const handleSubmit = async () => {


    const hltb = await hltbRequest(gameName)   
    console.log(hltb) 
    const success = await addGame(gameName, system, hltb);

    if (success) {
        setAddGameModal(false);
    }
        
    }

    return (
        <Modal show={addGameModal} onHide={() => setAddGameModal(false)}>
      <Modal.Header closeButton className="modal-header">
        <h5 className="modal-title" id="reviewModalLabel">
          Añadir juego
        </h5>
      </Modal.Header>
      <Modal.Body className="modal-body">
        <form id="reviewForm" onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="reviewName" className="form-label">
              Nombre
            </label>
            <input
              type="text"
              className="form-control"
              name="gameName"
              id="reviewName"
              placeholder="Introduce el nombre del juego"
              onChange={(event)=>setGameName(event.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="reviewComments" className="form-label">
              Sistema
            </label>
            <input
              className="form-control"
              name="reviewComments"
              id="reviewComments"
              
              placeholder="Introduce el sistema"
              onChange={(event)=>setSystem(event.target.value)}
              required
            />
          </div>
          
          <button type="submit" className="btn btn-primary">
            Guardar
          </button>
          <button
            type="button"
            className="btn btn-secondary ms-2"
            onClick={() => setAddGameModal(false)}
          >
            Cerrar
          </button>
        </form>
      </Modal.Body>
    </Modal> 
    )
}