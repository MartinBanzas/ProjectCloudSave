import GameModel from "../../../models/Models";
import React from "react";
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
  const formatDate = (date: Date) => {
    return new Date(date).toLocaleDateString("en-GB");
  };

  return (
    <Modal show={showModal} onHide={() => setShowModal(false)}>
      <Modal.Header closeButton className="">
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
              <span className="game-info-value">
                {formatDate(gameInfo.finicio)}
              </span>
            </li>
          )}
          {gameInfo.ffin && (
            <li className="game-info-item">
              <span className="game-info-label">Finalizado el:</span>
              <span className="game-info-value">
                {formatDate(gameInfo.ffin)}
              </span>
            </li>
          )}
          {gameInfo.company && (
            <li className="game-info-item">
              <span className="game-info-label">Compañía:</span>
              <span className="game-info-value">{gameInfo.company}</span>
            </li>
          )}
          {gameInfo.year && (
            <li className="game-info-item">
              <span className="game-info-label">Año:</span>
              <span className="game-info-value">{gameInfo.year}</span>
            </li>
          )}

          {gameInfo.main && (
            <li className="game-info-item">
              <span className="game-info-label">Juego principal:</span>
              <span className="game-info-value">{gameInfo.main}</span>
            </li>
          )}

          {gameInfo.main_extra && (
            <li className="game-info-item">
              <span className="game-info-label">Principal + Extra:</span>
              <span className="game-info-value">{gameInfo.main_extra}</span>
            </li>
          )}


          {gameInfo.completionist && (
            <li className="game-info-item">
              <span className="game-info-label">Completista</span>
              <span className="game-info-value">{gameInfo.completionist}</span>
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
