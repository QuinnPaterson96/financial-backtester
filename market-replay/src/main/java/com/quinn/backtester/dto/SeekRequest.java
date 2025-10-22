@Data
public class SeekRequest {
    private String targetDate;
    public Instant getTargetInstant() {
        return Instant.parse(targetDate + "T00:00:00Z");
    }
}
