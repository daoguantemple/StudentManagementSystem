package SMSMa.sms.PeOb;

import SMSMa.sms.SMUI.RTEAge;
import SMSMa.sms.SMUI.RTEGender;

import java.io.Serializable;

public class Stu implements Serializable {
    //"姓名""性别""年龄""班级""学号"
    private String name;
    private String sex;
    private int age;
    private String cls;
    private String id;

    public Stu(){};
    public Stu(String name,String sex,int age,String cls,String id)
    {
        this.name=name;
        this.sex=sex;
        this.age=age;
        this.cls=cls;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) throws RTEGender {
        if(sex.equals("男")||sex.equals("女"))
        {
            this.sex = sex;
        }
        else throw new RTEGender("性别输入错误！");

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws RTEAge {
        if(age<0||age>100)
            throw new RTEAge("年龄输入错误！");
        this.age = age;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
