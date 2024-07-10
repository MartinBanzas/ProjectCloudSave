import { Modal } from "react-bootstrap";
import { useEffect, useState } from "react";
import { searchGrids } from "../../../requests/Requests";
import GameModel from "../../../models/GameModel";
import GridModel from "../../../models/GridModel";
import "./../css/AddImage.css"

interface AddImageModalProps {
  gameInfo: GameModel;
  addImageModal: boolean;
  setAddImageModal: React.Dispatch<React.SetStateAction<boolean>>;
}

export const AddImage: React.FC<AddImageModalProps> = ({
  addImageModal,
  setAddImageModal,
  gameInfo,
}) => {
  const [grids, setGrids] = useState<GridModel[]>();

  useEffect(() => {
    const fetchData = async () => {
      const data = await searchGrids(gameInfo.name);
      if (data) {
        setGrids(data);
      }
    };
    fetchData();
    console.log(grids);
  }, []);

  console.log(grids);

  return (
    <Modal show={addImageModal} onHide={() => setAddImageModal(false)}>
      <Modal.Dialog className="modal-dialog">
        <Modal.Header closeButton className="modal-header">
          <h5 className="modal-title" id="reviewModalLabel">
           Selecciona una imagen como portada
          </h5>
        </Modal.Header>
        <Modal.Body className="modal-body">
          <form id="reviewForm">
            <div className="image-grid mb-3">
              {grids &&
                grids.map((grid) => (
                  <img
                    height={96}
                    width={96}
                    className=""
                    key={grid.id}
                    src={grid.thumbnail}
                    alt={`Thumbnail for ${grid.id}`}
                  />
                ))}
            </div>
  
            <button type="submit" className="btn btn-primary">
              Guardar
            </button>
            <button type="button" className="btn btn-secondary ms-2" onClick={() => setAddImageModal(false)}>
              Cerrar
            </button>
          </form>
        </Modal.Body>
      </Modal.Dialog>
    </Modal>
  )};