import React, { useCallback, useEffect, useState } from "react";
import { getGames as getGamesFromAPI, getImg } from "./Requests";
import GameModel from "../../models/Models";
import "./GameList.css";
import star from "../../static/icons/star.svg"
import placeholder from "../../../src/static/img/placeholder.png"

import { ModalReview } from "./modals/ModalReview";


export const GameList = () => {
  const [games, setGames] = useState<GameModel[]>([]);
  const [gamesImg, setGamesImg] = useState<{ [key: number]: string }>({});
  const [modalReview, setModalReview]= useState<boolean>(false)
  const [selectedGameId, setSelectedGameId]=useState<number>();

  const obtainGameListCallback = useCallback(async () => {
    const data = await getGamesFromAPI();
    setGames(data);
  }, []);

  useEffect(() => {
    obtainGameListCallback();
  }, [obtainGameListCallback]);

  const obtainImagesCallback = useCallback(async () => {
    const urls: { [key: number]: string } = {};
    for (const game of games) {
      const url = await getImg(game.id);
      if (typeof url === "string") {
        urls[game.id] = url;
      }
    }
    setGamesImg(urls);
  }, [games]);

  useEffect(() => {

    if (games.length > 0) {
      obtainImagesCallback();
    }
  }, [games, obtainImagesCallback]);


  const handleClick = (gameId:number) => {
    setModalReview(true);
    setSelectedGameId(gameId)
  }

  return (
    
    <div className="container">
      <div className="row">
        {games.map((game) => (
          <div
            key={game.id.toString()}
            className="col-lg-2 col-md-3 col-sm-6 col-6 game-card card-with-band"
          >
            <div className="card border-dark game-card">
              <img
                src={
                  gamesImg[game.id] != null
                    ? gamesImg[game.id]
                    : placeholder
                }
                alt={game.name}
                className="card-img-top game-img img-fluid"
              />
              <div className="card-body ">
                {game.terminado ? <div className="band">Done!</div> : null}
                <span className="game-name mb-2">{game.name}</span>
                <div className="card-footer">
                  <div className="buttonYreview">
                    <button  onClick={()=>handleClick(game.id)} className="btn btn text-dark small review-button">
                    <img src={star} className="starIcon" height="16px" width="16px"></img>
                    </button>
                    <span className="puntuacion text-white">{game.puntuacion}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
      <ModalReview modalReview={modalReview} setModalReview={setModalReview} gameId={selectedGameId!}/>
    </div>
  );
};
