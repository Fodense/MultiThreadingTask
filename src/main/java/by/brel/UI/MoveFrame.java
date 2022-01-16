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
    JButton jButton;

    public MoveFrame(Constants constants) {
        initContainerMoveFrame();
        initMoveFrame();
    }

    private void initContainerMoveFrame() {
        jButton = new JButton("Старт");
        jButton.addActionListener(new StartEventListener());

        jPanel = new JPanel();

        jPanel.add(jButton);

        setContentPane(jPanel);
    }

    private void initMoveFrame() {
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Движуха");
        setSize(1366,768);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(200);
                repaint();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics g){
        super.paint(g);

        g.drawRect(100, 150, getContentPane().getWidth() - 200, getContentPane().getHeight() / 2);

        int i = 0;

        //Рисуем станции
        for (Station station : Constants.STATIONS_COUNT_LIST) {
            i += 150;
            g.drawRect(station.getX() + i, 120, 30, 30);
            g.drawString(
                    Integer.toString(station.getCountPassengersInStation()),
                    station.getX() + i + (30 / 2) - 3,
                    118
            ); //Count Passengers in stations
            g.drawString(
                    Integer.toString(station.getNumberStation()),
                    station.getX() + i + (30 / 2) - 3,
                    140
            ); //№
        }

        int x = 100;
        for (Bus bus : Constants.BUS_COUNT_LIST) {
            g.drawRect((int) (bus.getX() / x) + 102, 152, 20, 10);
        }
    }

    static class StartEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.startAll();
        }
    }


}
