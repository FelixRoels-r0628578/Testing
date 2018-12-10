package model;

import exception.ModelException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Adres {

    private String straat, plaats, huisnummer;
    private int bus;

    public Adres(String straat, String plaats, String huisnummer, int bus) throws ModelException {
        setStraat(straat);
        setHuisnummer(huisnummer);
        setBus(bus);
        setPlaats(plaats);
    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) throws ModelException {
        this.huisnummer = huisnummer;
        //TODO kijk na of de regex werkt.
        if(huisnummer.isEmpty() || huisnummer == null){
            throw new ModelException("Huisnummer kan niet leeg zijn");
        }
        String HUISNUMMER_PATTERN = "^(.\\d{3}[a-zA-Z])$";
        Pattern p = Pattern.compile(HUISNUMMER_PATTERN);
        Matcher m = p.matcher(huisnummer);
        if(!m.matches()){
            throw new ModelException("Huisnummer not valid");
        }
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
}
