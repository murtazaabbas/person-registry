package org.test.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.test.model.Child;
import org.test.model.Person;
import org.test.model.Spouse;

import java.util.HashSet;
import java.util.Set;

public class PersonRegistryServiceTest {

    private static Long testSocialSecurityNumber=199012121212l;
    // tests for savePerson(...) started
    @Test
    public void save_person_with_all_attributes_should_be_successful(){
        Person person = new Person(testSocialSecurityNumber,"Jhonny");

        Spouse spouse = new Spouse("Marie");
        person.setSpouse(spouse);

        Set<Child> children = new HashSet<>();
        children.add(new Child("Jone", 7));
        children.add(new Child("Markus", 2));
        person.setChildren(children);

        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        boolean result = personRegistryService.savePerson(person);
        Assertions.assertTrue(result);

    }

    @Test
    public void save_person_with_only_his_details_should_be_successful(){
        Person person = new Person(testSocialSecurityNumber,"Jhonny");

        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        boolean result = personRegistryService.savePerson(person);
        Assertions.assertTrue(result);

    }

    @Test
    public void save_person_duplicate_child_name_should_not_be_entered(){
        Person person = new Person(testSocialSecurityNumber,"Jhonny");

        Spouse spouse = new Spouse("Marie");
        person.setSpouse(spouse);

        Set<Child> children = new HashSet<>();
        children.add(new Child("Jone", 7));
        children.add(new Child("jone", 2));
        person.setChildren(children);

        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        personRegistryService.savePerson(person);
        Assertions.assertEquals(1, personRegistryService.getPerson(testSocialSecurityNumber).getChildren().size());
    }

    @Test
    public void save_person_without_child_details_should_be_Successful(){
        Person person = new Person(testSocialSecurityNumber,"Jhonny");

        Spouse spouse = new Spouse("Marie");
        person.setSpouse(spouse);

        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        boolean result = personRegistryService.savePerson(person);
        Assertions.assertTrue(result);

    }

    @Test
    public void save_person_without_spouse_details_should_be_Successful(){

        Person person = new Person(testSocialSecurityNumber,"Jhonny");

        Set<Child> children = new HashSet<>();
        children.add(new Child("Jone", 7));
        children.add(new Child("Markus", 2));
        person.setChildren(children);

        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        boolean result = personRegistryService.savePerson(person);
        Assertions.assertTrue(result);
    }

    @Test
    public void save_person_without_valid_security_number_should_be_fail(){
        Person person = new Person(0l,"Jhonny");

        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        boolean result = personRegistryService.savePerson(person);
        Assertions.assertFalse(result);
    }

    @Test
    public void save_person_without_valid_name_should_be_fail(){
        Person person = new Person(0l,"");

        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        boolean result = personRegistryService.savePerson(person);
        Assertions.assertFalse(result);
    }

    // tests for getPerson(...) start
    @Test
    public void get_person_with_valid_social_security_number_should_return_details(){
        Person person = new Person(testSocialSecurityNumber,"Jhonny");

        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        boolean result = personRegistryService.savePerson(person);
        Assertions.assertTrue(result);

        Person person1 = personRegistryService.getPerson(testSocialSecurityNumber);
        Assertions.assertEquals(person.getSocialSecurityNumber(), person1.getSocialSecurityNumber());
        Assertions.assertEquals(person.getName(), person1.getName());
    }

    @Test
    public void get_person_with_invalid_social_security_number_should_return_null(){
        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        Person person = personRegistryService.getPerson(11L);
        Assertions.assertNull(person);
    }

    // tests for getOldestChild (...) start
    @Test
    public void get_oldest_child_for_valid_social_security_number_should_return_child_detail(){
        Person person = new Person(testSocialSecurityNumber,"Jhonny");

        Spouse spouse = new Spouse("Marie");
        person.setSpouse(spouse);

        Set<Child> children = new HashSet<>();
        children.add(new Child("Jone", 7));
        children.add(new Child("Markus", 2));
        person.setChildren(children);

        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        personRegistryService.savePerson(person);

        Child oldestChild = personRegistryService.getOldestChild(testSocialSecurityNumber);
        Assertions.assertEquals("Jone", oldestChild.getName());
        Assertions.assertEquals(7, oldestChild.getAge());
    }

    @Test
    public void get_oldest_child_for_invalid_person_social_security_number_should_return_null(){
        PersonRegistryService personRegistryService = new PersonRegistryServiceImpl();
        Child oldestChild = personRegistryService.getOldestChild(testSocialSecurityNumber);
        Assertions.assertNull(oldestChild);
    }

}
