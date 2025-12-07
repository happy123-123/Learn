package com.happy.basicLearn.Collection.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;

public class PokerGame {
    static ArrayList<Integer> poker = new ArrayList<>();
    static HashMap<Integer, String> pokerMap = new HashMap<>();

    static {
        String[] colors = {"♦", "♣", "♥","♠"};
        String[] numbers = { "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K","A", "2"};

        int num=1;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < colors.length; j++) {
                pokerMap.put(num,colors[j]+numbers[i]);
                poker.add(num);
                num++;
            }
        }
        pokerMap.put(num,"小鬼");
        poker.add(num);
        pokerMap.put(++num,"大鬼");
        poker.add(num);
    }
    public PokerGame() {
        //创建玩家
        TreeSet<Integer> onTable = new TreeSet<>();
        TreeSet<Integer> player01 = new TreeSet<>();
        TreeSet<Integer> player02 = new TreeSet<>();
        TreeSet<Integer> player03 = new TreeSet<>();

        //洗牌
        Collections.shuffle(poker);

        //发牌
        for (int i = 0; i < poker.size(); i++) {
            if (i <= 2) {
                onTable.add(poker.get(i));
            } else if (i <= 19) {
                player01.add(poker.get(i));
            } else if (i <= 36) {
                player02.add(poker.get(i));
            } else {
                player03.add(poker.get(i));
            }
        }

        //看牌
        looker("底牌",onTable);
        looker("玩家01",player01);
        looker("玩家02",player02);
        looker("玩家03",player03);
    }

    public void looker(String name,TreeSet<Integer> ts) {
        System.out.print(name + ": ");
        for (Integer i : ts) {
            System.out.print(pokerMap.get(i) + " ");
        }
        System.out.println();
    }
}
