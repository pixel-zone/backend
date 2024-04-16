package br.com.pixelzone.pixelzone.enums;

public enum UserTypeEnum {

    JOGADOR(1, "JOGADOR"),
    MODERADOR(2, "MODERADOR"),
    DESENVOLVEDOR(3, "DESENVOLVEDOR"),
    ANUNCIANTE(4, "ANUNCIANTE");

    public int key;
    public String value;

    private UserTypeEnum(int key, String value){
        this.key = key;
        this.value = value;
    }

    public String getValues(int key){

        for(UserTypeEnum userTypeEnum : UserTypeEnum.values()){

            if(userTypeEnum.key == key){

                return userTypeEnum.value;

            }

        }

        return null;

    }
    
}
