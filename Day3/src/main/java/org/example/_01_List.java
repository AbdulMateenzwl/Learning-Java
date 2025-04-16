package org.example;


import java.util.*;

public class _01_List {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        //  boolean add(E e)
        list.add("Apple");  // Appends "Apple" to the end

        //  void add(int index, E element)
        list.add(1, "Banana");  // Inserts "Banana" at index 1

        //  boolean addAll(Collection<? extends E> c)
        List<String> fruits = Arrays.asList("Orange", "Mango");
        list.addAll(fruits);  // Adds all fruits to list

        //  boolean addAll(int index, Collection<? extends E> c)
        list.addAll(2, Arrays.asList("Grapes", "Pineapple")); // Inserts at index 2

        //  void clear()
        list.clear();  // Removes all elements

        list.addAll(Arrays.asList("A", "B", "C", "D"));

        //  boolean contains(Object o)
        System.out.println(list.contains("B"));  // true

        //  boolean containsAll(Collection<?> c)
        System.out.println(list.containsAll(Arrays.asList("A", "B")));  // true

        //  boolean equals(Object o)
        List<String> list2 = Arrays.asList("A", "B", "C", "D");
        System.out.println(list.equals(list2));  // false (ArrayList vs Arrays$ArrayList)

        //  E get(int index)
        System.out.println(list.get(2));  // "C"

        //  int hashCode()
        System.out.println(list.hashCode());  // Returns hash code

        //  int indexOf(Object o)
        // Returns -1 if not found
        System.out.println(list.indexOf("C"));  // 2

        //  boolean isEmpty()
        System.out.println(list.isEmpty());  // false

        //  Iterator<E> iterator()
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }

        //  int lastIndexOf(Object o)
        list.add("A");
        System.out.println("\nLast Index of A: " + list.lastIndexOf("A"));  // 4

        //  ListIterator<E> listIterator()
        ListIterator<String> listIt = list.listIterator();
        while (listIt.hasNext()) {
            System.out.print(listIt.next() + " ");
        }

        System.out.println("\n");
        //  ListIterator<E> listIterator(int index)
        ListIterator<String> revIt = list.listIterator(list.size());
        while (revIt.hasPrevious()) {
            System.out.print(revIt.previous() + " ");
        }
        System.out.println("\n");
        //  E remove(int index)
        list.remove(1);  // Removes element at index 1

        //  boolean remove(Object o)
        list.remove("A");  // Removes first occurrence of "A"

        //  boolean removeAll(Collection<?> c)
        list.removeAll(Arrays.asList("C", "D"));  // Removes both

        //  void replaceAll(UnaryOperator<E> operator)
        list.replaceAll(String::toUpperCase);  // Convert to uppercase

        //  boolean retainAll(Collection<?> c)
        list.retainAll(Arrays.asList("A", "Z"));  // Keeps only "A" and "Z"

        //  E set(int index, E element)
        list.set(0, "Kiwi");  // Replace element at index 0

        //  int size()
        System.out.println("Size: " + list.size());

        //  void sort(Comparator<? super E> c)
        list.add("Banana");
        list.sort(Comparator.naturalOrder());

        //  Spliterator<E> spliterator()
        Spliterator<String> sp = list.spliterator();
        sp.forEachRemaining(System.out::println);

        //  List<E> subList(int fromIndex, int toIndex)
        list.addAll(Arrays.asList("One", "Two", "Three", "Four"));
        List<String> sub = list.subList(1, 3);  // Returns elements from index 1 to 2

        //  Object[] toArray()
        Object[] arr = list.toArray();
        System.out.println(Arrays.toString(arr));

        //  <T> T[] toArray(T[] a)
        String[] strArr = list.toArray(new String[0]);
        System.out.println(Arrays.toString(strArr));
    }
}
