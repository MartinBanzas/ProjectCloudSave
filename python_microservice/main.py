from fastapi import FastAPI, Request, HTTPException, APIRouter
from fastapi.responses import JSONResponse, ORJSONResponse
from hltb import HLTB
from starlette.middleware.cors import CORSMiddleware
from steamgrid import SteamGridDB,StyleType, PlatformType, MimeType, ImageType
import os

description = "Simple endpoint for HLTB. Microservice is required as HLTB doesn't allow CORS requests"

app = FastAPI(
    title="Python MicroService for HowLongToBeat and SteamGridDB API consultation",
    description=description,
    contact={'email': 'martin.antelo.jallas@gmail.com'}
)

SGDB_API_KEY=os.getenv('SGDB_API_KEY')
sgdb = SteamGridDB("bf17005eda7af4ff3201de2fa5003436")


# Uncomment this for API Key support.
# Be sure to have an .env file with 
# a parameter API_KEY 
""" @app.middleware("http")
async def check_api_key(request: Request, call_next):
    expected_api_key = os.getenv("API_KEY")
    if request.url.path in ["/docs", "/redoc"]:
        api_key = request.query_params.get("api_key")
        if not api_key:
            return JSONResponse(
                status_code=401, 
                content={"detail": "API Key required, pass it as parameter 'api_key' on the URL. Example: api_key=API"}
            )
        if api_key != expected_api_key:
            return JSONResponse(
                status_code=401, 
                content={"detail": "Wrong API Key."}
            )
    response = await call_next(request)
    return response """

@app.exception_handler(Exception)
async def exception_handler(request: Request, exc: Exception):
    return JSONResponse(
        status_code=500,
        content={
            "message": f"Unexpected Error: {exc.__class__.__name__}.",
            "description": str(exc)
        }
    )

@app.exception_handler(HTTPException)
async def http_exception_handler(request: Request, exc: HTTPException):
    return JSONResponse(
        status_code=exc.status_code,
        content={"message": exc.detail}
    )

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

router = APIRouter(
    tags=["Data"],
    default_response_class=ORJSONResponse
)

@router.get("/searchHLTB/{query}")
async def HLTBquery(query: str):
    hltb = HLTB()
    result = hltb.search(query)
    return result

@router.get("/obtain_game_id/{query}")
async def obtain_game_id(query: str):
    try:
        result = sgdb.search_game(query)
        for game in result:
            if (query in game.name):
                id = game.id 
                return id
    except Exception as e:
        raise HTTPException(status_code=404, detail=f"Game name didn't match with an id")

@router.get("/searchSG_with_steam_game_id/{query}")
async def search_grids_with_id(query:str):
    try:
        id = await obtain_game_id(query)
        grids = sgdb.get_grids_by_gameid([id])
        return grids
    except Exception as e:
        raise HTTPException(status_code=404, detail=f"Grids not found, refine your search parameters")

app.include_router(router)