package by.brel.UI;

import by.brel.Entity.Station;
import by.brel.Main;
import by.brel.Сonstants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveFrame extends JFrame {
    Container container;
    JPanel panel;
    JButton button;


    public static void main(String[] args) {
//        new MoveFrame();
    }

    public MoveFrame(int passengers_count_max, int stations_count_max, int bus_count_max, int bus_capacity, int bus_movement_interval, int bus_speed, int bus_route_max) {
        initContainerMoveFrame(passengers_count_max);
        initMoveFrame();
    }

    private void initContainerMoveFrame(int passengers_count_max) {
        button = new JButton("Старт");
        button.addActionListener(new StartEventListener());

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawRect(100, 150, getContentPane().getWidth() - 200, getContentPane().getHeight() / 2);

                int i = 0;

                //Рисуем станции
                for (Station station : Constants.STATIONS_LIST) {
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
            }
        };

        panel.add(button);

        add(panel);

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

    static class StartEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Main.createBus();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
