package controllers.stub;

import controllers.interfaces.IAppResources;
import data.Location;
import data.Person;
import data.Searchable;

import java.util.ArrayList;

public class StubAppResources implements IAppResources {

    private static StubAppResources controller = null;

    private ArrayList<Searchable> list= new ArrayList<Searchable>();

    private StubAppResources(){
        Location loc1 = new Location("x1.80", "Auditorium", 0, null, 10, 10);
        Location loc2 = new Location("x1.81", "Wierd room", 0, null, 12, 10);
        Location loc3 = new Location("m1.0", "Auditorium", 2, null, 12, 5);

        list.add(loc1);
        list.add(loc2);
        list.add(loc3);

        Person pers1 = new Person(0, "Julius Cæsar", "Cæsar@rome.it", "Emperor of rome.", "http://google.com", "Emperor", "m.10");
        Person pers2 = new Person(1, "Jørgen Leth", "jørgen@tv2.dk", "Elsker magnum", "http://google.com", "Vigtig mand", "x1.81");
        Person pers3 = new Person(2, "Søren Ryge", "søren@tv2.dk", "Horticulture", "http://google.com", "Glorified Gardnerer", "x1.81");

        list.add(pers1);
        list.add(pers2);
        list.add(pers3);

    }

    public static synchronized StubAppResources getInstance(){
        if(controller == null){
            controller = new StubAppResources();
        }

        return controller;
    }

    @Override
    public ArrayList<Searchable> search(String searchText) {
        return list;
    }
}
