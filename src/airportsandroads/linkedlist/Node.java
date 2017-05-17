/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportsandroads.linkedlist;

/**
 *
 * @author John
 */
public class Node<T> {

    private T info;
    private Node link;

    public Node(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public Node<T> getLink() {
        try {
            return link;
        } catch (Exception ex) {
            return null;
        }
    }

    public void setLink(Node<T> link) {
        this.link = link;
    }
}

