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

export const addGame = async (
  name: string,
  directorio: string,
  sistema: string
) => {
  const ADD_GAME = "juegos/addGame";

  const data = { name, directorio, sistema };

  try {
    const response = await fetch(`${API_BASE_URL}${ADD_GAME}`, {
      method: "POST",
      body: JSON.stringify(data),
    });
    if (response.ok) {
      return "Añadido con éxito";
    }
  } catch (error: any) {
    console.error("Error adding game:", error);
    return `Error: ${error.message}`;
  }
};

export const addReview = async (
  gameId: number,
  rate: number,
  reviewName: string,
  reviewComments: string
) => {
  const ADD_REVIEW = "juegos/review";
console.log(gameId)
  const data = { gameId, rate, reviewName, reviewComments };

  try {
    const response = await fetch(`${API_BASE_URL}${ADD_REVIEW}`, {
      headers: {
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify(data),

    });
    console.log(response.ok)
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
