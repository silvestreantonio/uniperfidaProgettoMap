/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.parser;

        import di.uniba.map.b.uniperfida.type.AdvObject;
        import di.uniba.map.b.uniperfida.type.Command;
        import di.uniba.map.b.uniperfida.type.Person;

/**
 *
 * @author pierpaolo
 */
public class ParserOutput {

    private Command command;

    private AdvObject object;

    private AdvObject invObject;

    private Person person;

    public ParserOutput(Command command, AdvObject object, AdvObject invObject) {
        this.command = command;
        this.object = object;
        this.invObject = invObject;
    }

    public ParserOutput(Command command, Person person) {
        this.command = command;
        this.person = person;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public AdvObject getObject() {
        return object;
    }

    public void setObject(AdvObject object) {
        this.object = object;
    }

    public AdvObject getInvObject() {
        return invObject;
    }

    public void setInvObject(AdvObject invObject) {
        this.invObject = invObject;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
