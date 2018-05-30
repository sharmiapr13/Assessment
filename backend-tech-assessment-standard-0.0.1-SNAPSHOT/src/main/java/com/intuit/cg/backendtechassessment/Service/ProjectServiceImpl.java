package com.intuit.cg.backendtechassessment.Service;

import com.intuit.cg.backendtechassessment.Entity.AutoBid;
import com.intuit.cg.backendtechassessment.Entity.Project;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    private static final AtomicLong counter = new AtomicLong();
    private List<Project> projects = new ArrayList<Project>();
    public HashMap<Long, AutoBid> autoBidHashMap = new HashMap<Long, AutoBid>();

    /* Returns the Project details based on project Id */
    public Project findByProjId(long projId) {
        for (Project project : projects) {
            if (project.getProjId() == projId) {
                return project;
            }
        }
        return null;
    }

    /* Return the Project details based on project Name */
    public List<Project> findByProjName(String projName) {
        List<Project> tempProjects = null;
        if (projects != null) {
            tempProjects = new ArrayList<Project>();
            for (Project project : projects) {
                if (project.getProjName().equalsIgnoreCase(projName)) {
                    tempProjects.add(project);
                }
            }
        }
        return tempProjects;
    }

    /* Create the project */
    public void createProject(Project project) {
        project.setProjId(counter.incrementAndGet());
        projects.add(project);
    }

    /* Update the Project */
    public void updateProject(Project project) {
        int index = projects.indexOf(project);
        projects.set(index, project);
    }

    /* Delete the project based on project id */
    public void deleteByProjId(long projId) {
        for (Iterator<Project> iterator = projects.iterator(); ((Iterator) iterator).hasNext(); ) {
            Project project = (Project) ((Iterator) iterator).next();
            if (project.getProjId() == projId) {
                iterator.remove();
            }
        }
    }

    /* Get all project details */
    public List<Project> getAllProjects() {
        return projects;
    }

    /* Check if the project exists */
    public boolean isProjectExists(Project project) {
        List<Project> tempProjects = findByProjName(project.getProjName());
        if (tempProjects == null) {
            return false;
        }
        for (Project proj : tempProjects) {
            if (proj.getSellerId().equalsIgnoreCase(project.getSellerId())
                    && proj.getMaxBidDateTime().isEqual(project.getMaxBidDateTime())) {
                return true;
            }
        }
        return false;
    }

    /* Add the Bid provided by the user */
    public void addBid(Project project, String userName, double bidAmt) {
        project.setLowestBidAmt(bidAmt);
        project.setBuyerId(userName);

        int index = projects.indexOf(project);
        projects.set(index, project);
    }

    /* Check Bidding amount is lowest */
    public Project checkIfLowestBid(long projId, double bidAmt) {
        for (Project project : projects) {
            if (project.getProjId() == projId
                    && (project.getLowestBidAmt() > bidAmt
                    || project.getLowestBidAmt() <= 0)
                    && project.getMaxBudget() >= bidAmt
                    && project.getMaxBidDateTime().isAfter(OffsetDateTime.now())) {
                return project;
            }
        }
        return null;
    }

    /* Get the Auto Bid details for the project */
    public AutoBid findAutoBidById(long projId) {
        if (autoBidHashMap.containsKey(projId))
            return autoBidHashMap.get(projId);
        return null;
    }

    /* Create the Auto Bid */
    public AutoBid createAutoBid (AutoBid autoBid) {
        AutoBid currentAutoBid = findAutoBidById(autoBid.getProjId());
        if (currentAutoBid == null) {
            autoBidHashMap.put(autoBid.getProjId(), autoBid);
            return autoBid;
        }
        if (checkIfLowestAutoBid(autoBid, currentAutoBid.getLowestBidAmt())) {
            if (!autoBid.getUserName().equalsIgnoreCase(currentAutoBid.getUserName())) {
                currentAutoBid.setSecondLowBidAmt(currentAutoBid.getLowestBidAmt());
            }
            currentAutoBid.setUserName(autoBid.getUserName());
            currentAutoBid.setReduceRange(autoBid.getReduceRange());
            currentAutoBid.setLowestBidAmt(autoBid.getLowestBidAmt());
        }
        else if (checkIfSecondLowestAutoBid(autoBid, currentAutoBid.getSecondLowBidAmt())
                || ifEqualToLowestAutoBid(autoBid, currentAutoBid.getLowestBidAmt())) {
            if (!autoBid.getUserName().equalsIgnoreCase(currentAutoBid.getUserName())) {
                currentAutoBid.setSecondLowBidAmt(autoBid.getLowestBidAmt());
            }
        }
        autoBidHashMap.replace(autoBid.getProjId(), currentAutoBid);
        return currentAutoBid;
    }

    /* Check if the Auto Bid provided is lowest */
    public boolean checkIfLowestAutoBid(AutoBid autoBid, double lowestAmt) {
        if (autoBid.getLowestBidAmt() < lowestAmt) {
            return true;
        }
        return false;
    }

    /* Check if auto bid amount is lowest */
    public boolean checkIfSecondLowestAutoBid(AutoBid autoBid, double secondLowestAmt) {
        if (autoBid.getLowestBidAmt() < secondLowestAmt) {
            return true;
        }
        return false;
    }

    /* Check if Bidding amount is same as existing lowest auto bid */
    public boolean ifEqualToLowestAutoBid(AutoBid autoBid, double lowestAmt) {
        if (autoBid.getLowestBidAmt() == lowestAmt) {
            return true;
        }
        return false;
    }

    /* Update the Auto Bid */
    public void updateAutoBid(AutoBid autoBid) {
        Project project = findByProjId(autoBid.getProjId());
        double lowestAutoBid;
        if (autoBid.getSecondLowBidAmt() > 0) {
            lowestAutoBid = autoBid.getSecondLowBidAmt() - autoBid.getReduceRange();
            if (lowestAutoBid < autoBid.getLowestBidAmt())
            {
                lowestAutoBid = autoBid.getLowestBidAmt();
            }
        }
        else {
            if (project.getLowestBidAmt() > 0) {
                lowestAutoBid = project.getLowestBidAmt() - autoBid.getReduceRange();
            }
            else {
                lowestAutoBid = project.getMaxBudget() - autoBid.getReduceRange();
            }
        }
        project.setLowestAutoBidAmt(lowestAutoBid);
        project.setAutoBidUser(autoBid.getUserName());

        int index = projects.indexOf(project);
        projects.set(index, project);
    }
}
