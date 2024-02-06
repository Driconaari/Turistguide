package tourism.repository;

import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;

import java.util.ArrayList;
import java.util.List;

@Repository

public class TouristRepository {

    private List<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository() {
        attractions.add(new TouristAttraction("Tivoli", "Forlystelsespark midt i København centrum"));
        attractions.add(new TouristAttraction("Den Lille Havfrue", "Skulptur i København ved Langelinie"));
    }

    public  List<TouristAttraction> getAttractions() {
        return attractions;
    }

    public TouristAttraction getAttractionByName(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equals(name)){
                return attraction;
            }
         }

        return null;
    }


    public void  addAttraction (TouristAttraction attraction) {
        attractions.add(attraction);
    }
    public void deleteAttraction(String name) {
        attractions.removeIf(attraction -> attraction.getName().equals(name));
    }

}
