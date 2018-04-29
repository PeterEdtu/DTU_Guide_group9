package api.rest.pojos;

import data.Person;
import data.SuggestionPerson;

public class PersonChange {

    public PersonChange(){

    }
    Person newPerson;
    SuggestionPerson oldPerson;

    public Person getNewPerson() {
        return newPerson;
    }

    public void setNewPerson(Person newPerson) {
        this.newPerson = newPerson;
    }

    public SuggestionPerson getOldPerson() {
        return oldPerson;
    }

    public void setOldPerson(SuggestionPerson oldPerson) {
        this.oldPerson = oldPerson;
    }
}
