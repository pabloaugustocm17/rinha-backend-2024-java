package rinhabackend.api.models;

import rinhabackend.api.utils.Dictionary;

public enum TipoTransacao {

    DEBITO,
    CREDITO,
    DEFAULT;

    static TipoTransacao tipoTransacao(String informacao){

        switch (informacao){
            case Dictionary.DEBITO -> {
                return DEBITO;
            }
            case Dictionary.CREDITO -> {
                return CREDITO;
            }
        }

        return DEFAULT;
    }
}
