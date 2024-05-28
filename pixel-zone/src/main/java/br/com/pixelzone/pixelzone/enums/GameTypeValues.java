package br.com.pixelzone.pixelzone.enums;

public enum GameTypeValues {

    FLIP(1, "FLIP"),
    JACKPOT(2, "JACKPOT"),
    ROBOT(3, "ROBOT");

    public int key;
    public String value;

    private GameTypeValues(int key, String value){

        this.key = key;
        this.value = value;

    }

    public static String getValues(int key){

        for(GameTypeValues gameTypeValues : GameTypeValues.values()){

            if(key == gameTypeValues.key){

                return gameTypeValues.value;

            }

        }

        return null;

    }
    
}
