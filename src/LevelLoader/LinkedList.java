
package LevelLoader;

public class LinkedList<T> {
  private Node<T> head;
  private Node<T> tail;
  private int size;

  // Node
  public static class Node<T> {
    T data;
    Node<T> next;
    Node<T> prev;
    Node<T> bonus;

    public Node(T data) {
      this.data = data;
      this.prev = null;
      this.next = null;
      this.bonus = null;
    }

    public Node(T data, Node<T> prev, Node<T> next, Node<T> bonus) {
      this.data = data;
      this.prev = prev;
      this.next = next;
      this.bonus = bonus;
    }

    public Node(T data, Node<T> prev, Node<T> next) {
      this.data = data;
      this.prev = prev;
      this.next = next;
      this.bonus = null;
    }
  }

  public void add(T data) {
    Node<T> newNode = new Node<T>(data);
    if (size == 0) {
      this.head = newNode;
      this.tail = newNode;
    } else if (size > 0) {
      newNode.prev = this.tail;
      this.tail.next = newNode;
      this.tail = newNode;
    }
    size++;
  }

  public Node<T> get(int index) {
    Node<T> node = this.tail;
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    for (int i = size - 1; i > index; i--) {
      node = node.prev;
    }
    return node;
  }

  public void addBonus(Node<T> bonus, int index) {
		Node<T> node = this.tail;
		if (index < 0 || index >= size) {
		    throw new IndexOutOfBoundsException();
		}
		for (int i = size - 1; i > index; i--) {
			node = node.prev;
		}
		node.bonus = bonus;
		bonus.prev = node;
		
	}
}
