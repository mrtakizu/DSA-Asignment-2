
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tnnh
 */
public class WordList {

    private Word root;
    private String words;
    private List<String> means;

    public WordList() {
        root = null;
    }

    public void Add(String name, List<String> meaning) {
        root = Insert(root, name, meaning);
    }

    private Word Insert(Word newWord, String name, List<String> meaning) {
        if (newWord == null) {
            newWord = new Word(name, meaning);
        } else {
            if (newWord.getName().compareTo(name) > 0) {
                newWord.setLeft(Insert(newWord.getLeft(), name, meaning));
            } else {
                newWord.setRight(Insert(newWord.getRight(), name, meaning));
            }
        }
        return newWord;
    }

    //Find the successor(at-most one child - right child)
    private static Word Successor(Word currentWord) {
        while (currentWord.getLeft() != null) {
            currentWord = currentWord.getLeft();
        }
        return currentWord;
    }

    public Word Delete(Word word, String name) {
        //Store parent node
        Word parent = null;

        //Start from root
        Word currentWord = root;

        //Search in BST list and set parent
        while (currentWord != null && !currentWord.getName().equals(name)) {
            //Update parent
            parent = currentWord;

            if (currentWord.getName().compareTo(name) > 0) {
                currentWord = currentWord.getLeft();
            } else {
                currentWord = currentWord.getRight();
            }
        }
        //if word's name is not found in list
        if (currentWord == null) {
            return root;
        }

        // if delete node is a leaf node (no children)
        if (currentWord.getLeft() == null && currentWord.getRight() == null) {
            //Set the parent point to this node == null
            if (currentWord != root) {
                if (parent.getLeft() == currentWord) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
            } //currentWord = root (only one element in list)
            else {
                root = null;
            }
        } //if delete node has 2 children
        else if (currentWord.getLeft() != null && currentWord.getRight() != null) {
            // Find successor of delete node
            Word successor = Successor(currentWord.getRight());

            //Store data successor
            String wordName = successor.getName();
            List<String> wordMean = successor.getMeaning();

            //Delete successor
            Delete(root, successor.getName());

            //Copy data of successor to current node
            currentWord.setName(wordName);
            currentWord.setMeaning(wordMean);
        } // if delete node has only one child, replace the node by it's child
        else {
            //Find child node
            Word child = (currentWord.getLeft() != null) ? currentWord.getLeft() : currentWord.getRight();

            if (currentWord != root) {
                if (parent.getLeft() == currentWord) {
                    parent.setLeft(child);
                } else {
                    parent.setRight(child);
                }
            } //if delete node is root
            else {
                root = child;
            }
        }
        return root;

    }

    public Word getRoot() {
        return root;
    }

    public void setRoot(Word root) {
        this.root = root;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

}
