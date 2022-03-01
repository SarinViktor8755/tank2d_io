package main.java.com.Units;


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Server;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.java.com.Network;

public class OutMassegeCollection {
    ConcurrentHashMap<Integer, OutMassege> outMassege;   // исходящие сообщения
    ListPlayers lp;
    Server serverGame;
    // ConcurrentSkipListSet<OutMassege> reservOutMassege;// резерв

    public OutMassegeCollection(ListPlayers lp, Server serverGame) {
        this.outMassege = new ConcurrentHashMap<Integer, OutMassege>();
        this.lp = lp;
        this.serverGame = serverGame;

//        this.reservOutMassege = new ConcurrentSkipListSet<OutMassege>();
//        for (int i = 0; i < 9_000; i++) {
//            OutMassege o = new OutMassege();
//            addMasssage(2,new Network.StockMess());
//        }
//
//        System.out.println(outMassege.size());
        /////////////////////////TEST
//        for (int i = 0; i < 1500; i++) {
//            addMasssage(i,new Network.StockMess());
//        }
//        mark_the_sent(-33);
//        System.out.println(outMassege);
//        System.out.println(outMassege.size());
    }


    ////////////////////////////////c
    public void tellMeYourNickname(int idUser) {
        // System.out.println("tellMeYourNickname");
        Network.StockMess m = new Network.StockMess();
        m.tip = Heading_type.MY_NIK;
        this.addMasssage(idUser, m);
    }

    public void sendPlayerDiscount(int idDisconect) { // cскать всем что игрок отдисканектился
        Network.StockMess m = new Network.StockMess();
        m.tip = Heading_type.DISCONECT_PLAYER;
        m.p1 = idDisconect;
        this.createMessageDorEveryone(m);
    }

    public boolean isExists(int nom){
        return outMassege.containsKey(nom);
    }

    public void sendPlayerHP(int nomPlayer, int HP) { // cскать всем что игрок про ХП игрока
        Network.StockMess m = new Network.StockMess();
        m.tip = Heading_type.HP_PLAYER;
        m.p1 = nomPlayer;
        m.p2 = HP;
        //System.out.println(m);
        this.createMessageDorEveryone(m);
       // System.out.println("send HP player");
    }

    public void sendPlayerHP(int nomPlayer, float HP) {
        sendPlayerHP(nomPlayer, (int)HP);
    }



    public void tellParamForPlayer(int fromidUser, Player p) {
        System.out.println("tellParamForPlayer IF:: " + fromidUser + " >>>>>");
        Network.StockMess m = new Network.StockMess();
        m.tip = Heading_type.PARAMETERS_PLAYER;
        m.p1 = p.id;
        m.p1 = MathUtils.random(60);
        m.p2 = p.death;
        m.p3 = p.command;
        m.p4 = p.hp;
        m.p6 = p.id;
        m.textM = p.getNikName();
    // System.out.println("!!!====***=====!!! NikName Bot  " + p.getNikName());
        this.addMasssage(fromidUser, m);
    }


    public void tellAboutShootBot(int x, int y, int alignShoot, int numberShoot, int idPlayer){
        Network.StockMess m = new Network.StockMess();

        m.tip = Heading_type.MY_SHOT;

        m.p1 = x;
        m.p2 = y;
        m.p3 = alignShoot;
        m.p4 = numberShoot;
        m.p5 = idPlayer;

        m.p6 = 111;

        createMessageDorEveryone(m);
        //this.outMassege.put(m.time_even, m);

    }

//    public void whatIsYourName(int p1) {
//        Network.StockMess m = new Network.StockMess();
//        m.tip = Heading_type.PARAMETERS_PLAYER;
//        m.p1 = -1;
//        this.addMasssage(p1, m);
//    }


    private void addMasssage(int fromPlayer, Network.StockMess m) {
        System.out.println("fromPlayer:: " + fromPlayer);
        OutMassege om = new OutMassege();
        int nom = m.time_even;
        while (outMassege.containsKey(nom)) nom++;
        m.time_even = nom;
        om.setParametors(m, nom, fromPlayer);
        om.actual = true;
        om.timeMomenIn = System.currentTimeMillis();
        this.outMassege.put(m.time_even, om);
    }

    public synchronized void sendingQueue(Server server) {
        Iterator<Map.Entry<Integer, OutMassege>> omass = outMassege.entrySet().iterator();
       // if(MathUtils.randomBoolean(.005f))System.out.println("outMassege.size :  "+outMassege.size());

        while (omass.hasNext()) {
            Map.Entry<Integer, OutMassege> om = omass.next();
            // System.out.println("  -"+om.getKey());
            if (om.getValue().actual) {
                checkByTime(om.getValue()); // проверка на время привышения 5 сеунды
                if(!om.getValue().actual) continue;
                om.getValue().sm.time_even = om.getKey();
              //  System.out.println(om.getValue().forPlayer);
                server.sendToUDP(om.getValue().forPlayer, om.getValue().sm);
             //   System.out.println("- " + om.getValue().sm.tip + " time_even " + om.getValue().sm.time_even + " nom  " + om.getValue().sm.nomer_pley + " forP : " + om.getValue().forPlayer+ "  "+ om.hashCode() + " ac " + om.getValue().actual + " Key" + om.getKey() );
                // System.out.println("--->>>  " + om.getValue().sm.time_even);
            } else {
                if (MathUtils.randomBoolean(.05f))
                    try {
                        outMassege.remove(om.getKey());
                    } catch (java.util.NoSuchElementException n) {
                        //   System.out.println("NoSuchElementException " + (om.getKey()));
                    }
            }

        }
    }

    private void sendВestRuction(OutMassege outMassege, int key) {
        if (outMassege.actual) return;
        if (MathUtils.randomBoolean(.5f)) this.outMassege.remove(key);
        if (this.outMassege.size() > 5000) this.outMassege.clear();

    }

    public void mark_the_sent(int nom) { // отметить как
        try {
            this.outMassege.get(nom).actual = false;
        } catch (NullPointerException e) {
          //  System.out.println("NPE :" + nom);
            e.printStackTrace();
        }

    }

    public void checkByTime(OutMassege outMassege) {
        boolean result = (System.currentTimeMillis() - outMassege.timeMomenIn) < 5_000;
        //System.out.println("---"+result);
       // System.out.println(Math.abs(System.currentTimeMillis() - outMassege.timeMomenIn) + " !!!!!!");
        outMassege.actual = result;
    }

    public void createMessageDorEveryone(int idExcept, Network.StockMess outMassege) { /// разослать сообщение всем кроме
        for (Integer key : lp.players.keySet()) {
            if (idExcept == key) continue;
            //         if(lp.players.get(key).status == Player.STATUS_DISCONECT) continue;
            addMasssage(key, outMassege);
            // System.out.println("ID = " + key + ", День недели = " +  lp.players.get(key));
        }


    }

    public void createMessageDorEveryone(Network.StockMess outMassege) { /// разослать сообщение
        for (Integer key : lp.players.keySet()) {
            if(lp.players.get(key).status == Player.STATUS_DISCONECT) continue;
            addMasssage(key, outMassege);
            //   System.out.print("-np ");
            //  System.out.println("ID = " + key + ", День недели = " +  lp.players.get(key));
        }
        ///sendingQueue(serverGame);
    }


}
