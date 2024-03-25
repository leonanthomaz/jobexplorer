package app.vercel.leonanthomaz.jobexplorer.service;

import app.vercel.leonanthomaz.jobexplorer.components.InfojobsProcessor;
import app.vercel.leonanthomaz.jobexplorer.components.LinkedinProcessor;
import app.vercel.leonanthomaz.jobexplorer.model.Job;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class JobService {
    @Autowired
    private InfojobsProcessor infojobsProcessor;

    @Autowired
    private LinkedinProcessor linkedinProcessor;

    public List<Job> searchJobs(String job){
        log.info("PALAVRA CHAVE SERVICE: {}", job);
        return infojobsProcessor.searchJobs(job);
    }
}