package com.idugalic.web.project;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idugalic.domain.project.Project;
import com.idugalic.domain.project.ProjectRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author idugalic
 * 
 * Project controller
 * 
 * On the server-side WebFlux supports 2 distinct programming models:
 *
 * - Annotation-based with @Controller and the other annotations supported also with Spring MVC
 * - Functional, Java 8 lambda style routing and handling
 * 
 * This is example of 'Annotation-based with @Controller' programming model.
 *
 */
@RestController
public class ProjectController {

	private final ProjectRepository projectRepository;

	public ProjectController(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@PostMapping("/projects")
	Mono<Void> create(@RequestBody Publisher<Project> projectStream) {
		return this.projectRepository.save(projectStream).then();
	}
	
	@GetMapping("/projects")
	Flux<Project> list() {
		return this.projectRepository.findAll();
	}
	
	@GetMapping("/projects/{id}")
	Mono<Project> findById(@PathVariable String id) {
		return this.projectRepository.findOne(id);
	}
	
	@GetMapping("/projects/search/byname")
	Flux<Project> findByName(@RequestParam String name) {
		return this.projectRepository.findByName(Mono.just(name));
	}
	
	

}