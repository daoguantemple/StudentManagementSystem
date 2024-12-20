package sms;

import javax.swing.*;
import java.awt.*;

public class SysUI extends JFrame{
    private JTextField usernameField;


    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public SysUI(){
        super("登录界面");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);

        createAndShowGUI();
    }

    private void createAndShowGUI(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        Font customFont = new Font("楷体", Font.BOLD, 18);
        Color primaryColor = new Color(66, 135, 245);
        Color secondaryColor = new Color(204, 204, 204);

        JLabel titleLabel = new JLabel("学生管理系统");
        titleLabel.setBounds(50, 30, 300, 30);
        titleLabel.setFont(customFont);
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setBounds(50, 100, 150, 30);
        usernameLabel.setFont(customFont);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 100, 190, 30);
        usernameField.setFont(customFont);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("密  码:");
        passwordLabel.setBounds(50, 150, 150, 30);
        passwordLabel.setFont(customFont);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 150, 190, 30);
        passwordField.setFont(customFont);
        panel.add(passwordField);

        loginButton = new JButton("登  录");
        loginButton.setBounds(50, 200, 150, 30);
        loginButton.setFont(customFont);
        loginButton.setBackground(primaryColor);
        loginButton.setForeground(Color.WHITE);
        panel.add(loginButton);

        registerButton = new JButton("注  册");
        registerButton.setBounds(200, 200, 150, 30);
        registerButton.setFont(customFont);
        registerButton.setBackground(secondaryColor);
        registerButton.setForeground(Color.BLACK);
        panel.add(registerButton);

        this.add(panel);
        this.setVisible(true);
    }
}
