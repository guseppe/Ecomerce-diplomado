package com.example.ecomerce_diplomado.utils;

public enum PhotoOptions {

    TAKE_PHOTO("Tomar foto"), CHOOSE_GALLERY("Buscar en la galería"), CHOOSE_FOLDER("Buscar en folder"), CANCEL("Cancelar");


    private String value;

    private PhotoOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
