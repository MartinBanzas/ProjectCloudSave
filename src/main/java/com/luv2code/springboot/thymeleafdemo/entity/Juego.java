package com.luv2code.springboot.thymeleafdemo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.type.NumericBooleanConverter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name="juego")
public class Juego {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="Nombre")
    private String name;

    @Column(name="Sistema")
    private String Sistema;

    @Column(name="Directorio")
    private String Directorio;


    @Column(name="puntuacion")
    private int puntuacion;

    @Column(name = "terminado", columnDefinition = "TINYINT(1)")
    private boolean terminado;

    @Column(name = "fInicio")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fInicio;

    @Column(name = "fFin")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fFin;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "juego", cascade={CascadeType.ALL})
    private List<Partida> listaPartidas;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "juego", cascade = CascadeType.ALL, orphanRemoval = true)
    private Img img;


    public Juego() {}

    public Juego(String name, String sistema, String directorio) {
        this.name = name;
        this.Sistema = sistema;
        this.Directorio = directorio;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", sistema='" + Sistema + '\'' +
                ", directorio='" + Directorio + '\'' +
                ", wiki='"  + '\'' +
                ", ultimaActu="  +
                '}';
    }

    public List<Partida> getListaPartidas() {return listaPartidas;}

    public void setListaPartidas(List<Partida> listaPartidas) {
        this.listaPartidas = listaPartidas;
    }

    public void add(Partida tempPartida) {

        if (listaPartidas == null) {
            listaPartidas=new ArrayList<>();
        }
        listaPartidas.add(tempPartida);
        tempPartida.setJuego(this);
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSistema() {
        return Sistema;
    }

    public void setSistema(String sistema) {
        this.Sistema = sistema;
    }


    public String getDirectorio() {
        return Directorio;
    }

    public void setDirectorio(String directorio) {
        this.Directorio = directorio;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public String getfInicio() {

        if (fInicio == null) {
            return "Sin establecer";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaFormateada = sdf.format(fInicio);
        return fechaFormateada;
    }

    public void setfInicio(Date fInicio) {
        this.fInicio = fInicio;
    }

    public String getfFin() {

        if (fFin == null) {
            return "Sin establecer";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaFormateada = sdf.format(fFin);

        return fechaFormateada;

    }

    public void setfFin(Date fFin) {
        this.fFin = fFin;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {

        this.puntuacion = puntuacion;

    }

}
