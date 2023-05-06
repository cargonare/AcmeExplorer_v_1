package com.example.acmeexplorer_v_1.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class Trip implements Serializable {
    private String id;
    private String ciudadProcedencia;
    private String ciudadDestino;
    private Double precio;
    private Date fechaIda;
    private Date fechaVuelta;
    private Boolean seleccionar;
    private String urlImagenes;

    public Trip(String id, String ciudadProcedencia, String ciudadDestino, Double precio, Date fechaIda, Date fechaVuelta, Boolean seleccionar, String urlImagenes) {
        this.id = id;
        this.ciudadProcedencia = ciudadProcedencia;
        this.ciudadDestino = ciudadDestino;
        this.precio = precio;
        this.fechaIda = fechaIda;
        this.fechaVuelta = fechaVuelta;
        this.seleccionar = seleccionar;
        this.urlImagenes = urlImagenes;
    }

    public Trip() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCiudadProcedencia() {
        return ciudadProcedencia;
    }

    public void setCiudadProcedencia(String ciudadProcedencia) {
        this.ciudadProcedencia = ciudadProcedencia;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getFechaIda() {
        return fechaIda;
    }

    public void setFechaIda(Date fechaIda) {
        this.fechaIda = fechaIda;
    }

    public Date getFechaVuelta() {
        return fechaVuelta;
    }

    public void setFechaVuelta(Date fechaVuelta) {
        this.fechaVuelta = fechaVuelta;
    }

    public Boolean getSeleccionar() {
        return seleccionar;
    }

    public void setSeleccionar(Boolean seleccionar) {
        this.seleccionar = seleccionar;
    }

    public String getUrlImagenes() {
        return urlImagenes;
    }

    public void setUrlImagenes(String urlImagenes) {
        this.urlImagenes = urlImagenes;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", ciudadProcedencia='" + ciudadProcedencia + '\'' +
                ", ciudadDestino='" + ciudadDestino + '\'' +
                ", precio=" + precio +
                ", fechaIda=" + fechaIda +
                ", fechaVuelta=" + fechaVuelta +
                ", seleccionar=" + seleccionar +
                ", urlImagenes='" + urlImagenes + '\'' +
                '}';
    }

    public static ArrayList<Trip> generarViajes() {
        ArrayList<Trip> trips = new ArrayList<>();

       /* trips.add(new Trip("Madrid", "El Cairo", 200.20, transformarFecha("2023-05-10"), transformarFecha("2023-05-14"), false, "https://static.eldiario.es/clip/27d13b63-82d3-4955-b670-66aeff150d97_16-9-discover-aspect-ratio_default_0.jpg"));
        trips.add(new Trip("Oporto", "París", 400.40, transformarFecha("2023-01-01"), transformarFecha("2023-02-01"), false, "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/26/92/e4/97/disneyland-paris.jpg?w=1200&h=1200&s=1"));
        trips.add(new Trip("Londres", "Buenos Aires", 100.60, transformarFecha("2023-01-01"), transformarFecha("2023-01-03"), false, "https://estaticos-cdn.elperiodico.com/clip/80ccbd4a-9d52-4b2a-b2b3-e9c254b3447c_alta-libre-aspect-ratio_default_0.jpg"));
        trips.add(new Trip("Faro", "Barcelona", 50.30, transformarFecha("2023-02-03"), transformarFecha("2023-02-04"), false, "https://media.timeout.com/images/105737732/image.jpg"));
        trips.add(new Trip("Valencia", "Bilbao", 56.15, transformarFecha("2023-04-10"), transformarFecha("2023-04-13"), false, "https://www.autopista.es/uploads/s1/11/47/61/63/fantastica-panoramica-de-la-ciudad-de-bilbao.jpeg"));
        trips.add(new Trip("Vigo", "Roma", 500.30, transformarFecha("2023-07-01"), transformarFecha("2023-07-18"), false, "https://www.italia.it/content/dam/tdh/es/interests/lazio/roma/roma-in-48-ore/media/20220127150143-colosseo-roma-lazio-shutterstock-756032350.jpg"));
        trips.add(new Trip("Lisboa", "Nueva York", 234.30, transformarFecha("2023-04-15"), transformarFecha("2023-04-18"), false, "https://upload.wikimedia.org/wikipedia/commons/4/47/New_york_times_square-terabass.jpg"));
        trips.add(new Trip("Barcelona", "Los Ángeles", 93.42, transformarFecha("2023-03-22"), transformarFecha("2023-03-29"), false, "https://www.visittheusa.mx/sites/default/files/styles/hero_l/public/images/hero_media_image/2017-01/Getty_515070156_EDITORIALONLY_LosAngeles_HollywoodBlvd_Web72DPI_0.jpg?h=0a8b6f8b&itok=lst_2_5d"));
        trips.add(new Trip("Sevilla", "Estambul", 120.50, transformarFecha("2023-08-15"), transformarFecha("2023-08-18"), false, "https://a.cdn-hotels.com/gdcs/production6/d781/3bae040b-2afb-4b11-9542-859eeb8ebaf1.jpg?impolicy=fcrop&w=800&h=533&q=medium"));
        trips.add(new Trip("Granada", "Atenas", 85.67, transformarFecha("2023-06-04"), transformarFecha("2023-06-10"), false, "https://www.worldhistory.org/img/r/p/500x600/3372.jpg?v=1669307043"));*/
        return trips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(ciudadProcedencia, trip.ciudadProcedencia) && Objects.equals(ciudadDestino, trip.ciudadDestino) && Objects.equals(precio, trip.precio) && Objects.equals(fechaIda, trip.fechaIda) && Objects.equals(fechaVuelta, trip.fechaVuelta) && Objects.equals(urlImagenes, trip.urlImagenes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ciudadProcedencia, ciudadDestino, precio, fechaIda, fechaVuelta, seleccionar, urlImagenes);
    }
}
