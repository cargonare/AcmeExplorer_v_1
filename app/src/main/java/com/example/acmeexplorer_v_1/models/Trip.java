package com.example.acmeexplorer_v_1.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Trip implements Serializable {
    private int id;
    private String ciudadProcedencia;
    private String ciudadDestino;
    private Double precio;
    private LocalDate fechaIda;
    private LocalDate fechaVuelta;
    private Boolean seleccionar;
    private String urlImagenes;

    public Trip(int id, String ciudadProcedencia, String ciudadDestino, Double precio, LocalDate fechaIda, LocalDate fechaVuelta, Boolean seleccionar, String urlImagenes) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public LocalDate getFechaIda() {
        return fechaIda;
    }

    public void setFechaIda(LocalDate fechaIda) {
        this.fechaIda = fechaIda;
    }

    public LocalDate getFechaVuelta() {
        return fechaVuelta;
    }

    public void setFechaVuelta(LocalDate fechaVuelta) {
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
                "id=" + id +
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
        Random random=new Random();

        trips.add(new Trip(random.nextInt(),"Miami", "Orlando", 200.20, LocalDate.of(2023, 05, 10), LocalDate.of(2023, 10, 14), false, "https://www.barcelo.com/guia-turismo/wp-content/uploads/2021/12/fin-de-semana-madrid-pal-3.jpg"));
        trips.add(new Trip(random.nextInt(), "Seville", "Madrid", 400.40, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 2, 1), false, "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/26/92/e4/97/disneyland-paris.jpg?w=1200&h=1200&s=1"));
        trips.add(new Trip(random.nextInt(), "Barquisimeto", "Caracas", 100.60, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 3), false, "http://png.pngtree.com/element_pic/16/09/12/2357d6c812acf90.jpg"));
        trips.add(new Trip(random.nextInt(), "Madrid", "Barcelona", 50.30, LocalDate.of(2023, 2, 3), LocalDate.of(2023, 2, 4), false, "http://png.pngtree.com/element_pic/20/16/01/3156adb71123719.jpg"));
        trips.add(new Trip(random.nextInt(), "Valencia", "Sevilla", 56.15, LocalDate.of(2023, 4, 10), LocalDate.of(2023, 4, 13), false, "http://png.pngtree.com/element_pic/30/03/20/1656fbd4b4641fc.jpg"));
        trips.add(new Trip(random.nextInt(), "Quito", "Amsterdam", 500.30, LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 18), false, "http://png.pngtree.com/element_pic/00/00/00/0056a3602a2cf41.jpg"));

        return trips;
    }
}
