package tourism.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    @Autowired
    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<TouristAttraction> getAllAttractions() {
        return touristRepository.getAttractions();
    }

    public TouristAttraction getAttractionByName(String name) {
        return touristRepository.getAttractionByName(name);
    }

    public void addAttraction(TouristAttraction attraction) {
        touristRepository.addAttraction(attraction);
    }


    public void deleteAttraction(String name) {
        // Perform error handling if attraction not found
        touristRepository.deleteAttraction(name);
    }

    public void updateAttraction(TouristAttraction updatedAttraction) {
        TouristAttraction existingAttraction = touristRepository.getAttractionByName(updatedAttraction.getName());
        if (existingAttraction != null) {
            existingAttraction.setDescription(updatedAttraction.getDescription());
            touristRepository.updateAttraction(existingAttraction);
        } else {
            // Handle case where attraction to update is not found
        }
    }


}
