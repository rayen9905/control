package com.pfe.socket1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pfe.Controller.*;
import com.pfe.DTO.PorteDto;
import com.pfe.entities.*;
import com.pfe.repos.HistoriqueRepository;
import com.pfe.repos.WaveRepository;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import jakarta.xml.bind.DatatypeConverter;
import org.json.JSONObject;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@RestController
public class MyController {
    @Autowired(required=true)
    private HistoriqueService hs;
    @Autowired(required=true)
    private HistoriqueRepository hss;
    @Autowired(required=true)
    private UserService us;
    @Autowired(required=true)
    private PorteService ps;
    @Autowired(required=true)
    private EventService es;
    @Autowired(required=true)
    private WaveService ws;
    @Autowired(required=true)
    private WaveRepository wss;
    public String send(Historique h) throws JsonProcessingException {
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("idhis",h.getIdHis());
        jsonObject.put("date",h.getDateHistorique());
        jsonObject.put("time",h.getTimeHistorique());
        jsonObject.put("nomporte",h.getPrt().getNomPorte());
        jsonObject.put("Departement",h.getPrt().getCntrl().getDept().getNomDep());
        jsonObject.put("etat",h.getEtatHistorique());
        jsonObject.put("cause",h.getCause());
        jsonObject.put("idevent",h.getIdEvent());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = objectMapper.writeValueAsString(jsonObject);
        return jsonString;
    }
    public void info(String rep,WebSocketClient client1,Type_Evt a) throws IOException, EncodeException, DeploymentException, URISyntaxException {
        List<Event> ww = new ArrayList<>();
        Event e= new Event();
        String mac = rep.substring(0,12);
        //System.out.println(mac);
        Porte pr = ps.getbyadr(mac);
        WaveShare w=ws.getwbyid(mac);
        ww=w.getEvents();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        //Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        e.setDateEvent(date);
        e.setTimeEvent(time);
        e.setEtEvent(a);
        Event e1=es.addevt(e);
        ww.add(e1);
        w.setEvents(ww);
        wss.save(w);
        Historique h=hs.gethisbyprt(pr.getIdPorte());
        h.setIdEvent(e1.getIdEvent());
        String info=send(h);
        hss.save(h);
        client3 client=new client3();
        client.sendMessage(info);
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("idporte", pr.getIdPorte());
        jsonObject.put("nomporte", pr.getNomPorte());
        jsonObject.put("departement", pr.getCntrl().getDept().getNomDep());
        jsonObject.put("etatevt", e1.getEtEvent());
        jsonObject.put("dateevnt", e1.getDateEvent());
        jsonObject.put("timeevnt", e1.getTimeEvent());
        jsonObject.put("idevent",e1.getIdEvent());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = objectMapper.writeValueAsString(jsonObject);
         client1.sendMessage(jsonString);
    }
    public void info1(String rep,client1 client1,Type_Evt a) throws IOException, EncodeException {
        List<Event> ww = new ArrayList<>();
        String mac = rep.substring(0,12);
        //System.out.println(mac);
        Porte pr = ps.getbyadr(mac);
        WaveShare w=ws.getwbyid(mac);
        ww=w.getEvents();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time.format(formatter);
        LocalTime localTimee = LocalTime.parse(formattedTime, formatter);
        System.out.println(localTimee);
        Event e= new Event();
        //Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        e.setDateEvent(date);
        e.setTimeEvent(localTimee);
        e.setEtEvent(a);
        Event e1=es.addevt(e);
        ww.add(e1);
        w.setEvents(ww);
        wss.save(w);
        Map<String, Object> jsonObject = new HashMap<>();

        jsonObject.put("idporte", pr.getIdPorte());
        jsonObject.put("nomporte", pr.getNomPorte());
        jsonObject.put("departement", pr.getCntrl().getDept().getNomDep());
        jsonObject.put("etatevt", e1.getEtEvent());
        jsonObject.put("dateevnt", e1.getDateEvent());
        jsonObject.put("timeevnt", e1.getTimeEvent());
        jsonObject.put("idevent",e1.getIdEvent());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = objectMapper.writeValueAsString(jsonObject);
        //return jsonString;
        client1.sendMessage(jsonString);
    }
    public void info3(String rep,WebSocketClient client1,Type_Evt a) throws IOException, EncodeException, DeploymentException, URISyntaxException {
        List<Event> ww = new ArrayList<>();
        Event e= new Event();
        String mac = rep.substring(0,12);
        //System.out.println(mac);
        Porte pr = ps.getbyadr(mac);
        WaveShare w=ws.getwbyid(mac);
        ww=w.getEvents();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        //Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        e.setDateEvent(date);
        e.setTimeEvent(time);
        e.setEtEvent(a);
        Event e1=es.addevt(e);
        ww.add(e1);
        w.setEvents(ww);
        wss.save(w);
        /*Historique h=hs.gethisbyprt(pr.getIdPorte());
        h.setIdEvent(e1.getIdEvent());
        String info=send(h);
        Historique h1=hss.save(h);
        String info=send(h);
        client3 client=new client3();
        client.sendMessage(info);*/
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("idporte", pr.getIdPorte());
        jsonObject.put("nomporte", pr.getNomPorte());
        jsonObject.put("departement", pr.getCntrl().getDept().getNomDep());
        jsonObject.put("etatevt", e1.getEtEvent());
        jsonObject.put("dateevnt", e1.getDateEvent());
        jsonObject.put("timeevnt", e1.getTimeEvent());
        jsonObject.put("idevent",e1.getIdEvent());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = objectMapper.writeValueAsString(jsonObject);
        client1.sendMessage(jsonString);
    }

@PostMapping("/webs")
        public void main() throws Exception {
    Socket client = null;
    String rep;
    try {
        // Connect to the Waveshare device
        //String rep = "9ca525b998e4";
        ServerSocket socket = new ServerSocket(13001);
        CountDownLatch latch = new CountDownLatch(1);
        WebSocketClient client1 = new WebSocketClient();
        client1 client2 = new client1();


        while (true) {
            client = socket.accept();
           // client.getInetAddress().
            rep = "";
            System.out.println("new client connected");
            InputStream inputStream = client.getInputStream();
            //rep = client.getInetAddress().getHostAddress();
            while (true) {
                rep = rep + Integer.toHexString(inputStream.read());

                if (rep.length() > 77) {
                    System.out.println(rep);
                    if (rep.contains("1200860")) {
                        info1(rep, client2, Type_Evt.Intrusion_Alarm);

//||(rep.contains("120085f")&&rep.contains("120089"))
                    } else if ((rep.contains("120085f")) || (rep == "9ca525b998e425b998e4aa01e120085fb0050013ba9ca525b998e4aa01e120089400500135daa0")) {
                        info1(rep, client2, Type_Evt.Stayed_On);


                    } else if (rep.contains("1200862")) {
                        info1(rep, client2, Type_Evt.Tailing_Alarm);

                    } else if (rep.contains("1200861")) {
                        info1(rep, client2, Type_Evt.Reverse_Alarm);

                    } else if (rep.contains("31012")) {

                        info(rep, client1, Type_Evt.Entry_Open);

                    } else if ((rep.contains("120089"))) {

                        info3(rep, client1, Type_Evt.Entry_Close);


                    } else if (rep.contains("31011")) {
                        info(rep, client1, Type_Evt.Exist_Open);


                    } else if (rep.contains("120088")) {
                        info3(rep, client1, Type_Evt.Exist_Close);

                    }
                    // rep=client.getInetAddress().getHostAddress();

                    rep = rep.substring(0, 12);

                }
                //rep="";
                //System.out.println(rep);

            }
            //rep="";
        }
        //}
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error connecting to Waveshare device");
    }

}
    }
