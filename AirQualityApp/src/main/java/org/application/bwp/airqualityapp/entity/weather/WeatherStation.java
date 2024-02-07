package org.application.bwp.airqualityapp.entity.weather;

import java.time.LocalDate;

public class WeatherStation {
    private int id_stacji;
    private String stacja;
    private LocalDate data_pomiaru;
    private int godzina_pomiaru;
    private double temperatura;
    private double predkosc_wiatru;
    private int kierunek_wiatru;
    private double wilgotnosc_wzgledna;
    private double suma_opadu;
    private double cisnienie;

    public WeatherStation(int id_stacji, String stacja, LocalDate data_pomiaru, int godzina_pomiaru, double temperatura, double predkosc_wiatru, int kierunek_wiatru, double wilgotnosc_wzgledna, double suma_opadu, double cisnienie) {
        this.id_stacji = id_stacji;
        this.stacja = stacja;
        this.data_pomiaru = data_pomiaru;
        this.godzina_pomiaru = godzina_pomiaru;
        this.temperatura = temperatura;
        this.predkosc_wiatru = predkosc_wiatru;
        this.kierunek_wiatru = kierunek_wiatru;
        this.wilgotnosc_wzgledna = wilgotnosc_wzgledna;
        this.suma_opadu = suma_opadu;
        this.cisnienie = cisnienie;
    }

    public int getId_stacji() {
        return id_stacji;
    }

    public void setId_stacji(int id_stacji) {
        this.id_stacji = id_stacji;
    }

    public String getStacja() {
        return stacja;
    }

    public void setStacja(String stacja) {
        this.stacja = stacja;
    }

    public LocalDate getData_pomiaru() {
        return data_pomiaru;
    }

    public void setData_pomiaru(LocalDate data_pomiaru) {
        this.data_pomiaru = data_pomiaru;
    }

    public int getGodzina_pomiaru() {
        return godzina_pomiaru;
    }

    public void setGodzina_pomiaru(int godzina_pomiaru) {
        this.godzina_pomiaru = godzina_pomiaru;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getPredkosc_wiatru() {
        return predkosc_wiatru;
    }

    public void setPredkosc_wiatru(double predkosc_wiatru) {
        this.predkosc_wiatru = predkosc_wiatru;
    }

    public int getKierunek_wiatru() {
        return kierunek_wiatru;
    }

    public void setKierunek_wiatru(int kierunek_wiatru) {
        this.kierunek_wiatru = kierunek_wiatru;
    }

    public double getWilgotnosc_wzgledna() {
        return wilgotnosc_wzgledna;
    }

    public void setWilgotnosc_wzgledna(double wilgotnosc_wzgledna) {
        this.wilgotnosc_wzgledna = wilgotnosc_wzgledna;
    }

    public double getSuma_opadu() {
        return suma_opadu;
    }

    public void setSuma_opadu(double suma_opadu) {
        this.suma_opadu = suma_opadu;
    }

    public double getCisnienie() {
        return cisnienie;
    }

    public void setCisnienie(double cisnienie) {
        this.cisnienie = cisnienie;
    }

    @Override
    public String toString() {
        return "WeatherStation{" +
                "id_stacji=" + id_stacji +
                ", stacja='" + stacja + '\'' +
                ", data_pomiaru=" + data_pomiaru +
                ", godzina_pomiaru=" + godzina_pomiaru +
                ", temperatura=" + temperatura +
                ", predkosc_wiatru=" + predkosc_wiatru +
                ", kierunek_wiatru=" + kierunek_wiatru +
                ", wilgotnosc_wzgledna=" + wilgotnosc_wzgledna +
                ", suma_opadu=" + suma_opadu +
                ", cisnienie=" + cisnienie +
                '}';
    }
}
