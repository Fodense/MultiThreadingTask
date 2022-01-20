package by.brel.UI;

import by.brel.Entity.Bus;
import by.brel.Entity.Station;
import by.brel.Main;
import by.brel.Сonstants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class MoveFrame extends JFrame implements Runnable {
    JPanel jPanel;
    JButton jButtonStart;
    JButton jButtonEnd;
    static int flag = 0;
    int interval = Constants.magicNumber / 1000;

    public MoveFrame() {
        initContainerMoveFrame();
        initMoveFrame();
    }

    private void initContainerMoveFrame() {
        jButtonStart = new JButton("Старт");
        jButtonStart.addActionListener(new StartEventListener());

        jButtonEnd = new JButton("Выход");
        jButtonEnd.addActionListener(new EndEventListener());

        jPanel = new JPanel();

        jPanel.add(jButtonStart);
        jPanel.add(jButtonEnd);

        setContentPane(jPanel);
    }

    private void initMoveFrame() {
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Движуха");
        setSize(1366,380);
        setLocationRelativeTo(null);
//        setResizable(false);
        setVisible(true);
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(300);
                repaint();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (Constants.livePassengers.get() == 0) {
            g.setColor(Color.RED);
        }

        g.drawString("Кол-во живых пассажиров: " + Constants.livePassengers.get(), 15 , 50);

        g.setColor(Color.BLACK);

        //Линии
        g.drawLine(0, 150, 1350, 150);
        g.drawLine(0, 300, 1350, 300);

        //Рисуем станции первой линии
        for (Station station : Constants.STATIONS_COUNT_LIST_FIRST_LINE) {

            g.drawRect(
                    (station.getX() / interval) + 50,
                    120,
                    30,
                    30);

            //Рисуем кол-во пассажиров на станции
            g.drawString(
                    Integer.toString(station.getCountPassengersInStation()),
                    (station.getX() / interval) + 61,
                    118
            );

            //№ станции
            g.drawString(
                    Integer.toString(station.getNumberStation()),
                    (station.getX() / interval) + 61,
                    140
            );
        }

        //Рисуем станции второй линии
        for (Station station : Constants.STATIONS_COUNT_LIST_LAST_LINE) {
            g.drawRect(
                    (Constants.magicNumber - station.getX()) / interval + 50,
                    270,
                    30,
                    30
            );

            //Рисуем кол-во пассажиров на станции
            g.drawString(
                    Integer.toString(station.getCountPassengersInStation()),
                    (Constants.magicNumber - station.getX()) / interval + 61,
                    268
            );

            //№ станции
            g.drawString(
                    Integer.toString(station.getNumberStation()),
                    (Constants.magicNumber - station.getX()) / interval + 61,
                    290
            );
        }

        //Рисуем автобусы
        for (Bus bus : Constants.BUS_COUNT_LIST) {
            if (bus.getRoute() == 0) {
                g.drawRect(
                        (int) (bus.getX() / interval) + 50,
                        152,
                        30,
                        20
                );

                //Их номера | кол-во пассажиров внутри
                g.drawString(
                        "" + bus.getName() + "|" + bus.getCountPassenger(),
                        (int) (bus.getX() / interval) + 55,
                        165);

            } else if (bus.getRoute() == 1){
                g.drawRect(
                        (Constants.magicNumber - (int)bus.getX()) / interval + 50,
                        302,
                        30,
                        20
                );

                //Их номера | кол-во пассажиров внутри
                g.drawString(
                        "" + bus.getName() + "|" + bus.getCountPassenger(),
                        (int) (Constants.magicNumber - bus.getX()) / interval + 55,
                        315);
            }
        }

        //Кнопки
        if (flag == 1) {
            jButtonStart.setEnabled(false);
        }

        if (flag == 2) {
            jButtonEnd.setBackground(Color.RED);
        }

        if (Constants.livePassengers.get() == 0) {
            flag++;
        }
    }

    static class StartEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            flag++;

            Main.startBus();
        }
    }

    static class EndEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }


}
