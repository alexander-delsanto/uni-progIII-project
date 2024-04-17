package model;

public class EmailValidator {

    public boolean checkValidity(String recipients) {
        if (recipients == null || recipients.isEmpty()) return false;
        recipients = recipients.trim();
        String[] splitRecipients = recipients.split(",");
        for (String recipient : splitRecipients)
            if (isAddressInvalid(recipient))
                return false;
        return true;
    }

    public boolean isAddressInvalid(String address) {
        if (address == null) return true;
        String trimmedAddress = address.trim();
        return !trimmedAddress.matches("[a-zA-Z0-9]{1,16}@[a-zA-Z0-9]{2,10}\\.[a-zA-Z0-9]{2,5}");
    }
}
