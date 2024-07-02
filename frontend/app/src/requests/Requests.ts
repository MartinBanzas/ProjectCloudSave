const API_BASE_URL = "http://localhost:8080/";

export const getGames = async () => {
  const GET_JUEGOS = "juegos/all";

  try {
    const response = await fetch(`${API_BASE_URL}${GET_JUEGOS}`, {
      method: "GET",
    });

    if (!response.ok) {
      throw new Error("Error fetching logs");
    }

    return response.json();
  } catch (error: any) {
    return error;
  }
};

export const getImg = async (id: number) => {
  const IMG = `juegos/img/${id}`;
  try {
    const response = await fetch(`${API_BASE_URL}${IMG}`);
    if (response.ok) {
      const blob = await response.blob();
      return URL.createObjectURL(blob);
    }
  } catch (error) {
    return error;
  }
};

export const addGame = async (name: string, sistema: string, hltb: any) => {
  const ADD_GAME = "juegos/addGame";
  const data = {
    name,
    sistema,
    main: hltb.game_main,
    main_extra: hltb.game_extra,
    completionist: hltb.game_completionist,
    year: hltb.year,
    company: hltb.company,
  };

  console.log(data);

  try {
    const response = await fetch(`${API_BASE_URL}${ADD_GAME}`, {
      headers: {
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify(data),
    });

    if (response.ok) {
      return "Añadido con éxito";
    } else {
      console.error("Error adding game:", response.status, response.statusText);
      return `Error: ${response.status} ${response.statusText}`;
    }
  } catch (error: any) {
    console.error("Error adding game:", error);
    return `Error: ${error.message}`;
  }
};

export const hltbRequest = async (gameName: string) => {
  const url = `http://localhost:5000/searchHLTB/${gameName}`;

  console.log(gameName);
  try {
    const response = await fetch(url, {
      headers: {
        "Content-Type": "application/json",
      },
      method: "GET",
    });

    if (response.ok) {
      const json = await response.json();
     
      const game = json.find((game: any) => game.game_name === gameName);

      const obj = {
        game_main: game.game_main,
        game_extra: game.game_extra,
        game_completionist: game.game_completionist,
        company: game.profile_dev,
        year: game.release_world,
      };
     
      return obj;
    } else {
      console.error(
        "Error fetching data:",
        response.status,
        response.statusText
      );
    }
  } catch (error: any) {
    console.error("Error during request:", error);
  }

  // Retorna algo significativo en caso de error o si no hay coincidencias
  return null;
};

export const addReview = async (
  gameId: number,
  rate: number,
  reviewName: string,
  reviewComments: string
) => {
  const ADD_REVIEW = "juegos/review";
  const data = { gameId, rate, reviewName, reviewComments };

  try {
    const response = await fetch(`${API_BASE_URL}${ADD_REVIEW}`, {
      headers: {
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify(data),
    });
    console.log(response.ok);
    return response.ok;
  } catch (error) {
    return error;
  }
};

export const updateGame = async (
  id: number,
  name: string,
  sistema: string,
  directorio: string
) => {
  const UPDATE_GAME = "juegos/update/";
  try {
    const response = await fetch(`${API_BASE_URL}${UPDATE_GAME}/${id}`, {
      method: "PATCH",
      body: JSON.stringify({ name, sistema, directorio }),
    });

    return response.ok;
  } catch (error) {
    return error;
  }
};

export const markCompleted = async (
  id: number,
  fechaInicio: Date,
  fechaFin: Date
) => {
  const COMPLETED = "juegos/completo";

  try {
    const response = await fetch(`${API_BASE_URL}${COMPLETED}`, {
      method: "POST",
      body: JSON.stringify({ id, fechaInicio, fechaFin }),
    });
    return response;
  } catch (error) {
    return error;
  }
};

export const searchGame = async (nombre: string) => {
  const SEARCH = "juegos/buscar";
  try {
    const response = await fetch(
      `${API_BASE_URL}${SEARCH}?nombre=${encodeURIComponent(nombre)}`,
      {
        method: "GET",
      }
    );
    return response.json();
  } catch (error) {
    return error;
  }
};
