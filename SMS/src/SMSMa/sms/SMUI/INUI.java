package SMSMa.sms.SMUI;

import SMSMa.sms.PeOb.Stu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class INUI extends JFrame
{
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField IDTextFieldSearch;

    private ArrayList<Stu> students = new ArrayList<>();

    public INUI() {
    }

    public INUI(String username) {
        super("用户 " + username);
        frame = this;
        initialize();
        loadStudents();
        this.setVisible(true);
    }
    private void initialize()
    {
        this.setBounds(100, 100, 800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        IDTextFieldSearch = new JTextField(20);
        JButton btnSearch = new JButton("搜索");
        JButton btnAdd = new JButton("添加");

        topPanel.add(IDTextFieldSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnAdd);

        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"学号","姓名", "性别", "年龄", "班级"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowHeight(30);

        //右键菜单
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem updateItem = new JMenuItem("修改");
        JMenuItem deleteItem = new JMenuItem("删除");
        popupMenu.add(updateItem);
        popupMenu.add(deleteItem);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        updateItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String id = (String) model.getValueAt(selectedRow, 0);
                try {
                    upStudents(id);
                } catch (RTEGender ex) {
                    JOptionPane.showMessageDialog(frame, "性别输入错误");
                    throw new RuntimeException(ex);
                } catch (RTEAge ex) {
                    JOptionPane.showMessageDialog(frame, "年龄输入错误");
                    throw new RuntimeException(ex);
                }
                updateTable();
            }
        });

        deleteItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();//拿到行
            if (selectedRow >= 0) {
                String id = (String) model.getValueAt(selectedRow, 0);
                deleteStudents(id);
                model.removeRow(selectedRow);
            }
        });

        btnSearch.addActionListener(e -> {
            String ID = IDTextFieldSearch.getText();
            if (ID.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "请输入ID");
                return;
            }
            for (int i = 0; i < model.getRowCount(); i++) {
                String rowID = (String) model.getValueAt(i, 0);
                if (rowID.equals(ID)) {
                    table.setRowSelectionInterval(i, i);
                    return;
                }
            }
        });

        btnAdd.addActionListener(e -> new AddStudentUI(this));

        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

    }

    private void upStudents(String id) throws RTEGender, RTEAge {
        for (int i = 0; i < students.size(); i++) {
            Stu stu = students.get(i);
            if (id.equals(stu.getId())) {
                JOptionPane.showMessageDialog(frame, "修改ID：" + id);
                stu.setName(JOptionPane.showInputDialog(frame, "请输入姓名"));
                stu.setSex(JOptionPane.showInputDialog(frame, "请输入性别"));
                stu.setAge(Integer.parseInt(JOptionPane.showInputDialog(frame, "请输入年龄")));
                stu.setCls(JOptionPane.showInputDialog(frame, "请输入班级"));
                model.setValueAt(stu.getName(), i, 1);
                model.setValueAt(stu.getSex(), i, 2);
                model.setValueAt(stu.getAge(), i, 3);
                model.setValueAt(stu.getCls(), i, 4);
                model.setValueAt(stu.getId(), i, 0);
                JOptionPane.showMessageDialog(frame, "ID"+id+"修改成功！");
                saveStudents(students);
                break;
            }
        }
    }

    private void deleteStudents(String id) {
        for (int i = 0; i < students.size(); i++) {
            Stu stu = students.get(i);
            if (id.equals(stu.getId())) {
                students.remove(i);
                saveStudents(students);
                break;
            }
        }
    }

    public void addStu(Stu stu) throws IOException{
        students.add(stu);
        model.addRow(new Object[]{stu.getId(), stu.getName(), stu.getSex(), stu.getAge(), stu.getCls()});
        saveStudents(students);
        System.out.println("添加成功");
    }

    public void saveStudents(ArrayList<Stu> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            oos.writeObject(students);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "保存失败");
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("students.csv"))) {
            for (Stu stu : students) {
                String line = stu.getId() + "," + stu.getName() + "," + stu.getSex() + "," + stu.getAge() + "," + stu.getCls();
                try (BufferedWriter bw2 = new BufferedWriter(new FileWriter("students.csv", true))) {
                    bw2.write(line);
                    bw2.newLine();
                }
            }
            bw.flush();
        } catch (IOException e) {
            System.out.println("保存失败");
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Stu> loadStudents() {
        ArrayList<Stu> loadedStudents = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            loadedStudents = (ArrayList<Stu>) ois.readObject();
            students = loadedStudents; // 将加载的数据存储到 students 变量
            updateTable(); // 更新表格显示
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "加载失败");
            e.printStackTrace();
        }
        return loadedStudents;
    }

    public void updateTable() {
        model.setRowCount(0);
        for (Stu stu : students) {
            model.addRow(new Object[]{stu.getId(), stu.getName(), stu.getSex(), stu.getAge(), stu.getCls()});
        }
    }

}













