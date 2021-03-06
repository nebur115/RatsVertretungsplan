package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by User on 13.04.2018.
 */

public class Memory_Stunde {
    private String Fach;
    private String Lehrer;
    private String Raum;
    private boolean Freistunde;
    private boolean Schriftlich;
    private String Kursart;
    private String Fachkürzel;
    private int Kursnummer;
    private int StartJahr;
    private int pStartjahr;
    private String kürzel;


    public Memory_Stunde(){}




    public Memory_Stunde(Boolean pFreistunde, String pFach, String pFachkürzel, String pLehrer, String pRaum, String pKurart, int pKursnummer, Boolean pSchriftlich, int pStartjahr, String pkursname){
        StartJahr = pStartjahr;
        Kursart = pKurart;
        Fachkürzel = pFachkürzel;
        Schriftlich = pSchriftlich;
        Fach = pFach;
        Lehrer = pLehrer;
        Raum = pRaum;
        Freistunde = pFreistunde;
        Kursnummer = pKursnummer;
        kürzel = pkursname;
    }

    public void setpStartjahr(int pStartjahr) {
        this.pStartjahr = pStartjahr;
    }

    public void setKürzel(String kürzel) {
        this.kürzel = kürzel;
    }

    public int getpStartjahr() {

        return pStartjahr;
    }

    public String getKürzel() {
        return kürzel;
    }

    public int getStartJahr() {
        return StartJahr;
    }

    public void setStartJahr(int startJahr) {
        StartJahr = startJahr;
    }


    public int getKursnummer() {
        return Kursnummer;
    }

    public void setFreistunde(boolean freistunde) {
        Freistunde = freistunde;
    }

    public void setSchriftlich(boolean schriftlich) {
        Schriftlich = schriftlich;
    }

    public void setKursart(String kursart) {
        Kursart = kursart;
    }

    public void setFachkürzel(String fachkürzel) {
        Fachkürzel = fachkürzel;
    }

    public void setKursnummer(int kursnummer) {
        Kursnummer = kursnummer;
    }

    public String getFachkürzel() {
        return Fachkürzel;
    }



    public boolean isSchriftlich() {
        return Schriftlich;
    }

    public String getKursart(){return Kursart;}


    public boolean isFreistunde() {
        return Freistunde;
    }

    public String getFach() {
        return Fach;
    }

    public String getLehrer() {
        return Lehrer;
    }

    public String getRaum() {
        return Raum;
    }


    public void setFach(String fach) {
        Fach = fach;
    }

    public void setLehrer(String lehrer) {
        Lehrer = lehrer;
    }

    public void setRaum(String raum) {
        Raum = raum;
    }


}
