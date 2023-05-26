package org.test.service;

import org.test.model.Child;
import org.test.model.Person;

import java.util.HashMap;
import java.util.Map;

public class PersonRegistryServiceImpl implements PersonRegistryService {

    private final Map<Long, Person> personMap;

    public PersonRegistryServiceImpl(){
        this.personMap = new HashMap<>();
    }

    @Override
    public boolean savePerson(Person person) {
        if(!isValidatePersonInfo(person)){
            return false;
        }
        personMap.put(person.getSocialSecurityNumber(), person);
        return true;
    }

    @Override
    public Person getPerson(Long socialSecurityNumber) {
        return personMap.get(socialSecurityNumber);
    }

    @Override
    public Child getOldestChild(Long socialSecurityNumber) {
        Person person = personMap.get(socialSecurityNumber);
        if (person == null) {
            return null;
        }

        return person.getChildren().stream()
                .reduce((p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2).get();
    }

    private boolean isValidatePersonInfo(Person person){
        if(person.getSocialSecurityNumber() == null || person.getSocialSecurityNumber()==0){
            return false;
        }
        if(person.getName() == null || person.getName().isEmpty()){
            return false;
        }
        return true;
    }
}
