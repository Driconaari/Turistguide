package tourism.repository;

import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository

public class TouristRepository {

    private List<TouristAttraction> attractions = new ArrayList<>();


    public TouristRepository() {
        attractions.add(new TouristAttraction("Tivoli Gardens", "Amusement park in Copenhagen city center"));
        attractions.add(new TouristAttraction("Bakken", "Amusement park near Copenhagen"));
        attractions.add(new TouristAttraction("Legoland Billund", "Theme park based on the Lego toy brand in Billund"));
        attractions.add(new TouristAttraction("Rosenborg Slot", "Renaissance castle in Copenhagen housing the Crown Jewels"));
        attractions.add(new TouristAttraction("Hotel D'Angleterre, Copenhagen", "Luxury hotel located in the heart of Copenhagen"));
        attractions.add(new TouristAttraction("Frederik's Church, Copenhagen", "Popular church in Copenhagen known for its distinctive copper-green spire"));
    }

    public List<TouristAttraction> getAttractions() {
        return attractions;
    }

    public TouristAttraction getAttractionByName(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName() != null && attraction.getName().equals(name)) {
                return attraction;
            }
        }
        return null;
    }


    public void updateAttraction(TouristAttraction updatedAttraction) {
        for (int i = 0; i < attractions.size(); i++) {
            TouristAttraction existingAttraction = attractions.get(i);
            if (existingAttraction.getName().equals(updatedAttraction.getName())) {
                existingAttraction.setDescription(updatedAttraction.getDescription());
                attractions.set(i, existingAttraction);
                break;
            }

        }
    }

    public void addAttraction(TouristAttraction attraction) {
        attractions.add(attraction);
    }

    public void deleteAttraction(String name) {
        Iterator<TouristAttraction> iterator = attractions.iterator();
        while (iterator.hasNext()) {
            TouristAttraction attraction = iterator.next();
            if (attraction.getName().equals(name)) {
                iterator.remove();
            }
        }

    }

    public List<TouristAttraction> getAllAttractions() {
        return attractions;
    }

    public String getTivoliDescription() {
        for (TouristAttraction attraction : attractions) {
            if ("Tivoli Gardens".equals(attraction.getName())) {
                return attraction.getDescription();
            }
        }
        return ""; // Return an empty string if Tivoli Gardens is not found
    }



}