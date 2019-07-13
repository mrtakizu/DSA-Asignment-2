
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tnnh
 */
public class Word {

    private Word left, right;
    private String name;
    private List<String> meaning;

    public Word(String name, List<String> meaning) {
        this.name = name;
        this.meaning = meaning;
        left = null;
        right = null;
    }

    public Word getLeft() {
        return left;
    }

    public void setLeft(Word left) {
        this.left = left;
    }

    public Word getRight() {
        return right;
    }

    public void setRight(Word right) {
        this.right = right;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaningString() {
        String s = "";
        for (int i = 0; i < meaning.size(); i++) {
            if (i != (meaning.size() - 1)) {
                s += meaning.get(i) + ", ";
            } else {
                s += meaning.get(i);
            }
        }
        return s;
    }

    public List<String> getMeaning() {
        return meaning;
    }

    public void setMeaning(List<String> meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "Word: " + getName() + "Meaning: " + getMeaningString();
    }

}
