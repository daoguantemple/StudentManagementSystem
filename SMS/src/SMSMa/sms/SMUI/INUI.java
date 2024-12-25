package SMSMa.sms.SMUI;

import SMSMa.sms.PeOb.Stu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class INUI extends JFrame
{
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameTextFieldSearch;

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
        nameTextFieldSearch = new JTextField(20);
        JButton btnSearch = new JButton("搜索");
        JButton btnAdd = new JButton("添加");

        topPanel.add(nameTextFieldSearch);
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
                JOptionPane.showMessageDialog(frame, "修改ID：" + id);
                upStudents(id);
            }
        });
        //删
        deleteItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();//拿到行
            if (selectedRow >= 0) {
                String id = (String) model.getValueAt(selectedRow, 0);//取第0列
                deleteStudents(id);
                model.removeRow(selectedRow);
            }
        });
        //搜索按钮监听器逻辑**
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextFieldSearch.getText();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "请输入姓名");
                    return;
                }
                for (int i = 0; i < model.getRowCount(); i++) {
                    String rowName = (String) model.getValueAt(i, 1);
                    if (rowName.equals(name)) {
                        table.setRowSelectionInterval(i, i);
                        return;
                    }
                }
            }
        });

        //添加按钮监听器逻辑**
        btnAdd.addActionListener(e -> {
            new AddStudentUI(this);
        });

        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

    }

    private void upStudents(String id) {
        for (int i = 0; i < students.size(); i++) {
            Stu stu = students.get(i);
            if (stu.getId() == id) {
                //修改id所对应的行里面的信息
                //未完成
                System.out.println("修改成功");
                saveStudents(students);
                break;
            }
        }
    }

    private void deleteStudents(String id) {
        for (int i = 0; i < students.size(); i++) {
            Stu stu = students.get(i);
            if (stu.getId() == id) {
                students.remove(i);
                saveStudents(students);
                break;
            }
        }

    }

    public void addStu(Stu stu) {
        students.add(stu);
        model.addRow(new Object[]{stu.getId(), stu.getName(), stu.getSex(), stu.getAge(), stu.getCls()});
        saveStudents(students);
        System.out.println("添加成功");
    }

    public void saveStudents(ArrayList<Stu> students) {//保存学生信息
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            oos.writeObject(students);
        } catch (Exception e) {
            e.printStackTrace();
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













