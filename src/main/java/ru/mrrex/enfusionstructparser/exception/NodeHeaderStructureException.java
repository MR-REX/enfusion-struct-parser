package ru.mrrex.enfusionstructparser.exception;

public class NodeHeaderStructureException extends RuntimeException {

    public NodeHeaderStructureException(String message, String nodeHeader) {
        super(message + ": " + nodeHeader);
    }

    public NodeHeaderStructureException(String nodeHeader) {
        this("Couldn't find object data from the header of entity template node", nodeHeader);
    }
}
