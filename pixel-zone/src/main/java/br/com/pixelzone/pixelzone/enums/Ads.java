package br.com.pixelzone.pixelzone.enums;

public enum Ads {

    NEGADO(0, "Negado"),
    APROVADO(1, "Aprovado");

    public int key;
    public String value;

    private Ads(int key, String value){

        this.key = key;
        this.value = value;

    }

    public String getValues(int key){

        for(Ads ad : Ads.values()){

            if(ad.key == key){

                return ad.value;

            }

        }

        return null;

    }
    
}
