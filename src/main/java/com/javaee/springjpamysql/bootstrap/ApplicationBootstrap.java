package com.javaee.springjpamysql.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.javaee.springjpamysql.domain.Category;
import com.javaee.springjpamysql.repositories.CategoryRepository;

public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent>{

	private CategoryRepository categoryRepository;
	
	public ApplicationBootstrap(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (categoryRepository.count() == 0L) {
			categoryRepository.deleteAll();
			loadCategories();
		}
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
    }

}
