package app.vercel.leonanthomaz.jobexplorer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    private String title;
//    private String description;
    private String location;
    private String timePosted;
    private String link;
}
