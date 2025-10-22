@Slf4j
public class FeedEmitter {
    private final String dataset;
    private final KafkaTemplate<String, String> kafka;
    private final Connection conn;
    private ResultSet rs;
    private MarketRecord next;

    public FeedEmitter(String dataset, KafkaTemplate<String, String> kafka, String parquetPath) throws SQLException {
        this.dataset = dataset;
        this.kafka = kafka;
        this.conn = DriverManager.getConnection("jdbc:duckdb:");
        Statement stmt = conn.createStatement();
        this.rs = stmt.executeQuery("SELECT * FROM read_parquet('" + parquetPath + "') ORDER BY Date ASC");
        advance();
    }

    private void advance() throws SQLException {
        if (rs.next()) next = MarketRecord.from(rs);
        else next = null;
    }

    public void emitUpTo(Instant simTime) throws SQLException {
        while (next != null && !next.getDate().isAfter(simTime)) {
            kafka.send("market-replay." + dataset, next.toJson());
            advance();
        }
    }

    public void close() throws SQLException { conn.close(); }
}
