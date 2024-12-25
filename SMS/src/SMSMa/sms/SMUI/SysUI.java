package SMSMa.sms.SMUI;

import SMSMa.sms.PeOb.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SysUI extends JFrame implements ActionListener {
    private JTextField loginNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    //静态集合储存用户对象信息
    private static ArrayList<User> allUser = new ArrayList<>();
    static{
        allUser.add(new User("山田凉","123456"));
    }

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

        loginNameField = new JTextField();
        loginNameField.setBounds(160, 100, 190, 30);
        loginNameField.setFont(customFont);
        panel.add(loginNameField);

        JLabel passwordLabel = new JLabel("密  码:");
        passwordLabel.setBounds(50, 150, 150, 30);
        passwordLabel.setFont(customFont);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 150, 190, 30);
        passwordField.setFont(customFont);
        passwordField.setEchoChar('*');//隐藏密码的符号
        panel.add(passwordField);

        loginButton = new JButton("登  录");
        loginButton.setBounds(50, 200, 150, 30);
        loginButton.setFont(customFont);
        loginButton.setBackground(primaryColor);
        loginButton.setForeground(Color.WHITE);
        panel.add(loginButton);
        loginButton.addActionListener(this);

        registerButton = new JButton("注  册");
        registerButton.setBounds(200, 200, 150, 30);
        registerButton.setFont(customFont);
        registerButton.setBackground(secondaryColor);
        registerButton.setForeground(Color.BLACK);
        panel.add(registerButton);
        registerButton.addActionListener(this);

        this.add(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton btn=(JButton) e.getSource();
        if (btn == loginButton) {
            login();
        }
        else {
            register();
        }
    }

    private void login(){
        String loinName = loginNameField.getText();
        String password = new String(passwordField.getPassword());
        //根据登录名称查找用户对象返回，查询到用户对象，说明登录名称正确。
        User user = findUser(loinName);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                //登录成功，跳转界面
                new INUI(user.getUsername());
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "密码错误，请重新输入！");
            }
        }else {
            JOptionPane.showMessageDialog(this, "用户名不存在，请重新输入！");
        }

    }
    //根据登录名称查找用户对象返回，查询到用户对象，说明登录名称正确,使用for循环根据索引遍历。
    private User findUser(String loginName){
        for (int i = 0; i < allUser.size(); i++) {
            User user = allUser.get(i);
            if (user.getUsername().equals(loginName)) {
                return user;
            }
        }
        return null;
    }

    private void register(){
        String loinName = loginNameField.getText();
        String password = new String(passwordField.getPassword());
        //根据登录名称查找用户对象返回，查询到用户对象，说明登录名称正确。
        User user = findUser(loinName);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "用户名已存在，请重新输入！");
        }else {
            allUser.add(new User(loinName,password));
            JOptionPane.showMessageDialog(this, "注册成功！");
        }
    }
}
