package backend;

import java.util.Date;
import java.util.List;

public record Email(String sender, List<String> recipients, String subject, String body, Date sentTime) {}
