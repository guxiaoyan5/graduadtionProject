package edu.Infomation.enumObject;

public enum Sex {
    MAN("男"),
    WOMAN("女");

    private final String sex;

    Sex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Sex{" +
                "sex='" + sex + '\'' +
                '}';
    }

    public String getSex() {
        return sex;
    }
}
