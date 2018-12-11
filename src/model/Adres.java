package model;

import exception.ModelException;

import javax.jws.WebParam;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Adres {

    private String straat, plaats, nummer;
    private int bus, postcode;

    public Adres(String straat, String plaats, String nummer, int bus, int postcode) throws ModelException {
        setPostcode(postcode);
        setStraat(straat);
        setNummer(nummer);
        setBus(bus);
        setPlaats(plaats);
    }

    public Adres(){

    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) throws ModelException {
        this.bus = bus;
        if(bus <= 0){
            throw new ModelException("Gelieve een geldige bus in te geven");
        }
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) throws ModelException {
        if(nummer == null || nummer.trim().isEmpty()){
            throw new ModelException("nummer mag niet leeg zijn");
        }
        if(Integer.parseInt(nummer) < 1){
            throw new ModelException("nummer moet groter dan 0 zijn");
        }
        this.nummer=nummer;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) throws ModelException {
        this.plaats = plaats;
        if(plaats.trim().isEmpty() || plaats == null){
            throw new ModelException("Plaats kan niet leeg zijn");
        }
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) throws ModelException {
        this.straat = straat;
        if(straat.trim().isEmpty() || straat == null){
            throw new ModelException("Straat kan niet leeg zijn");
        }
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) throws ModelException {
        this.postcode = postcode;
        if(postcode <= 1000 || postcode > 9999){
            throw new ModelException("Gelieve een geldige postcode in te geven");
        }
    }
}
