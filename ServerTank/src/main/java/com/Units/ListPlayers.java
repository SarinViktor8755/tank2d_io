package main.java.com.Units;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import main.java.com.GameServer;
import main.java.com.Network;

public class ListPlayers {
    ConcurrentHashMap<Integer, Player> players;
    GameServer gameServer;

    ConcurrentSkipListSet<Integer> incomingMessageLog;


    public ListPlayers(GameServer gameServer) {
        this.players = new ConcurrentHashMap<>();
        this.gameServer = gameServer;
        incomingMessageLog = new ConcurrentSkipListSet<Integer>();
    }

    public Player getPlayerForId(int id) { // почему то вызывается  иногда
        Player result = players.get(id);
        if (result == null) players.put(id, new Player(id));
        return players.get(id);
    }

    public void clearList() {
        this.players.clear();
    }

    public void addPlayer(int con) {
        this.players.put(con, new Player(con));
    }

    public void routerMassege(Connection connection, Network.StockMess obj) {
        //System.out.println("--> " + obj.time_even);
        if (obj.tip.equals(Heading_type.MY_SHOT)) {
            obj.p5 = connection.getID();


            Vector2 velBullet = new Vector2(700, 0).setAngleDeg(obj.p3);
            gameServer.getMainGame().getBullets().addBullet(new Vector2(obj.p1, obj.p2), velBullet, obj.p4);
            System.out.println("MY_SHOT :: " + obj.time_even);
            //  System.out.println(obj);
            gameServer.outMassegeCollection.createMessageDorEveryone(obj);
            gameServer.getLp().getPlayerForId(connection.getID()).setNikName(obj.textM);
            return;
        }

        if (obj.tip.equals(Heading_type.BUTTON_STARTGAME)) {
            //  System.out.println("BUTTON_STARTGAME");
            // System.out.println(obj.p1);
            players.get(connection.getID()).setStatus(Player.STATUS_IN_GAME);
            players.get(connection.getID()).setNikName(obj.textM);
            return;
        }

        if (obj.tip.equals(Heading_type.MY_NIK)) {
            System.out.println("MY_NIK");
            players.get(connection.getID()).setNikName(obj.textM);
            // System.out.println(players.get(connection.getID()).getNikName());
            return;
        }

        if (obj.tip.equals(Heading_type.MY_TOKKEN)) {
            System.out.println("MY_TOKKEN");
            players.get(connection.getID()).setTokken(obj.textM);
            //System.out.println(players.get(connection.getID()).getTokken());
            return;

        }
        if (obj.tip.equals(Heading_type.PARAMETERS_PLAYER)) {
            //  System.out.println("PARAMETERS_PLAYER");
            gameServer.getLp().getPlayerForId(connection.getID()).setNikName(obj.textM);
            if (obj.p1 == null) return;
            //   System.out.println(obj.p1 + " :::!!!!!!!!!!!!!!!!!!!!!!!!!!!");


//            if (obj.p1 > 0) {
//                System.out.println("!!! PARAMETERS_PLAYER");
//                if (gameServer.getLp().getPlayerForId(obj.p1).getNikName() == null) return;
//                gameServer.getOutMassegeCollection().tellParamForPlayer(connection.getID(), gameServer.getLp().getPlayerForId(obj.p1));
//
//            } else {
//                System.out.println("!!! PARAMETERS_BOT");
//                //   gameServer.getMainGame().getBot().getBotList().get(obj.p1);
//               // System.out.println("!!!=========!!! NikName Bot  " + gameServer.getMainGame().getBot().getBotList().get(obj.p1).getNikName());
  //              gameServer.getOutMassegeCollection().tellParamForPlayer(connection.getID(), gameServer.getMainGame().getBot().getBotList().get(obj.p1));
//                System.out.println("send_param");
//            }

            Collection<Player> playersLive = gameServer.getLp().getPlayers().values();
            Iterator<Player> iter = playersLive.iterator();
            while(iter.hasNext()){
                gameServer.getOutMassegeCollection().tellParamForPlayer(connection.getID(),iter.next());
            }

            Collection<Player> playersBot = gameServer.getMainGame().getBot().getBotList().values();
            Iterator<Player> iterBot = playersBot.iterator();
            while(iter.hasNext()){
                gameServer.getOutMassegeCollection().tellParamForPlayer(connection.getID(),iterBot.next());
            }


            return;
        }

        if (obj.tip.equals(Heading_type.MY_PARAMETERS)) {
            System.out.println("!!MY_PARAMETERS " + obj.textM);
            gameServer.getLp().getPlayerForId(connection.getID()).hp = obj.p1;
            gameServer.getLp().getPlayerForId(connection.getID()).setNikName(obj.textM);
            return;

        }


    }

    public ConcurrentHashMap<Integer, Player> getPlayers() {
        return players;
    }

    public void sendMessageToEveryone(Object mess) { // разослать сообщение всем


//        try {
//            for (int key : playrsLisn.keySet()) {
//                if (!getPlaeyrToId(key).isLive()) continue;// проверка на жизнь
//                float distant = StaticService.getDistance(this.getPlaeyrToId(id).getX(), this.getPlaeyrToId(id).getY(), this.getPlaeyrToId(key).getX(), this.getPlaeyrToId(key).getY());
//                if (!decisionDistance(distant)) continue;
//                if (id == key) continue; // Самому себе отправлять только 4% коорденат
//                if (isRoomOne(id, key)) // проверка на одну комнату или нет ))
//                    //        System.out.println(key);
//                    gs.server.sendToUDP(id, packeCcoordinatesPlayer(key));
//            }
//        } catch (ConcurrentModificationException e) {
//            // e.printStackTrace();
//        }

    }

    public void sendMessageToEveryone(Object mess, int aftorID) { // разослать сообщение всем
        for (int key : players.keySet()) {
            if (aftorID == players.get(key).getId()) continue;

        }

//        try {
//            for (int key : playrsLisn.keySet()) {
//                if (!getPlaeyrToId(key).isLive()) continue;// проверка на жизнь
//                float distant = StaticService.getDistance(this.getPlaeyrToId(id).getX(), this.getPlaeyrToId(id).getY(), this.getPlaeyrToId(key).getX(), this.getPlaeyrToId(key).getY());
//                if (!decisionDistance(distant)) continue;
//                if (id == key) continue; // Самому себе отправлять только 4% коорденат
//                if (isRoomOne(id, key)) // проверка на одну комнату или нет ))
//                    //        System.out.println(key);
//                    gs.server.sendToUDP(id, packeCcoordinatesPlayer(key));
//            }
//        } catch (ConcurrentModificationException e) {
//            // e.printStackTrace();
//        }
    }

    public int getSize() {
        return this.players.size();
    }


}
