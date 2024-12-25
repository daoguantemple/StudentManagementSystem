package SMSMa.sms.SMUI;

import SMSMa.sms.PeOb.Stu;

import javax.swing.*;
import java.awt.*;

public class AddStudentUI extends JFrame {

    private JTextField txtName,txtSex,txtAge,txtClass,txtNo;
    private JFormattedTextField txtHireDate;
    private JButton btnAdd,btnCancel;
    private INUI inui;

    public AddStudentUI(INUI inui){
        //"姓名", "性别", "年龄", "班级", "学号"
        super("添加");
        this.inui = inui;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        Font labelFont = new Font("楷体", Font.PLAIN, 14);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelName = new JLabel("姓名:");
        labelName.setFont(labelFont);
        add(labelName, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        txtName = new JTextField(10);
        add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel labelSex = new JLabel("性别:");
        labelSex.setFont(labelFont);
        add(labelSex, gbc);

        gbc.gridx = 1;
        txtSex = new JTextField(10);
        add(txtSex, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel labelAge = new JLabel("年龄:");
        labelAge.setFont(labelFont);
        add(labelAge, gbc);

        gbc.gridx = 1;
        txtAge = new JTextField(10);
        add(txtAge, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel labelClass = new JLabel("班级:");
        labelClass.setFont(labelFont);
        add(labelClass, gbc);

        gbc.gridx = 1;
        txtClass = new JTextField(10);
        add(txtClass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel labelNo = new JLabel("学号:");
        labelNo.setFont(labelFont);
        add(labelNo, gbc);

        gbc.gridx = 1;
        txtNo = new JTextField(10);
        add(txtNo, gbc);

        //添加和取消按钮
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill=GridBagConstraints.NONE;
        btnAdd = new JButton("添加");
        btnCancel = new JButton("取消");
        btnAdd.setPreferredSize(new Dimension(100, 30));
        btnCancel.setPreferredSize(new Dimension(100, 30));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        add(buttonPanel, gbc);

        btnAdd.addActionListener(e -> {
            //获取数据，封装成员对象，添加信息展示
            Stu stu = new Stu();
            stu.setName(txtName.getText());
            stu.setSex(txtSex.getText());
            stu.setAge(Integer.parseInt(txtAge.getText()));
            stu.setCls(txtClass.getText());
            stu.setId(txtNo.getText());
            inui.addStu(stu);
            JOptionPane.showMessageDialog(this, "添加成功！");
            this.dispose();
        });

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


}
