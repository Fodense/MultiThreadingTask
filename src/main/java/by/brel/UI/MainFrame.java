package by.brel.UI;

import by.brel.Main;
import by.brel.Сonstants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JPanel panel;

    private static final JLabel jLabel1 = new JLabel("Общее кол-во пассажиров", SwingConstants.LEFT);
    private static final JTextField passengerCountMax = new JTextField(Integer.toString(Constants.PASSENGERS_COUNT_MAX),1);
    private static final JLabel jLabel2 = new JLabel("Кол-во остановок", SwingConstants.LEFT);
    private static final JTextField stationsCountMax = new JTextField(Integer.toString(Constants.STATIONS_COUNT_MAX), 1);
    private static final JLabel jLabel3 = new JLabel("Количество автобусов", SwingConstants.LEFT);
    private static final JTextField busCountMax = new JTextField(Integer.toString(Constants.BUS_COUNT_MAX), 1);
    private static final JLabel jLabel4 = new JLabel("Вместимость каждого автобуса", SwingConstants.LEFT);
    private static final JTextField busCapacity = new JTextField(Integer.toString(Constants.BUS_CAPACITY), 1);
    private static final JLabel jLabel5 = new JLabel("Интервал между автобусами", SwingConstants.LEFT);
    private static final JTextField busInterval = new JTextField(Integer.toString(Constants.BUS_MOVEMENT_INTERVAL), 1);
    private static final JLabel jLabel6 = new JLabel("Скорость движения", SwingConstants.LEFT);
    private static final JTextField busSpeed = new JTextField(Integer.toString(Constants.BUS_SPEED), 1);

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
            int PASSENGERS_COUNT_MAX = 0;
            int STATIONS_COUNT_MAX = 0;
            int BUS_COUNT_MAX = 0;
            int BUS_CAPACITY = 0;
            int BUS_MOVEMENT_INTERVAL = 0;
            int BUS_SPEED = 0;
            int BUS_ROUTE_MAX = 1;
            
            try {
            PASSENGERS_COUNT_MAX = Integer.parseInt(passengerCountMax.getText());
            STATIONS_COUNT_MAX = Integer.parseInt(stationsCountMax.getText());
            BUS_COUNT_MAX = Integer.parseInt(busCountMax.getText());
            BUS_CAPACITY = Integer.parseInt(busCapacity.getText());
            BUS_MOVEMENT_INTERVAL = Integer.parseInt(busInterval.getText());
            BUS_SPEED = Integer.parseInt(busSpeed.getText());

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null,"Некорректный ввод!");
            }

            Main.createStation();
            Main.createPassenger();

            new MoveFrame(
                     PASSENGERS_COUNT_MAX,
                     STATIONS_COUNT_MAX,
                     BUS_COUNT_MAX,
                     BUS_CAPACITY,
                     BUS_MOVEMENT_INTERVAL,
                     BUS_SPEED,
                     BUS_ROUTE_MAX
             );
        }
    }
}
