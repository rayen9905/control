package com.pfe.socket1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfe.Controller.*;
import com.pfe.DTO.PorteDto;
import com.pfe.entities.*;
import com.pfe.repos.HistoriqueRepository;
import com.pfe.repos.WaveRepository;
import jakarta.websocket.Session;
import jakarta.xml.bind.DatatypeConverter;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@RestController
public class MyController {
    @Autowired(required=true)
  HistoriqueService hs;
    @Autowired(required=true)
    HistoriqueRepository hss;
    @Autowired(required=true)
    UserService us;
    @Autowired(required=true)
    PorteService ps;
    @Autowired(required=true)
    EventService es;
    @Autowired(required=true)
    WaveService ws;
    @Autowired(required=true)
    WaveRepository wss;
   /* @Autowired
    public MyController(HistoriqueService h,UserService u,PorteService p) {
        this.hs=h;
        this.us=u;
        this.ps=p;
    }*/
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

                //  System.out.println("aniii jayyy");


                // client1.sendMessage("Hello, server!");

                while (true) {
                    client = socket.accept();
                    rep="";
                    System.out.println("new client connected");
                    InputStream inputStream = client.getInputStream();
                    //rep = client.getInetAddress().getHostAddress();
                    while (true) {
                        rep = rep + Integer.toHexString(inputStream.read());
                        if (rep.length() > 77) {
                            System.out.println(rep);
                            if (rep.contains("31012")) {
                                String mac = rep.substring(0,12);
                                System.out.println(mac);
                                Porte pr = ps.getbyadr(mac);
                                List<Event> ww=new ArrayList<>();
                                WaveShare w=ws.getwbyid(mac);
                                ww=w.getEvents();

                            LocalDateTime date = LocalDateTime.now();
                            Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
                            Event e= new Event();
                            e.setDateEvent(date1);
                            e.setEtEvent(Type_Evt.Entry_Open);
                               // ww.add(w);
                            //e.setWaves(ww);
                            Event e1=es.addevt(e);
                                ww.add(e1);
                                w.setEvents(ww);
                                wss.save(w);
                                Historique h=hs.gethisbyprt(pr.getIdPorte());
                                h.setIdEvent(e1.getIdEvent());
                                hss.save(h);
                            Map<String, Object> jsonObject = new HashMap<>();
                            jsonObject.put("idporte", pr.getIdPorte());
                            jsonObject.put("etatevt", e1.getEtEvent());
                            jsonObject.put("dateevnt", e1.getDateEvent());
                            jsonObject.put("idevent",e1.getIdEvent());
                            ObjectMapper objectMapper = new ObjectMapper();
                            String jsonString = objectMapper.writeValueAsString(jsonObject);

                          /*  User u = new User();
                            Porte p = new Porte();
                            Optional<User> u1 = us.getbyid();
                            Optional<Porte> p1 = ps.getbyid();
                            u.setId(u1.get().getId());
                            p.setIdPorte(p1.get().getIdPorte());
                            Historique h1 = new Historique(p,u);
                            Long a = hs.addhiss(h1);
                            Historique h2=hs.getbyid(a);*/
                        client1.sendMessage(jsonString);
                            } else if ((rep.contains("120089"))&&(!rep.contains("120085f"))) {
                                String mac = rep.substring(0,12);
                                System.out.println(mac);
                                Porte pr = ps.getbyadr(mac);
                                List<Event> ww=new ArrayList<>();
                                WaveShare w=ws.getwbyid(mac);
                                ww=w.getEvents();
                                LocalDateTime date = LocalDateTime.now();
                                Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
                                Event e= new Event();
                                e.setDateEvent(date1);
                                e.setEtEvent(Type_Evt.Entry_Close);
                                Event e1=es.addevt(e);
                                ww.add(e1);
                                w.setEvents(ww);
                                wss.save(w);
                                Map<String, Object> jsonObject = new HashMap<>();
                                jsonObject.put("idporte", pr.getIdPorte());
                                jsonObject.put("etatevt", e1.getEtEvent());
                                jsonObject.put("dateevnt", e1.getDateEvent());
                                jsonObject.put("idevent",e1.getIdEvent());
                                ObjectMapper objectMapper = new ObjectMapper();
                                String jsonString = objectMapper.writeValueAsString(jsonObject);
                                client1.sendMessage(jsonString);

                            } else if (rep.contains("31011")) {
                                String mac = rep.substring(0,12);
                                System.out.println(mac);
                                Porte pr = ps.getbyadr(mac);
                                List<Event> ww=new ArrayList<>();
                                WaveShare w=ws.getwbyid(mac);
                                ww=w.getEvents();
                                LocalDateTime date = LocalDateTime.now();
                                Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
                                Event e= new Event();
                                e.setDateEvent(date1);
                                e.setEtEvent(Type_Evt.Exist_Open);
                                Event e1=es.addevt(e);
                                ww.add(e1);
                                w.setEvents(ww);
                                wss.save(w);
                                Historique h=hs.gethisbyprt(pr.getIdPorte());
                                h.setIdEvent(e1.getIdEvent());
                                hss.save(h);
                                Map<String, Object> jsonObject = new HashMap<>();
                                jsonObject.put("idporte", pr.getIdPorte());
                                jsonObject.put("etatevt", e1.getEtEvent());
                                jsonObject.put("dateevnt", e1.getDateEvent());
                                jsonObject.put("idevent",e1.getIdEvent());
                                ObjectMapper objectMapper = new ObjectMapper();
                                String jsonString = objectMapper.writeValueAsString(jsonObject);
                                client1.sendMessage(jsonString);

                            } else if (rep.contains("120088")) {
                                String mac = rep.substring(0,12);
                                System.out.println(mac);
                                Porte pr = ps.getbyadr(mac);
                                List<Event> ww=new ArrayList<>();
                                WaveShare w=ws.getwbyid(mac);
                                ww=w.getEvents();
                                LocalDateTime date = LocalDateTime.now();
                                Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
                                Event e= new Event();
                                e.setDateEvent(date1);
                                e.setEtEvent(Type_Evt.Exist_Close);
                                Event e1=es.addevt(e);
                                ww.add(e1);
                                w.setEvents(ww);
                                wss.save(w);
                                Map<String, Object> jsonObject = new HashMap<>();
                                jsonObject.put("idporte", pr.getIdPorte());
                                jsonObject.put("etatevt", e1.getEtEvent());
                                jsonObject.put("dateevnt", e1.getDateEvent());
                                jsonObject.put("idevent",e1.getIdEvent());
                                ObjectMapper objectMapper = new ObjectMapper();
                                String jsonString = objectMapper.writeValueAsString(jsonObject);
                                client1.sendMessage(jsonString);

                            } else if (rep.contains("1200860")) {
                                String mac = rep.substring(0,12);
                                System.out.println(mac);
                                Porte pr = ps.getbyadr(mac);
                                List<Event> ww=new ArrayList<>();
                                WaveShare w=ws.getwbyid(mac);
                                ww=w.getEvents();
                                LocalDateTime date = LocalDateTime.now();
                                Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
                                Event e= new Event();
                                e.setDateEvent(date1);
                                e.setEtEvent(Type_Evt.Intrusion_Alarm);
                                Event e1=es.addevt(e);
                                ww.add(e1);
                                w.setEvents(ww);
                                wss.save(w);
                                Map<String, Object> jsonObject = new HashMap<>();
                                jsonObject.put("idporte", pr.getIdPorte());
                                jsonObject.put("etatevt", e1.getEtEvent());
                                jsonObject.put("dateevnt", e1.getDateEvent());
                                jsonObject.put("idevent",e1.getIdEvent());
                                ObjectMapper objectMapper = new ObjectMapper();
                                String jsonString = objectMapper.writeValueAsString(jsonObject);
                                client1.sendMessage(jsonString);

                            } else if (rep.contains("120085f")) {
                                String mac = rep.substring(0,12);
                                System.out.println(mac);
                                Porte pr = ps.getbyadr(mac);
                                List<Event> ww=new ArrayList<>();
                                WaveShare w=ws.getwbyid(mac);
                                ww=w.getEvents();
                                LocalDateTime date = LocalDateTime.now();
                                Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
                                Event e= new Event();
                                e.setDateEvent(date1);
                                e.setEtEvent(Type_Evt.Stayed_On);
                                Event e1=es.addevt(e);
                                ww.add(e1);
                                w.setEvents(ww);
                                wss.save(w);
                                Map<String, Object> jsonObject = new HashMap<>();
                                jsonObject.put("idporte", pr.getIdPorte());
                                jsonObject.put("etatevt", e1.getEtEvent());
                                jsonObject.put("dateevnt", e1.getDateEvent());
                                jsonObject.put("idevent",e1.getIdEvent());
                                ObjectMapper objectMapper = new ObjectMapper();
                                String jsonString = objectMapper.writeValueAsString(jsonObject);
                                client1.sendMessage(jsonString);

                            } else if (rep.contains("1200862")) {
                                String mac = rep.substring(0,12);
                                System.out.println(mac);
                                Porte pr = ps.getbyadr(mac);
                                List<Event> ww=new ArrayList<>();
                                WaveShare w=ws.getwbyid(mac);
                                ww=w.getEvents();
                                LocalDateTime date = LocalDateTime.now();
                                Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
                                Event e= new Event();
                                e.setDateEvent(date1);
                                e.setEtEvent(Type_Evt.Tailing_Alarm);
                                Event e1=es.addevt(e);
                                ww.add(e1);
                                w.setEvents(ww);
                                wss.save(w);
                                Map<String, Object> jsonObject = new HashMap<>();
                                jsonObject.put("idporte", pr.getIdPorte());
                                jsonObject.put("etatevt", e1.getEtEvent());
                                jsonObject.put("dateevnt", e1.getDateEvent());
                                jsonObject.put("idevent",e1.getIdEvent());
                                ObjectMapper objectMapper = new ObjectMapper();
                                String jsonString = objectMapper.writeValueAsString(jsonObject);
                                client1.sendMessage(jsonString);

                            } else if (rep.contains("1200861")) {
                                String mac = rep.substring(0,12);
                                System.out.println(mac);
                                Porte pr = ps.getbyadr(mac);
                                List<Event> ww=new ArrayList<>();
                                WaveShare w=ws.getwbyid(mac);
                                ww=w.getEvents();
                                LocalDateTime date = LocalDateTime.now();
                                Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
                                Event e= new Event();
                                e.setDateEvent(date1);
                                e.setEtEvent(Type_Evt.Reverse_Alarm);
                                Event e1=es.addevt(e);
                                ww.add(e1);
                                w.setEvents(ww);
                                wss.save(w);
                                Map<String, Object> jsonObject = new HashMap<>();
                                jsonObject.put("idporte", pr.getIdPorte());
                                jsonObject.put("etatevt", e1.getEtEvent());
                                jsonObject.put("dateevnt", e1.getDateEvent());
                                jsonObject.put("idevent",e1.getIdEvent());
                                ObjectMapper objectMapper = new ObjectMapper();
                                String jsonString = objectMapper.writeValueAsString(jsonObject);
                                client1.sendMessage(jsonString);

                            }
                            // rep=client.getInetAddress().getHostAddress();

                            rep = "9ca525b998e4";

                        }
                        //rep="";
                        //System.out.println(rep);

                    }
                    //rep="";
                    //}
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error connecting to Waveshare device");
            }
        }
    }
