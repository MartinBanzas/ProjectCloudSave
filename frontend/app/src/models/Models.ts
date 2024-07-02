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
    review?: string;
    year?: string;
    company?:string;
    main?: string;
    main_extra?: string;
    completionist?: string



    constructor (id:number, name:string, sistema:string, directorio:string, puntuacion:number, terminado:boolean
        ,finicio:Date, ffin:Date, tituloReview:string, review:string, year:string, company:string, main:string, main_extra:string, completionist:string) {
            this.id=id;
            this.name=name,
            this.sistema=sistema,
            this.directorio=directorio,
            this.puntuacion=puntuacion,
            this.terminado=terminado,
            this.finicio=finicio,
            this.ffin=ffin,
            this.tituloReview=tituloReview,
            this.review=review,
            this.company=company,
            this.year=year;
            this.main=main;
            this.main_extra=main_extra,
            this.completionist=completionist
        }
        
}

export default GameModel