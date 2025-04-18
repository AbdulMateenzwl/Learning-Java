package org.example;

abstract class Employe implements IEmploye, IEmployeBonus {
    int Id;
    String Name;

    public Employe(int id, String name) {
        Id = id;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    abstract public Float calculateBonus(Float salary);

    @Override
    public String toString() {
        return "Employee{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                '}';
    }
}

class PermanentEmploye extends Employe {
    public PermanentEmploye(int id, String name) {
        super(id, name);
    }


    @Override
    public Float getMinimumSalary() {
        return 0f;
    }

    @Override
    public Float calculateBonus(Float salary) {
        return salary * 0.1f;
    }
}

class ContractEmploye implements IEmploye {
    int Id;
    String Name;

    public ContractEmploye(int id, String name) {
        this.Id = id;
        this.Name = name;
    }


    @Override
    public int getId() {
        return Id;
    }

    @Override
    public void setId(int id) {
        Id = id;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String name) {
        this.Name = name;
    }

    @Override
    public Float getMinimumSalary() {
        return 0f;
    }
}

class TemporaryEmploye extends Employe {
    public TemporaryEmploye(int id, String name) {
        super(id, name);
    }

    @Override
    public Float calculateBonus(Float salary) {
        return salary * 0.02f;
    }

    @Override
    public Float getMinimumSalary() {
        return 0f;
    }
}

interface IEmploye {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Float getMinimumSalary();
}

interface IEmployeBonus {
    Float calculateBonus(Float salary);
}

// The Liskov Substitution Principle (LSP) states that objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program. In other words, if class S is a subclass of class T, then objects of type T should be replaceable with objects of type S without altering any of the desirable properties of the program.

public class _04_LSP {
    public static void main(String[] args) {
        Employe emp1 = new PermanentEmploye(4, "Mateen");
//        Employe emp2 = new ContractEmploye(5, "Ali");
        Employe emp3 = new TemporaryEmploye(6, "Sara");
        System.out.println(emp1.calculateBonus(120000f));
        System.out.println(emp3.calculateBonus(120000f));

        IEmploye emp4 = new ContractEmploye(4, "Mateen");
        IEmploye emp5 = new TemporaryEmploye(5, "Ali");
        IEmploye emp6 = new PermanentEmploye(6, "Sara");

        System.out.println(emp4.getMinimumSalary());
        System.out.println(emp5.getMinimumSalary());
        System.out.println(emp6.getMinimumSalary());

    }
}
