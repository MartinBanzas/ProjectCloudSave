import React, { useState, useEffect } from 'react';
import { Modal } from 'react-bootstrap';
import { addReview } from '../Requests';

interface ModalReviewProps {
  modalReview: boolean | undefined;
  setModalReview: React.Dispatch<React.SetStateAction<boolean>>;
  gameId: number;
}

export const ModalReview: React.FC<ModalReviewProps> = ({ modalReview, setModalReview, gameId }) => {
  const [review, setReview] = useState({
    reviewName: '',
    rate: 5,
    reviewComments: '',
  });

  console.log(gameId);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setReview({ ...review, [name]: value });
  };

  const handleRateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setReview({ ...review, rate: parseInt(e.target.value) });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    console.log(review);
    const success = await addReview(gameId, review.rate, review.reviewName, review.reviewComments);
    if (success) {
      // handle success, e.g., close modal or show success message
      setModalReview(false);
    } else {
      // handle error, e.g., show error message
    }
  };

  useEffect(() => {
    console.log('Rate changed:', review.rate);
  }, [review.rate]);

  return (
    <Modal show={modalReview} onHide={() => setModalReview(false)}>
      <Modal.Header closeButton className="modal-header">
        <h5 className="modal-title" id="reviewModalLabel">
          Añadir review
        </h5>
      </Modal.Header>
      <Modal.Body className="modal-body">
        <form id="reviewForm" onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="reviewName" className="form-label">
              Titular
            </label>
            <input
              type="text"
              className="form-control"
              name="reviewName"
              id="reviewName"
              value={review.reviewName}
              onChange={handleInputChange}
              required
            />
          </div>

          <p className="form-label">Valoración:</p>
          <div className="rate">
            {[5, 4, 3, 2, 1].map((value) => (
              <React.Fragment key={value}>
                <input
                  type="radio"
                  id={`star${value}`}
                  name="rate"
                  value={value}
                  checked={review.rate === value}
                  onChange={handleRateChange}
                  required
                />
                <label htmlFor={`star${value}`} title={`${value} stars`}>
                  {value} stars
                </label>
              </React.Fragment>
            ))}
          </div>

          <div className="mb-3">
            <label htmlFor="reviewComments" className="form-label">
              Tu review
            </label>
            <textarea
              className="form-control"
              name="reviewComments"
              id="reviewComments"
              rows={5}
              value={review.reviewComments}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <button type="submit" className="btn btn-primary">
            Guardar
          </button>
          <button
            type="button"
            className="btn btn-secondary ms-2"
            onClick={() => setModalReview(false)}
          >
            Cerrar
          </button>
        </form>
      </Modal.Body>
    </Modal>
  );
};