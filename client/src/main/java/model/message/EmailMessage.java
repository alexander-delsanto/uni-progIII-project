package model.message;

import java.util.Date;

public record EmailMessage(String sender, String recipients, String subject, String body, int id, Date sentTime) {}
