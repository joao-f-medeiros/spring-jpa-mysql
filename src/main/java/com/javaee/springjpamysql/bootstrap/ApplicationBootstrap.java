package com.javaee.springjpamysql.bootstrap;

import com.javaee.springjpamysql.domain.Category;
import com.javaee.springjpamysql.domain.Garage;
import com.javaee.springjpamysql.domain.GasStation;
import com.javaee.springjpamysql.repositories.CategoryRepository;
import com.javaee.springjpamysql.repositories.GarageRepository;
import com.javaee.springjpamysql.repositories.GasStationRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Profile({"dev", "prod"})
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent>{

	private final CategoryRepository categoryRepository;
	private final GarageRepository garageRepository;
	private final GasStationRepository gasStationRepository;
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
//		if (categoryRepository.count() == 0L) {
//			categoryRepository.deleteAll();
			loadCategories();
//		}
	}
	
	private void loadCategories() {
        Category cat1 = new Category();
        cat1.setDescription("Franchise");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setDescription("Self owner");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setDescription("borrowing");
        categoryRepository.save(cat3);

		GasStation gasStation = new GasStation();
		gasStation.setName("Gas Statio");
		GasStation gasStationSaved = gasStationRepository.save(gasStation);
//		garageRepository.deleteAll();
		Garage garage = new Garage();
		garage.setName("Test");
		garage.setAddress("Addreess");
		garage.addCategory(cat1);
		garage.addCategory(cat2);
		garage.addCategory(cat3);
		garage.setGasStation(gasStationSaved);
		Garage garageSaved = garageRepository.save(garage);
		garageRepository.findByName(garageSaved.getName()).forEach(garage1 -> {
			garage1.setAddress("Another");
			garage1.getCategories().remove(cat2);
			garageRepository.save(garage1);
		});
		garageRepository.findAll().forEach(garageRepository::delete);
		if(garageSaved != null) {
//			throw new IllegalArgumentException();
		}
	}

}
