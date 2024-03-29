package tourism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;
import tourism.service.TouristService;
import org.springframework.ui.Model;

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
            model.addAttribute("attraction");
            return "attraction-details"; // This corresponds to the HTML page for displaying attraction details
        } else {
            return "attraction-not-found"; // This can be a custom error page for attraction not found
        }
    }

    @GetMapping("/tivoli")
    public String getTivoliHtml() throws IOException {
        // Read the contents of the tivoli.html file from the resources folder
        ClassPathResource resource = new ClassPathResource("static/tivoli.html");
        String tivoliHtml = new String(StreamUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8);

        // Get the hardcoded message from the repository
        String hardcodedMessage = touristRepository.getTivoliDescription();

        // Inject the hardcoded message into the HTML content
        tivoliHtml = tivoliHtml.replace("<!-- HARDCODED_MESSAGE -->", hardcodedMessage);

        return tivoliHtml;
    }


  /*@GetMapping("/tivoli")
  public String getTivoliHtml(Model model) {
      String description = touristRepository.getTivoliDescription();
      model.addAttribute("description", description);
      return "tivoli"; // Return the name of the HTML page (tivoli.html)
  }

   */

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

    /*@PostMapping("/add")
    public ResponseEntity<Void> addAttraction(@RequestBody TouristAttraction attraction) {
        touristService.addAttraction(attraction); // Call the addAttraction method from the service
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

     */

    /*@PostMapping("/add")
    public ResponseEntity<Void> addAttraction(@RequestParam String name, @RequestParam String description) {
        TouristAttraction newAttraction = new TouristAttraction(name, description);
        touristService.addAttraction(newAttraction);
        return new ResponseEntity<>(HttpStatus.CREATED);


     */

    @PostMapping("/addAttraction")
    public ResponseEntity<Void> addAttraction(@RequestParam String name, @RequestParam String description) {
        if (name != null && description != null) {
            TouristAttraction newAttraction = new TouristAttraction(name, description);
            touristService.addAttraction(newAttraction);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            // Handle invalid request
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/attractions/submit_attraction")
    public String showSubmitAttractionForm(Model model) {
        // Create an empty TouristAttraction object to bind to the form
        TouristAttraction newAttraction = new TouristAttraction();

        // Add the newAttraction object to the model to be used in the form
        model.addAttribute("newAttraction", newAttraction);

        // Return the name of the HTML template for the form
        return "submit_attraction_form";
    }


    @PostMapping("/attractions/submit_attraction")
    public String submitAttraction(@RequestParam("attractionName") String attractionName,
                                   @RequestParam("attractionDescription") String attractionDescription) {
        // Create a new TouristAttraction object with the provided name and description
        TouristAttraction newAttraction = new TouristAttraction(attractionName, attractionDescription);

        // Add the new attraction to the tourist repository
        touristRepository.addAttraction(newAttraction);

        // Redirect to the attractions page after submission
        return "redirect:/attractions";
    }





}