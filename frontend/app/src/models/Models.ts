class GameModel {

    id:number;
    name:string;
    sistema:string;
    directorio:string;
    puntuacion?: number;
    terminado?:boolean;
    fInicio?:Date;
    fFin?:Date;
    titulo_review?:string;
    review?: string


    constructor (id:number, name:string, sistema:string, directorio:string, puntuacion:number, terminado:boolean
        ,fInicio:Date, fFin:Date, titulo_review:string, review:string) {
            this.id=id;
            this.name=name,
            this.sistema=sistema,
            this.directorio=directorio,
            this.puntuacion=puntuacion,
            this.terminado=terminado,
            this.fInicio=fInicio,
            this.fFin=fFin,
            this.titulo_review=titulo_review,
            this.review=review
        }
        
}

export default GameModel