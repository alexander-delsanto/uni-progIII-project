package model;

public class EmailValidator {
    public boolean isAddressValid(String address) {
        if (address == null || address.isEmpty())
            return false;
        address = address.trim();

        String[] splitNameAndDomain = address.split("@");
        if (splitNameAndDomain.length != 2) return false;
        String[] splitDot = splitNameAndDomain[1].split("\\.");
        if (splitDot.length != 2) return false;

        boolean res = containsOnlyLettersAndNumbers(splitNameAndDomain[0]);
        for (String s : splitDot)
            res &= containsOnlyLettersAndNumbers(s);

        return res;
    }

    public boolean containsOnlyLettersAndNumbers(String str) {
        if (str == null || str.isEmpty()) return false;
        str = str.toLowerCase();
        for (char c : str.toCharArray())
            if (!(c >= 'a' && c <= 'z' || c >= '0' && c <= '9'))
                return false;

        return true;
    }
}
