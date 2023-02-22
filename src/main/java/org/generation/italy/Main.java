package org.generation.italy;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.view.UserInterfaceConsole;

public class Main {
    public static void main(String[] args) { // absolutely nope
        UserInterfaceConsole console = new UserInterfaceConsole();
        console.start();
    }
}