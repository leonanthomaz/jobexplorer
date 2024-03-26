package app.vercel.leonanthomaz.jobexplorer.controller;


import app.vercel.leonanthomaz.jobexplorer.model.Job;
import app.vercel.leonanthomaz.jobexplorer.service.JobService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("jobs")
@Log4j2
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

//    @GetMapping("/match")
//    public ResponseEntity<Job> buscarVagas(@RequestParam String job) {
//        // Chamada para o serviço de busca de vagas com o cargo especificado
//        return ResponseEntity.status(HttpStatus.OK).body(jobService.searchJobs(job));
//    }

    @GetMapping("/search")
    public ResponseEntity<List<Job>> buscarVagas(@RequestParam String job) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.searchJobs(job));
    }
}