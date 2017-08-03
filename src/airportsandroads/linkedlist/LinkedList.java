/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportsandroads.linkedlist;

/**
 *
 * @author John
 * @param <T>
 */
public class LinkedList<T> {

    private Node<T> ptr, last;
    private int size;

    public LinkedList() {
        clear();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T info) {
        if (info != null) {
            Node<T> q = new Node<>(info);
            if (ptr == null) {
                ptr = q;
                last = q;
            } else {
                last.setLink(q);
                last = q;
            }
            size++;
        }
    }

    public void delete(T info) {
        if (info != null) {
            Node<T> p = ptr;
            Node antp = null;
            if (ptr != null) {
                while (p.getInfo() != info && p.getLink() != null) {
                    antp = p;
                    p = p.getLink();
                }
                if (p.getInfo() == info) {
                    if (p == ptr) {
                        ptr = p.getLink();
                        p.setLink(null);
                    } else {
                        if (p == last) {
                            last = antp;
                        }
                        antp.setLink(p.getLink());
                        p.setLink(null);
                    }
                    size--;
                } else {
                    System.out.println("No se encontro el elemento");
                }
            } else {
                System.out.println("Lista vacia!");
            }
        }
    }

    public void delete(Node<T> q) {
        delete(q.getInfo());
    }

    public void delete(int position) {
        delete(get(position));
    }

    public void remove(int position) {
        delete(get(position));
    }

    public Node<T> getNode(int position) {
        if (position < size) {
            Node<T> p = ptr;
            for (int i = 0; i < size; i++) {
                if (i == position) {
                    return p;
                }
                p = p.getLink();
            }
        }
        return null;
    }

    public T get(int position) {
        try {
            return getNode(position).getInfo();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean add(int index, T info) {
        if (index > 0 && index < size && info != null) {
            Node<T> p = ptr;
            Node antp = null;
            if (ptr != null) {
                int cont = 0;
                while (cont < index) {
                    antp = p;
                    p = p.getLink();
                    cont++;
                }
                Node<T> q = new Node(info);
                if (antp == null) {
                    ptr = q;
                    q.setLink(p);
                } else if (p == last) {
                    p.setLink(q);
                    last = q;
                } else {
                    antp.setLink(q);
                    q.setLink(p);
                }
                return true;
            }
        }
        return false;
    }

    public boolean set(int index, T info) {
        if (index > 0 && index < size && info != null) {
            Node<T> p = ptr;
            Node antp = null;
            if (ptr != null) {
                int cont = 0;
                while (cont < index) {
                    antp = p;
                    p = p.getLink();
                    cont++;
                }
                Node<T> q = new Node(info);
                if (antp == null) {
                    ptr = q;
                    q.setLink(p.getLink());
                } else {
                    antp.setLink(q);
                    q.setLink(p.getLink());
                }
                if (p == last) {
                    last = q;
                }
                p.setLink(null);
                return true;
            }
        }
        return false;
    }

    public void changePosition(int positionA, int positionB) {
        if (size > 1 && 0 <= positionA && positionA < positionB && positionB < size) {
            if (positionB == positionA + 1) {
                Node<T> a = getNode(positionA), b = getNode(positionB);
                Node<T> antA = getNode(positionA - 1), bLink = b.getLink();
                if (antA != null) {
                    antA.setLink(b);
                } else {
                    ptr = b;
                }
                b.setLink(a);
                if (bLink != null) {
                    a.setLink(bLink);
                } else {
                    a.setLink(null);
                    last = a;
                }
            } else {
                Node<T> a = getNode(positionA), b = getNode(positionB);
                Node<T> antA = getNode(positionA - 1), antB = getNode(positionB - 1);
                Node<T> aLink = a.getLink(), bLink = b.getLink();
                if (antA != null) {
                    antA.setLink(b);
                } else {
                    ptr = b;
                }
                b.setLink(aLink);
                antB.setLink(a);
                if (bLink != null) {
                    a.setLink(bLink);
                } else {
                    a.setLink(null);
                    last = a;
                }
            }
        } else if (size > 1 && 0 <= positionB && positionB < positionA && positionA < size) {
            changePosition(positionB, positionA);
        }
    }

    public void addFirst(T info) {
        if (info != null) {
            Node<T> p = new Node<>(info);
            if (ptr == null) {
                ptr = p;
                last = p;
            } else {
                p.setLink(ptr);
                ptr = p;
            }
            size++;
        }
    }

    public void pollFirst() {
        if (size > 0) {
            ptr = ptr.getLink();
            size--;
        }
    }

    public void addLast(T info) {
        if (info != null) {
            Node<T> p = new Node<>(info);
            if (ptr == null) {
                ptr = p;
                last = p;
            } else {
                last.setLink(p);
                last = p;
            }
            size++;
        }
    }

    public void pollLast() {
        if (size > 0) {
            if (ptr == last) {
                ptr = null;
                last = null;
            } else {
                Node<T> p = ptr;
                while (p.getLink() != last) {
                    p = p.getLink();
                }
                p.setLink(null);
                last = p;
            }
            size--;
        }
    }

    public int size() {
        return size;
    }

    public T getFirst() {
        try {
            return ptr.getInfo();
        } catch (Exception e) {
            return null;
        }
    }

    public T getLast() {
        try {
            return last.getInfo();
        } catch (Exception e) {
            return null;
        }
    }

    public void clear() {
        size = 0;
        ptr = null;
        last = null;
    }

    public boolean contains(T info) {
        Node<T> p = ptr;
        while (p != null) {
            if (p.getInfo() == info) {
                return true;
            }
            p = p.getLink();
        }
        return false;
    }

    public int indexOf(T info) {
        Node<T> p = ptr;
        int index = -1;
        while (p != null) {
            index++;
            if (p.getInfo() == info) {
                return index;
            }
        }
        return -1;
    }

    public void addAll(LinkedList<T> list) {
        if (list != null) {
            Node<T> pList = list.getNode(0), p;
            for (int i = 0; i < list.size; i++) {
                add(pList.getInfo());
                pList = pList.getLink();
            }
        }
    }

}
