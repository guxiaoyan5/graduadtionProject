package edu.Infomation.enumObject;

public enum ThreeMeals{
    BREAKFAST("早"),
    LUNCH("午"),
    DINNER("晚");

    private final String Meal;

    ThreeMeals(String meal) {
        Meal = meal;
    }

    @Override
    public String toString() {
        return "ThreeMeals{" +
                "Meal='" + Meal + '\'' +
                '}';
    }

    public String getMeal() {
        return Meal;
    }
}
