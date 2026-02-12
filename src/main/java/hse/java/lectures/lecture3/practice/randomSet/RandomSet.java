package hse.java.lectures.lecture3.practice.randomSet;
import java.util.Random;


public class RandomSet<T extends Comparable<T>> {
    Node root;
    El[] vals;
    int size;
    private Random rand = new Random();


    public RandomSet() {
        vals = (El[]) java.lang.reflect.Array.newInstance(El.class, 8);
        size = 0;
    }


    private class Node{
        T value;
        int ind;
        Node p;
        Node l;
        Node r;
        int pr;

        Node(T value, int ind) {
            this.value = value;
            this.ind = ind;
            this.p = null;
            this.l = null;
            this.r = null;
            this.pr = rand.nextInt();
        }
    }

    private Node rotateRight(Node v) {
        Node u = v.l;
        v.l = u.r;
        if (u.r != null) {
            u.r.p = v;
        }
        u.r = v;
        u.p = v.p;
        v.p = u;

        return u;
    }

    private Node rotateLeft(Node v) {
        Node u = v.r;
        v.r = u.l;
        if (u.l != null) {
            u.l.p = v;
        }
        u.l = v;
        u.p = v.p;
        v.p = u;

        return u;
    }

    private class El {
        T val;
        Node node;
    }

    private class Pair {
        Node node;
        boolean flag;
        Pair(Node node, boolean flag) {
            this.node = node;
            this.flag = flag;
        }
    }


    private Pair insert(Node v, T value, int ind) {
        if (v == null) {
            Node newNode = new Node(value, ind);
            add(newNode);
            return new Pair(newNode, true);
        }

        if (value.compareTo(v.value) < 0) {
            Pair l = insert(v.l, value, ind);
            v.l = l.node;
            v.l.p = v;

            if (v.l.pr > v.pr) {
                v = rotateRight(v);
            }
            return new Pair(v, l.flag);
        }
        else if (value.compareTo(v.value) > 0) {
            Pair r = insert(v.r, value, ind);
            v.r = r.node;
            v.r.p = v;

            if (v.r.pr > v.pr) {
                v = rotateLeft(v);
            }
            return new Pair(v, r.flag);
        }

        return new Pair(v, false);
    }

    public boolean insert(T value) {
        Pair res = insert(root, value, size);
        root = res.node;
        return res.flag;
    }


    private Node Next(Node v) {
        if (v.r != null) {
            v = v.r;
            while (v.l != null) {
                v = v.l;
            }
            return v;
        }
        else {
            while (v.p != null && v.p.r == v) {
                v = v.p;
            }
            return v.p;
        }
    }

    private Pair remove(Node v, T value) {
        if (v == null) {
            return new Pair(null, false);
        }

        if (value.compareTo(v.value) < 0) {
            Pair l = remove(v.l, value);
            v.l = l.node;
            if (v.l != null) {
                v.l.p = v;
            }
            return new Pair(v, l.flag);
        }
        else if (value.compareTo(v.value) > 0) {
            Pair r = remove(v.r, value);
            v.r = r.node;
            if (v.r != null) {
                v.r.p = v;
            }
            return new Pair(v, r.flag);
        }
        else  {
            if (v.l == null && v.r == null) {
                del(v.ind);
                return new Pair(null, true);
            }
            if (v.l == null) {
                v.r.p = v.p;
                del(v.ind);
                return new Pair(v.r, true);
            }
            if (v.r == null) {
                v.l.p = v.p;
                del(v.ind);
                return new Pair(v.l, true);
            }
            else {
                Node next = Next(v);

                T temp = v.value;
                v.value = next.value;
                next.value = temp;

                if (next.p.l == next) {
                    next.p.l = next.r;
                }
                else {
                    next.p.r = next.r;
                }

                if (next.r != null) {
                    next.r.p = next.p;
                }

                del(next.ind);

                return new Pair(v, true);
            }
        }
    }

    public boolean remove(T value) {
        Pair res = remove(root, value);
        root = res.node;
        return res.flag;
    }


    public boolean contains(T value) {
        Node v = root;

        while (v != null) {
            if (value.compareTo(v.value) < 0) {
                v = v.l;
            }
            else if (value.compareTo(v.value) > 0) {
                v = v.r;
            }
            else {
                return true;
            }
        }

        return false;
    }

    public T getRandom() {
        if (size == 0) {
            throw new EmptySetException("попытка получить случайный элемент из пустого множества.");
        }
        return vals[rand.nextInt(size)].val;
    }

    private void add(Node v) {
        if (size == vals.length) {
            El[] temp = (El[]) java.lang.reflect.Array.newInstance(El.class, vals.length * 2);
            System.arraycopy(vals, 0, temp, 0, vals.length);
            vals = temp;
        }
        vals[size] = new El();
        vals[size].val = v.value;
        vals[size++].node = v;
    }

    private void del(int ind) {
        vals[ind] = vals[size - 1];
        vals[ind].node.ind = ind;
        vals[size - 1] = null;
        size--;
    }
}
