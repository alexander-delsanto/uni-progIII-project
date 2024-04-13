package model.message;

import java.util.Date;
import java.util.List;

public record EmailMessage(String sender, String recipients, String subject, String body, int id, Date sentTime) {}
