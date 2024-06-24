class GameModel {

    id:number;
    name:string;
    sistema:string;
    directorio:string;
    puntuacion?: number;
    terminado?:boolean;
    finicio?:Date;
    ffin?:Date;
    tituloReview?:string;
    review?: string


    constructor (id:number, name:string, sistema:string, directorio:string, puntuacion:number, terminado:boolean
        ,finicio:Date, ffin:Date, tituloReview:string, review:string) {
            this.id=id;
            this.name=name,
            this.sistema=sistema,
            this.directorio=directorio,
            this.puntuacion=puntuacion,
            this.terminado=terminado,
            this.finicio=finicio,
            this.ffin=ffin,
            this.tituloReview=tituloReview,
            this.review=review
        }
        
}

export default GameModel