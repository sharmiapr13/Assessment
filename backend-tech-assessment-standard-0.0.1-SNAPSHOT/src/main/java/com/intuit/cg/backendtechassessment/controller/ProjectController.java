package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.Entity.AutoBid;
import com.intuit.cg.backendtechassessment.Entity.Bid;
import com.intuit.cg.backendtechassessment.Entity.Project;
import com.intuit.cg.backendtechassessment.Service.ProjectService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping(RequestMappings.MARKETPLACE)
public class ProjectController {
    public static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    ProjectService projectService;

    /*
     *  Get all Project details -> localhost:8080/marketplace/projects, Method = GET
     *  Http Status: 200 OK (Success), 204 No Content (No Projects available)
     */
    @RequestMapping(value=RequestMappings.PROJECTS, method = RequestMethod.GET)
    public ResponseEntity<List<Project>> listAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        if (projects.isEmpty()) {
            logger.info("No projects are created yet");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Project>> (projects, HttpStatus.OK);
    }

    /*
     *  Get Project by Project ID -> localhost:8080/marketplace/projects/1, Method = GET
     *  Http Status: 200 OK (Success), 204 No Content (Project not available)
     */
    @RequestMapping(value =RequestMappings.PROJECTS+"/{projId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable("projId") long projId) {
        logger.info("Fetching Project details for Project Id: {} " + projId );
        Project project = projectService.findByProjId(projId);
        if (project == null) {
            logger.error("Project with Id {} not found" +projId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    /*
     *  Create Project -> localhost:8080/marketplace/projects, Method = POST
     *  Http Status: 201 Created (Success), 400 Bad Request (Project Name NULL),
     */
    @RequestMapping(value = RequestMappings.PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<?> createProject(@RequestBody Project project, UriComponentsBuilder uriComponentsBuilder) {
        logger.info("Creating Project: {}" + project );

        if (project.getProjName() == null) {
            logger.error("Error creating project. Project Name is null"
                    , project.getProjName());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (projectService.isProjectExists(project)) {
            logger.error("Error creating project. Project with Id {} already Exists"
                    , project.getProjName());
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        projectService.createProject(project);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path(RequestMappings.MARKETPLACE+ RequestMappings.PROJECTS +"/{projId}")
                .buildAndExpand(project.getProjId()).toUri());
        return new ResponseEntity<Project>(project, headers, HttpStatus.CREATED);
    }

    /*
     *  Update Project by Project ID -> localhost:8080/marketplace/projects/1, Method = PUT
     *  Http Status: 200 OK (Success), 204 No Content (Project not available)
     */
    @RequestMapping(value=RequestMappings.PROJECTS+"/{projId}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateProject(@PathVariable("projId") long projId, @RequestBody Project project) {
        logger.info("Updating Project with Id: "+ projId);

        Project updProject = projectService.findByProjId(projId);
        if (updProject == null) {
            logger.error("Cannot update the Project. Project with Id {} not found" + projId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        updProject.setProjName(project.getProjName());
        updProject.setMaxBudget(project.getMaxBudget());
        updProject.setMaxBidDateTime(project.getMaxBidDateTime());
        updProject.setProjDesc(project.getProjDesc());

        projectService.updateProject(updProject);
        return new ResponseEntity<Project>(updProject,HttpStatus.OK);
    }

    /*
     *  Delete Project by Project ID -> localhost:8080/marketplace/projects/1, Method = DELETE
     *  Http Status: 200 OK (Success), 204 No Content (Project not available)
     */
    @RequestMapping(value =RequestMappings.PROJECTS+"/{projId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProject(@PathVariable("projId") long projId) {
        logger.info("Retrieving and Deleting Project with Project Id " + projId);

        Project project = projectService.findByProjId(projId);
        if (project == null) {
            logger.error("Project id not present. Cannot delete Project with id {}" + projId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        projectService.deleteByProjId(projId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /*
     *  Add Bidding for Project -> localhost:8080/marketplace/bids, Method = POST
     *  Http Status: 200 OK (Success), 204 No Content(Not Valid Bid)
     */
    @RequestMapping(value = RequestMappings.BIDS, method = RequestMethod.POST)
    public ResponseEntity<?> addProjectBid(@RequestBody Bid bid) {
        logger.info("Validating and Storing the Lowest Bid");

        Project project = projectService.checkIfLowestBid(bid.getProjId(), bid.getBidAmt());

        if (project != null) {
            projectService.addBid(project, bid.getUserName(), bid.getBidAmt());
            logger.info("Updated the Lowest Bid Amt {} for user {} "
                    + bid.getBidAmt() + bid.getUserName());
            return new ResponseEntity<Project>(project,HttpStatus.OK);
        }
        logger.info("Amount entered {} is not the lowest Bid " + bid.getBidAmt());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /*
     *  Update Auto Bidding for Project -> localhost:8080/marketplace/autobid, Method = POST
     *  Http Status: 200 OK (Success), 204 (Not valid auto bid)
     */
    @RequestMapping(value = RequestMappings.AUTOBID, method = RequestMethod.POST)
    public ResponseEntity<?> updAutoBid(@RequestBody AutoBid autoBid) {
        logger.info("Creating Auto Bid for Project Id {} " + autoBid);
        Project project = projectService.findByProjId(autoBid.getProjId());
        if (project != null && autoBid.isAutoBidFlag()) {
            AutoBid lowestAutoBid = projectService.createAutoBid(autoBid);
            projectService.updateAutoBid(lowestAutoBid);
            return new ResponseEntity<Project>(project, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}