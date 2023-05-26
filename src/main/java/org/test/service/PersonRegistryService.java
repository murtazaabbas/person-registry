package org.test.service;

import org.test.model.Child;
import org.test.model.Person;

public interface PersonRegistryService {

    public boolean savePerson(Person person);
    public Person getPerson(Long socialSecurityNumber);
    public Child getOldestChild(Long socialSecurityNumber);
}
