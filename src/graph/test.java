package graph;

import java.util.List;

public class test implements Cloneable{
    private String name;
    private int age;
    private List<Integer> scores;
    @Override
    public test clone() throws CloneNotSupportedException {
        return (test)super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }
}
