package ru.geekbrains.HW2;

public class Program {

    public static void main(String[] args) {

        LinkedList<Employee> linkedList = new LinkedList<>();
        linkedList.addFirst(new Employee("AAAAAAA", 0));
        linkedList.addFirst(new Employee("BBBBBB", 11));
        linkedList.addFirst(new Employee("CCCCC", 222));
        linkedList.addFirst(new Employee("DDDD", 3333));
        linkedList.addFirst(new Employee("EEE", 44444));
        linkedList.addFirst(new Employee("FF", 555555));
        linkedList.addFirst(new Employee("G", 6666666));

        System.out.println();
        System.out.println(linkedList);

        linkedList.reverse();
        System.out.println();
        System.out.println(linkedList);

        LinkedList<Integer> linkedList2 = new LinkedList();
        linkedList2.addLast(1);
        linkedList2.addLast(2);
        linkedList2.addLast(3);
        linkedList2.addLast(4);
        linkedList2.addLast(5);
        linkedList2.addLast(6);
        linkedList2.addLast(7);
        linkedList2.addLast(8);
        linkedList2.addLast(9);
        System.out.print(linkedList2.toString().replace("\n", " "));
        System.out.println();

        linkedList2.reverse();
        System.out.print(linkedList2.toString().replace("\n", " "));

    }

}
