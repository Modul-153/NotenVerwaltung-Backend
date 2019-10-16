package net.myplayplanet.querygenerator.api.exceptions;

/**
 * this exception should be thrown if there was no translation via the {@link net.myplayplanet.querygenerator.api.FieldTranslator} found.
 */
public class NoNameFoundException extends RuntimeException {
    public NoNameFoundException() {
    }

    public NoNameFoundException(String message) {
        super(message);
    }
}
