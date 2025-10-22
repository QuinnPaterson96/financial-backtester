@Service
@RequiredArgsConstructor
@Slf4j
public class ReplayService {
    private final KafkaTemplate<String, String> kafka;
    private final ClockService clockService;
    private ScheduledExecutorService scheduler;
    private List<FeedEmitter> feeds = new ArrayList<>();
    private volatile boolean running = false;

    public void startReplay(ReplayRequest req) {
        stop(); // stop previous session
        clockService.start(Instant.parse(req.getStartDate() + "T00:00:00Z"), req.getSpeed());

        try {
            FeedEmitter sp500 = new FeedEmitter("sp500", kafka, "data/parquet/equities_long.parquet");
            feeds = List.of(sp500);
        } catch (Exception e) {
            log.error("Error creating feed emitter", e);
            return;
        }

        running = true;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::tick, 0, 100, TimeUnit.MILLISECONDS);
        log.info("Replay started");
    }

    private void tick() {
        if (!running) return;
        Instant nowSim = clockService.nowSim();
        try {
            for (FeedEmitter f : feeds) f.emitUpTo(nowSim);
        } catch (Exception e) {
            log.error("Error during tick", e);
        }
    }

    public void stop() {
        running = false;
        if (scheduler != null) scheduler.shutdownNow();
        feeds.forEach(f -> { try { f.close(); } catch (Exception ignored) {} });
    }

    public void pause() { clockService.pause(); }
    public void resume() { clockService.resume(); }
    public void setSpeed(double s) { clockService.setSpeed(s); }
    public void seek(Instant target) { clockService.seekTo(target); }
    public ReplayStatus getStatus() { return clockService.status(); }
}
