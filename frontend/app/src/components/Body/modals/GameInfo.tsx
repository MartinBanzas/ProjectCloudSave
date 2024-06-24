import GameModel from "../../../models/Models";
import React, { useState, useEffect } from "react";
import { Modal } from "react-bootstrap";
import "../css/GameInfo.css"; // Asegúrate de importar el archivo CSS


interface GameInfoProps {
  gameInfo: GameModel;
  showModal: boolean;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}

export const GameInfo: React.FC<GameInfoProps> = ({
  showModal,
  setShowModal,
  gameInfo,
}) => {
  const formatDate = (date:Date) => {
    return new Date(date).toLocaleDateString("en-GB");
   };


   return (
    <Modal show={showModal} onHide={() => setShowModal(false)}>
      <Modal.Header closeButton className="modal-header">
        <h5 className="modal-title" id="reviewModalLabel">
          {gameInfo?.name}
        </h5>
      </Modal.Header>
      <Modal.Body className="modal-body">
        <ul className="game-info-list">
          <li className="game-info-item">
            <span className="game-info-label">¿Terminado?</span>
            <span className="game-info-value">
              {gameInfo.terminado ? "Sí" : "No"}
            </span>
          </li>
          <li className="game-info-item">
            <span className="game-info-label">Sistemas:</span>
            <span className="game-info-value">{gameInfo.sistema}</span>
          </li>
          {gameInfo.finicio && (
            <li className="game-info-item">
              <span className="game-info-label">Empezado el:</span>
              <span className="game-info-value">{formatDate(gameInfo.finicio)}</span>
            </li>
          )}
          {gameInfo.ffin && (
            <li className="game-info-item">
              <span className="game-info-label">Finalizado el:</span>
              <span className="game-info-value">{formatDate(gameInfo.ffin)}</span>
            </li>
          )}
          {gameInfo.review && (
            <li className="game-info-item">
            <span className="game-info-label">{gameInfo.tituloReview}</span>
            <span className="game-info-value">{gameInfo.review}</span>
          </li>
          )}
        </ul>
      </Modal.Body>
    </Modal>
  );
};