package ru.example;

public class Door {
    boolean isOpen = false;
    Results behindTheDoor = Results.LOSSE;
    int id;

    Door(int id) {
        this.id = id;
    }
}
