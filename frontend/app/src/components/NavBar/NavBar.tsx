import { DropdownMenu } from 'react-bootstrap';
import '../Body/css/NavBar.css';
import { DropdownActionsMenu } from './DropdownActionsMenu';
import { AddGame } from '../Body/modals/AddGame';
import { useState } from 'react';

export const NavBar = () => {
  const style = {
    color: "#ffd700",
    margin: "15px",
    fontWeight: "bold",
  };

  const [addGameModal, setAddGameModal]=useState(false)

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container px-4 px-lg-5 navi">
        <a className="navbar-brand custom-link" href="#">
          Martin's Library
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
         <DropdownActionsMenu/>
          <form className="d-flex" method="get">
            <input
              className="form-control me-2 search-bar"
              type="search"
              placeholder="Introduce el nombre a buscar..."
              aria-label="Buscar"
              name="nombre"
            />
            <button className="btn btn-outline-light" type="submit">
              <i className="fas fa-search"></i>
            </button>
          </form>
          <form method="post">
            <button className="btn btn-outline-light btn-log" type="submit">
              <i className="bi-cart-fill me-1"></i>
              Iniciar sesión
            </button>
          </form>
          <div>
            <span className="navbar-text" style={style}>
              <span>{}</span>
            </span>
          </div>
          <form method="post">
            <button className="btn btn-outline-light" type="submit">
              <i className="bi-cart-fill me-1"></i>
              Finalizar sesión
            </button>
          </form>
        </div>
      </div>
      <div className="modal" id="aboutModal" aria-labelledby="aboutModalLabel" aria-hidden="true">
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="aboutModalLabel">
                Acerca de esta web
              </h5>
              <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div className="modal-body">
              <p>
                Web app desarrollada por Martín Antelo Jallas. Hecho en React. MySQL. Atribuciones: Flaticon
              </p>
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
};