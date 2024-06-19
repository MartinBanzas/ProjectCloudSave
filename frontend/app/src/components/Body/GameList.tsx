import React, { useCallback, useEffect, useState } from "react";
import { getGames as getGamesFromAPI, getImg } from "./Requests";
import GameModel from "../../models/Models";
import "./GameList.css";

export const GameList = () => {
  const [games, setGames] = useState<GameModel[]>([]);
  const [gamesImg, setGamesImg] = useState<{ [key: number]: string }>({});

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

  console.log(games);

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
                    : gamesImg[game.id]
                }
                alt={game.name}
                className="card-img-top game-img img-fluid"
              />
              <div className="card-body bg-white">
                {game.terminado ? <div className="band">Done!</div> : null}
                <span>{game.name}</span>
                <div className="card-footer bg-white">
                  <div className="buttonYreview">
                    <button className="btn btn small review-button">
                      <svg
                        // style="margin-right: 2px; cursor: pointer;"
                        width="12"
                        height="12"
                        xmlns="http://www.w3.org/2000/svg"
                        className="ipc-icon  ipc-icon--star-inline"
                        viewBox="0 0 24 24"
                        fill="#ffd700"
                        role="presentation"
                        id="open-modal-button"
                        data-bs-toggle="modal"
                        data-bs-target="#reviewModal"
                      />
                      <path d="M12 20.1l5.82 3.682c1.066.675 2.37-.322 2.09-1.584l-1.543-6.926 5.146-4.667c.94-.85.435-2.465-.799-2.567l-6.773-.602L13.29.89a1.38 1.38 0 0 0-2.581 0l-2.65 6.53-6.774.602C.052 8.126-.453 9.74.486 10.59l5.147 4.666-1.542 6.926c-.28 1.262 1.023 2.26 2.09 1.585L12 20.099z"/>
                    </button>
                    <span className="puntuacion text-color-blue">{game.puntuacion}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
