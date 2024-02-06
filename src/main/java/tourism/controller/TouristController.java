package tourism.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;
import tourism.service.TouristService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;
    private final TouristRepository touristRepository;

    @Autowired
    public TouristController(TouristService touristService, TouristRepository touristRepository) {
        this.touristService = touristService;
        this.touristRepository = touristRepository;
    }

    // GET all attractions
    @GetMapping
    public ResponseEntity<List<TouristAttraction>> getAllAttractions() {
        List<TouristAttraction> attractions = touristRepository.getAllAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }


    // GET attraction by name
    @GetMapping("/{name}")
    public String getAttractionDetails(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        if (attraction != null) {
            model.addText("attraction");
            return "attraction-details"; // This corresponds to the HTML page for displaying attraction details
        } else {
            return "attraction-not-found"; // This can be a custom error page for attraction not found
        }
    }

    @GetMapping("/tivoli")
    public String getTivoliHtml() throws IOException {
        // Read the contents of the tivoli.html file from the resources folder
        ClassPathResource resource = new ClassPathResource("static/tivoli.html");
        String descrtiption = touristRepository.getTivoliDescrtiption();
        byte[] fileBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        String tivoliHtml = new String(fileBytes, StandardCharsets.UTF_8);
        return tivoliHtml;
    }

    // POST to update an existing attraction
    @PostMapping("/update")
    public ResponseEntity<Void> updateAttraction(@RequestBody TouristAttraction attraction) {
        touristService.updateAttraction(attraction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // GET to delete an attraction by name
    @GetMapping("/delete/{name}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable String name) {
        touristService.deleteAttraction(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addAttraction(@RequestBody TouristAttraction attraction) {
        touristService.addAttraction(attraction); // Call the addAttraction method from the service
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}