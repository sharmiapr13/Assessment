package com.intuit.cg.backendtechassessment;

import com.intuit.cg.backendtechassessment.Entity.Project;
import com.intuit.cg.backendtechassessment.Service.ProjectService;
import com.intuit.cg.backendtechassessment.controller.ProjectController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.OffsetDateTime;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProjectController.class, secure = false)
public class BackendTechAssessmentApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    public void getProjectTest() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String date = "2018-05-30T10:15:30+01:00";
        Project project = new Project(1, "test", "testDesc"
                , "testuser", 150
                , OffsetDateTime.parse(date, dateTimeFormatter));
        Project tempProject = new Project(1, "test", "testDesc"
                , "testuser", 150, OffsetDateTime.now());

        Mockito.when(projectService.findByProjId(1)).thenReturn(project);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/marketplace/projects/1")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println("output" +result.getResponse().getContentAsString());

        String expectedOutput = "{\"projId\":1,\"projName\":\"test\",\"projDesc\":\"testDesc\",\"sellerId\":\"testuser\",\"buyerId\":null,\"maxBudget\":150.0,\"maxBidDateTime\":\"2018-05-30T10:15:30+01:00\",\"lowestBidAmt\":0.0,\"lowestAutoBidAmt\":0.0,\"autoBidUser\":null}";

        JSONAssert.assertEquals(expectedOutput, result.getResponse().getContentAsString(), false);
    }

    @Autowired
    private ProjectController controller;
    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getAllProjectsTest() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String date = "2018-05-30T10:15:30+01:00";
        List<Project> projectList = new ArrayList<Project>();
        Project project = new Project(1, "test", "testDesc"
                , "testuser", 150
                , OffsetDateTime.parse(date, dateTimeFormatter));
        Project tempProject = new Project(1, "test", "testDesc"
                , "testuser", 150
                ,OffsetDateTime.parse(date, dateTimeFormatter));

        projectList.add(project);
        projectList.add(tempProject);

        Mockito.when(projectService.getAllProjects()).thenReturn(projectList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/marketplace/projects")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println("output" +result.getResponse().getContentAsString());

        String expectedOutput = "[{\"projId\":1,\"projName\":\"test\",\"projDesc\":\"testDesc\",\"sellerId\":\"testuser\",\"buyerId\":null,\"maxBudget\":150.0,\"maxBidDateTime\":\"2018-05-30T10:15:30+01:00\",\"lowestBidAmt\":0.0,\"lowestAutoBidAmt\":0.0,\"autoBidUser\":null},{\"projId\":1,\"projName\":\"test\",\"projDesc\":\"testDesc\",\"sellerId\":\"testuser\",\"buyerId\":null,\"maxBudget\":150.0,\"maxBidDateTime\":\"2018-05-30T10:15:30+01:00\",\"lowestBidAmt\":0.0,\"lowestAutoBidAmt\":0.0,\"autoBidUser\":null}]";

        JSONAssert.assertEquals(expectedOutput, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createProjectTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .request(HttpMethod.POST, "/marketplace/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"projId\":1,\"projName\":\"test\",\"projDesc\":\"testDesc\",\"sellerId\":\"testuser\",\"buyerId\":null,\"maxBudget\":150.0,\"maxBidDateTime\":\"2018-05-30T10:15:30+01:00\",\"lowestBidAmt\":0.0,\"lowestAutoBidAmt\":0.0,\"autoBidUser\":null}");

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void invalidMethodTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .request(HttpMethod.GET, "/marketplace/autobid")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    public void noContentTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .request(HttpMethod.DELETE, "/marketplace/projects/1");

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
