@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketRecord {
    private Instant date;
    private String ticker;
    private double open, high, low, close, adjClose, volume;

    public static MarketRecord from(ResultSet rs) throws SQLException {
        return new MarketRecord(
                rs.getTimestamp("Date").toInstant(),
                rs.getString("Ticker"),
                rs.getDouble("Open"),
                rs.getDouble("High"),
                rs.getDouble("Low"),
                rs.getDouble("Close"),
                rs.getDouble("Adj Close"),
                rs.getDouble("Volume")
        );
    }

    public String toJson() {
        return String.format(
                "{\"date\":\"%s\",\"ticker\":\"%s\",\"close\":%.2f}",
                date, ticker, close
        );
    }
}

