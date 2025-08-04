package team6.bw5_epic_energy_services.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("We haven't found an element with id " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }
}

