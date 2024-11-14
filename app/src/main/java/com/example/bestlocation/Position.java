package com.example.bestlocation;

public class Position {
    int idposition;
    String pseudo,numero,longitude,latitude;

    @Override
    public String toString() {
        return "Position{" +
                "idposition=" + idposition +
                ", pseudo='" + pseudo + '\'' +
                ", numero='" + numero + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }

    public Position(int idposition, String latitude, String longitude, String numero, String pseudo) {
        this.idposition = idposition;
        this.latitude = latitude;
        this.longitude = longitude;
        this.numero = numero;
        this.pseudo = pseudo;
    }
}
