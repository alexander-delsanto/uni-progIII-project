package backend;

import java.util.List;
import java.util.Date;

public record Mail(String sender, List<String> recipients, String subject, String body, Date sentTime) {}
