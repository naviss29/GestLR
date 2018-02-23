export interface Conges {
    // using type any to avoid methods complaining of invalid type
    id?: any;
    typeConge: String;
    dateDebutConge: any;
    dateFinConge: any;
    periode?: String;
    signatureDemandeur?:boolean;
    signatureResponsable?:boolean;
    etat:Etat;
}

enum Etat {
    SoumisCollab,
    SoumisResp,
    SoumisRespMiss,
    Clot 
}
