package api.rest.pojos;

import data.Person;

public class PersonChange {

    PersonChange(){

    }
    Person newPerson;
    Person oldPerson;

    public Person getNewPerson() {
        return newPerson;
    }

    public void setNewPerson(Person newPerson) {
        this.newPerson = newPerson;
    }

    public Person getOldPerson() {
        return oldPerson;
    }

    public void setOldPerson(Person oldPerson) {
        this.oldPerson = oldPerson;
    }
}
