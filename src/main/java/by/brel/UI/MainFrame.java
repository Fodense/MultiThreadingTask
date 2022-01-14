package by.brel.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private static final JLabel jLabel1 = new JLabel("Общее кол-во пассажиров", SwingConstants.LEFT);
    private static final JTextField jTextField1 = new JTextField("2",1);
    private static final JLabel jLabel2 = new JLabel("Кол-во остановок", SwingConstants.LEFT);
    private static final JTextField jTextField2 = new JTextField("2", 1);
    private static final JLabel jLabel3 = new JLabel("Количество автобусов", SwingConstants.LEFT);
    private static final JTextField jTextField3 = new JTextField("2", 1);
    private static final JLabel jLabel4 = new JLabel("Вместимость каждого автобуса", SwingConstants.LEFT);
    private static final JTextField jTextField4 = new JTextField("1", 1);
    private static final JLabel jLabel5 = new JLabel("Интервал между автобусами", SwingConstants.LEFT);
    private static final JTextField jTextField5 = new JTextField("3000", 1);
    private static final JLabel jLabel6 = new JLabel("Скорость движения", SwingConstants.LEFT);
    private static final JTextField jTextField6 = new JTextField("1000", 1);

    public MainFrame() {
        this.setTitle("Общественный транспорт");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);

        Container container = getContentPane();
        container.setLayout(new GridLayout(0, 2));

        container.add(jLabel1);
        container.add(jTextField1);
        container.add(jLabel2);
        container.add(jTextField2);
        container.add(jLabel3);
        container.add(jTextField3);
        container.add(jLabel4);
        container.add(jTextField4);
        container.add(jLabel5);
        container.add(jTextField5);
        container.add(jLabel6);
        container.add(jTextField6);

        JButton button = new JButton("Создать мир");
        button.addActionListener(new StartEventListener());
        container.add(button).setSize(500, 0);

        setContentPane(container);
    }

    public MainFrame(int name, int maxCapacityBus, int countPassenger, int travelSpeed, int route, boolean flag2, boolean direction) {

    }

    public MainFrame(int passengers_count_max, int stations_count_max, int bus_count_max, int bus_capacity, int bus_movement_interval, int bus_speed, int route) {
        this.setTitle("Движуха");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(510, 150, 800, 800);
        this.setVisible(true);

        Container container = getContentPane();
        container.setLayout(new GridLayout());
//        JButton button = new JButton("Старт");
//        button.addActionListener(new StartEventListener());
//        container.add(button);


        Graphics graphics = new DebugGraphics();
        paint(graphics);



        setContentPane(container);
    }

    public void paint(Graphics g){
        g.drawOval(50,75, getContentPane().getWidth() - 100, getContentPane().getHeight() - 100);
        g.setColor(Color.RED);
    }

    static class StartEventListener implements ActionListener {

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
                PASSENGERS_COUNT_MAX = Integer.parseInt(jTextField1.getText());
                STATIONS_COUNT_MAX = Integer.parseInt(jTextField2.getText());
                BUS_COUNT_MAX = Integer.parseInt(jTextField3.getText());
                BUS_CAPACITY = Integer.parseInt(jTextField4.getText());
                BUS_MOVEMENT_INTERVAL = Integer.parseInt(jTextField5.getText());
                BUS_SPEED = Integer.parseInt(jTextField6.getText());

             } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null,"Некорректный ввод!");
             }

             new MainFrame(PASSENGERS_COUNT_MAX, STATIONS_COUNT_MAX, BUS_COUNT_MAX, BUS_CAPACITY, BUS_MOVEMENT_INTERVAL, BUS_SPEED, BUS_ROUTE_MAX);
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
