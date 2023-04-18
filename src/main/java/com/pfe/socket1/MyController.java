package com.pfe.socket1;

import com.pfe.Controller.HistoriqueService;
import com.pfe.Controller.PorteService;
import com.pfe.Controller.UserService;
import com.pfe.entities.Historique;
import com.pfe.entities.Porte;
import com.pfe.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@RestController
public class MyController {
    @Autowired(required=true)
  HistoriqueService hs;
    @Autowired(required=true)
    UserService us;
    @Autowired(required=true)
    PorteService ps;
   /* @Autowired
    public MyController(HistoriqueService h,UserService u,PorteService p) {
        this.hs=h;
        this.us=u;
        this.ps=p;
    }*/
@PostMapping("/webs")
        public void main() throws Exception {
            String rep="";
            try {
                // Connect to the Waveshare device
               // ServerSocket socket = new ServerSocket(13001);
                CountDownLatch latch = new CountDownLatch(1);
                WebSocketClient client1 = new WebSocketClient(latch);
                client1.connect("ws://localhost:8080/websocket");
                System.out.println("aniii jayyy");
                client1.sendMessage("Hello, server!");

               /* while(true) {
                   Socket client = socket.accept();
                    System.out.println("new client connected");
                    //BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    // Read incoming data from the socket's input stream
                    //String data = reader.readLine();

                    // Process the incoming data here
                   // System.out.println("Received data: " + data);

                   InputStream inputStream = client.getInputStream();
                   //String ao=Integer.toHexString(inputStream.read());
                   // System.out.println(ao);
                    while(true){
                    rep=rep+Integer.toHexString(inputStream.read());

                    if(rep.length()>76){
                        if(rep.contains("31012")){
                            User u = new User();
                            Porte p = new Porte();
                            Optional<User> u1 = us.getbyid();
                            Optional<Porte> p1 = ps.getbyid();
                            u.setId(u1.get().getId());
                            p.setIdPorte(p1.get().getIdPorte());
                            Historique h1 = new Historique(p,u);
                            Long a = hs.addhiss(h1);
                            Historique h2=hs.getbyid(a);
                            client1.sendMessage1(h2.getIdHis());
                        } /*else if (rep.contains("120089")) {
                            
                        } else if (rep.contains("31011")){

                        } else if (rep.contains("12088")) {

                        } else if (rep.contains("1200860")) {

                        }else if(rep.contains("120085F")){

                        } else if (rep.contains("1200862")) {

                        } else if (rep.contains("1200861")) {

                        }
                        //System.out.println(rep);
                        //rep="9ca525b998e4";
                  //rep=client.getInetAddress().getHostAddress();

                    }
                    }
                }*/
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error connecting to Waveshare device");
            }
        }
    }
