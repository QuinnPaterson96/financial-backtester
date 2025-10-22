@RestController
@RequestMapping("/replay")
@RequiredArgsConstructor
public class ReplayController {
    private final ReplayService replayService;

    @PostMapping("/start")
    public ResponseEntity<String> start(@RequestBody ReplayRequest req) {
        replayService.startReplay(req);
        return ResponseEntity.ok("Started replay for " + req.getDataset());
    }

    @PostMapping("/pause")  public void pause() { replayService.pause(); }
    @PostMapping("/resume") public void resume() { replayService.resume(); }
    @PostMapping("/stop")   public void stop() { replayService.stop(); }

    @PostMapping("/speed")
    public void setSpeed(@RequestBody SpeedRequest req) { replayService.setSpeed(req.getSpeed()); }

    @PostMapping("/seek")
    public void seek(@RequestBody SeekRequest req) { replayService.seek(req.getTargetInstant()); }

    @GetMapping("/status")
    public ReplayStatus status() { return replayService.getStatus(); }
}
