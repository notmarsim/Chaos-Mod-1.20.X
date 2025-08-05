package net.marsim.chaosmod.block.entity;

public enum ConnectionType {
    NONE,
    INPUT,
    OUTPUT,
    INPUT_OUTPUT;

    public ConnectionType getNext() {
        return switch (this) {
            case NONE -> INPUT_OUTPUT;
            case INPUT -> OUTPUT;
            case OUTPUT -> NONE;
            case INPUT_OUTPUT -> INPUT;
        };
    }


    public boolean canReceive() {
        return this == INPUT || this == INPUT_OUTPUT;
    }



    public boolean canExtract() {
        return this == OUTPUT || this == INPUT_OUTPUT;
    }
}
