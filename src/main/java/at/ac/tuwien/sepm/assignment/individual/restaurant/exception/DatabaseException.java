package at.ac.tuwien.sepm.assignment.individual.restaurant.exception;

public class DatabaseException extends Exception {

    private String message;

    public DatabaseException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

}
