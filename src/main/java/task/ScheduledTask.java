package task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.formation.velo.service.StationService;
import lombok.extern.java.Log;

@Component
@Log

public class ScheduledTask {

    private final StationService stationService;

    public ScheduledTask(StationService stationService){

        this.stationService = stationService;

    }

    @Scheduled(fixedRate = 60000)

    public void searchNextMatchByCompetition(){

        log.info("update stations 😉 ");

        stationService.saveRecords();

    }



}
