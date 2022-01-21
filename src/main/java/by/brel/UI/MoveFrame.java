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
import java.util.Objects;

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

        //Верхняя дорога
        g.drawLine(0, 150, 1350, 150);
        g.drawLine(0, 180, 1350, 180);

        //Нижняя дорога
        g.drawLine(0, 300, 1350, 300);
        g.drawLine(0, 330, 1350, 330);

        //Рисуем станции первой линии
        for (Station station : Constants.STATIONS_COUNT_LIST_FIRST_LINE) {
            g.drawImage(
                    getImage("station"),
                    (station.getX() / interval) + 50,
                    120,
                    50,
                    30,
                    this
            );

            //Рисуем человечка
            g.drawImage(
                    getImage("passenger"),
                    (station.getX() / interval) + 52,
                    103,
                    15,
                    15,
                    this
            );

            //Рисуем кол-во пассажиров на станции
            g.drawString(
                    Integer.toString(station.getCountPassengersInStation()),
                    (station.getX() / interval) + 70,
                    118
            );

            //№ станции
            g.drawString(
                    Integer.toString(station.getNumberStation()),
                    (station.getX() / interval) + 94,
                    140
            );
        }

        //Рисуем станции второй линии
        for (Station station : Constants.STATIONS_COUNT_LIST_LAST_LINE) {
            g.drawImage(
                    getImage("station"),
                    (Constants.magicNumber - station.getX()) / interval + 50,
                    270,
                    50,
                    30,
                    this
            );

            //Рисуем человечка
            g.drawImage(
                    getImage("passenger"),
                    (Constants.magicNumber - station.getX()) / interval + 52,
                    253,
                    15,
                    15,
                    this
            );

            //Рисуем кол-во пассажиров на станции
            g.drawString(
                    Integer.toString(station.getCountPassengersInStation()),
                    (Constants.magicNumber - station.getX()) / interval + 70,
                    268
            );

            //№ станции
            g.drawString(
                    Integer.toString(station.getNumberStation()),
                    (Constants.magicNumber - station.getX()) / interval + 94,
                    290
            );
        }

        //Рисуем автобусы
        for (Bus bus : Constants.BUS_COUNT_LIST) {
            if (bus.getRoute() == 0) {
                g.drawImage(
                        getImage("bus"),
                        (int) (bus.getX() / interval) + 50,
                        153,
                        40,
                        25,
                        this
                );

                //Их номера | кол-во пассажиров внутри
                g.drawString(
                        "" + bus.getName() + "|" + bus.getCountPassenger(),
                        (int) (bus.getX() / interval) + 60,
                        193
                );

            } else if (bus.getRoute() == 1){
                g.drawImage(
                        getImage("bus"),
                        (Constants.magicNumber - (int)bus.getX()) / interval + 50,
                        303,
                        40,
                        25,
                        this
                );

                //Их номера | кол-во пассажиров внутри
                g.drawString(
                        "" + bus.getName() + "|" + bus.getCountPassenger(),
                        (int) (Constants.magicNumber - bus.getX()) / interval + 60,
                        343
                );
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

    private Image getImage(String name) {
        ImageIcon imageIcon = new ImageIcon("src/main/resources/img/" + name + ".png");

        return imageIcon.getImage();
    }
}
