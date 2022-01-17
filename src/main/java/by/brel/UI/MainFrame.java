package by.brel.UI;

import by.brel.Main;
import by.brel.Сonstants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public static Constants constants;

    private JPanel panel;

    private static JLabel jLabel1;
    private static JTextField passengerCountMax;
    private static JLabel jLabel2;
    private static JTextField stationsCountMax;
    private static JLabel jLabel3;
    private static JTextField busCountMax;
    private static JLabel jLabel4;
    private static JTextField busCapacity;
    private static JLabel jLabel5;
    private static JTextField busInterval;
    private static JLabel jLabel6;
    private static JTextField busSpeed;

    public static void main(String[] args) {
        new MainFrame();
    }

    public MainFrame() {
        initContainer();
        initFrame();
    }

    private void initContainer() {
        Container container = getContentPane();
        container.setLayout(new GridLayout(0, 2));

        initStartingParameters();

        container.add(jLabel1);
        container.add(passengerCountMax);
        container.add(jLabel2);
        container.add(stationsCountMax);
        container.add(jLabel3);
        container.add(busCountMax);
        container.add(jLabel4);
        container.add(busCapacity);
        container.add(jLabel5);
        container.add(busInterval);
        container.add(jLabel6);
        container.add(busSpeed);

        JButton button = new JButton("Создать мир");
        button.addActionListener(new CreateWorldEventListener());
        container.add(button).setSize(500, 0);

        setContentPane(container);
    }

    private void initStartingParameters() {
        jLabel1 = new JLabel("Общее кол-во пассажиров", SwingConstants.LEFT);
        passengerCountMax = new JTextField(Integer.toString(Constants.PASSENGERS_COUNT_MAX),1);
        jLabel2 = new JLabel("Кол-во остановок", SwingConstants.LEFT);
        stationsCountMax = new JTextField(Integer.toString(Constants.STATIONS_COUNT_MAX), 1);
        jLabel3 = new JLabel("Количество автобусов", SwingConstants.LEFT);
        busCountMax = new JTextField(Integer.toString(Constants.BUS_COUNT_MAX), 1);
        jLabel4 = new JLabel("Вместимость каждого автобуса", SwingConstants.LEFT);
        busCapacity = new JTextField(Integer.toString(Constants.BUS_CAPACITY), 1);
        jLabel5 = new JLabel("Интервал между автобусами", SwingConstants.LEFT);
        busInterval = new JTextField(Integer.toString(Constants.BUS_MOVEMENT_INTERVAL), 1);
        jLabel6 = new JLabel("Скорость движения", SwingConstants.LEFT);
        busSpeed = new JTextField(Double.toString(Constants.BUS_SPEED), 1);
    }

    private void initFrame() {
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Общественный транспорт");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    static class CreateWorldEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            initParameters();

            try {
                Main.init();
                Main.startPassenger();

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            Thread thread = new Thread(
                    new MoveFrame()
            );

            thread.start();
        }
    }

    private static void initParameters(){
        constants.PASSENGERS_COUNT_MAX      = Integer.parseInt(passengerCountMax.getText());
        constants.STATIONS_COUNT_MAX        = Integer.parseInt(stationsCountMax.getText());
        constants.BUS_COUNT_MAX             = Integer.parseInt(busCountMax.getText());
        constants.BUS_CAPACITY              = Integer.parseInt(busCapacity.getText());
        constants.BUS_MOVEMENT_INTERVAL     = Integer.parseInt(busInterval.getText());
        constants.BUS_SPEED                 = Double.parseDouble(busSpeed.getText());
    }
}
