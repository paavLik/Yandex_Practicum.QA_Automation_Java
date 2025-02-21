package ru.yandex;

public class Account {

    private final String ownerDetails;

    public Account(String ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

    public boolean checkNameToEmboss() {
        return checkLengthName() && checkOneSpace() && checkSpaceStartOrEnd();

    }

    public boolean checkLengthName() {
        return ownerDetails.length() >= 3 && ownerDetails.length() <= 19;
    }

    public boolean checkOneSpace() {
        return ownerDetails.split(" ").length == 2;
    }

    public boolean checkSpaceStartOrEnd() {
        return !ownerDetails.startsWith(" ") && !ownerDetails.endsWith(" ");
    }
}