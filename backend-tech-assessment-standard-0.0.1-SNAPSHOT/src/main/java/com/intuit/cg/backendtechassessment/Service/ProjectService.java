package com.intuit.cg.backendtechassessment.Service;

import com.intuit.cg.backendtechassessment.Entity.AutoBid;
import com.intuit.cg.backendtechassessment.Entity.Project;
import java.util.List;

public interface ProjectService {
    Project findByProjId(long projId);
    List<Project> findByProjName(String projName);
    void createProject(Project project);
    void updateProject(Project project);
    void deleteByProjId(long projId);
    List<Project> getAllProjects();
    boolean isProjectExists(Project project);

    void addBid(Project project, String userName, double bidAmt);
    Project checkIfLowestBid( long projId, double bidAmt);
    AutoBid findAutoBidById(long projId);
    boolean checkIfLowestAutoBid(AutoBid autoBid, double lowestAmt);
    boolean checkIfSecondLowestAutoBid(AutoBid autoBid, double secondLowestAmt);
    boolean ifEqualToLowestAutoBid(AutoBid autoBid, double lowestAmt);
    AutoBid createAutoBid(AutoBid autoBid);
    void updateAutoBid(AutoBid autoBid);
}

