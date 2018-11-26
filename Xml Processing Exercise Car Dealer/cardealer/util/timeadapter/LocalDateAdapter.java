package cardealer.util.timeadapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       return   LocalDate.parse(v, formatter);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
