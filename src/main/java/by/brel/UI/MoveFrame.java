package by.brel.UI;

import by.brel.Entity.Bus;
import by.brel.Entity.Station;
import by.brel.Main;
import by.brel.Сonstants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveFrame extends JFrame implements Runnable {
    JPanel jPanel;
    JButton jButtonStart;
    JButton jButtonEnd;
    static int flag = 0;

    public MoveFrame(Constants constants) {
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
        setSize(1366,768);
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

        //Линия
        g.drawLine(100, 150, getContentPane().getWidth() - 100, 150);

        //Прямоугольник
//        g.drawRect(100, 150, getContentPane().getWidth() - 200, getContentPane().getHeight() / 2);

        int i = 0;

        //Рисуем станции
        for (Station station : Constants.STATIONS_COUNT_LIST) {
            i += 100;

            g.drawRect(station.getX() + i, 120, 30, 30);


            g.drawString(//Рисуем кол-во пассажиров на станции
                    Integer.toString(station.getCountPassengersInStation()),
                    station.getX() + i + (30 / 2) - 3,
                    118
            );
            g.drawString(//№ станции
                    Integer.toString(station.getNumberStation() + 1),
                    station.getX() + i + (30 / 2) - 3,
                    140
            );
        }

        int x = 100;
        for (Bus bus : Constants.BUS_COUNT_LIST) {
//            bus.setX(Constants.STATIONS_COUNT_LIST.get(j).getX());
//            g.drawRect((int) ((bus.getX() / x) + 102), 152, 20, 10);
            g.drawRect((int) (bus.getX() + x), 152, 30, 10);
        }


        //Кнопки
        if (flag == 1) {
            jButtonStart.setEnabled(false);
        }

        if (flag == 2) {
            jButtonEnd.setBackground(Color.decode("#red"));
        }

        if (Constants.livePassengers.get() == 0) {
            flag++;
        }
    }

    static class StartEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            flag++;

            Main.startAll();
        }
    }

    static class EndEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }


}
