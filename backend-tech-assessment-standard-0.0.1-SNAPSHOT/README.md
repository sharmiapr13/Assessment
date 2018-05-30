# backend-tech-assessment

Skeleton project for Backend Technical Assessment.

Includes
--------
- Maven - [pom.xml](pom.xml)
- Application properties - [application.yml](src/main/resources/application.yml)
- Runnable Spring Boot Application - [BackendTechAssessmentApplication](src/main/java/com/intuit/cg/backendtechassessment/BackendTechAssessmentApplication.java)
- REST endpoints - [RequestMappings.java](src/main/java/com/intuit/cg/backendtechassessment/controller/requestmappings/RequestMappings.java)

Requirements
------------
See Backend Technical Assessment document for detailed requirements.

Assumptions
-----------
Bidding amount entered by buyer is always greater than current lowest Bidding amount
User is common entity and can be both buyer and seller
Username is the unique identifier for Buyer/Seller
Project Id is automatically incremented
Amount lesser than or equal to 0 is not considered valid bidding amount
Project is considered duplicate if the same user creates Project with same name while  the existing project is active
Auto Bid once created by a user, cannot be deleted but can be updated
Auto Bid Logic:
- Maintain the lowest 2 auto bid amount
- Lowest auto bid is always the amount equal to second lowest minus the range declared by lowest Auto Bid
- Few Validations are assumed to be performed before calling the service
- Handling the error scenario with appropriate HTTP Status code and avoided Custom error message for Response Body

Notes:
-------
- When Bidding is added, UI will send the /bids and /autobid
- Considered cassandra for maintaining the persistent data for history of bidding
but concentrated more on the specific requirements and so, dint maintain all the history of bidding
- Maintained the lowest Bidding amount and corresponding Buyer info
- Maintained the lowest Auto Bid amount and the corresponding Buyer info
- Maintain seperate RequestMapping for autobid to avoid any impact due to auto bid implementation
- Covered few flavours of Junits

Few Test Cases manually tested:
-------------------------------
- Create a Project/User
- Update/Delete of existing User
- Update/Delete of Invalid User
- Duplicate Project/User creation
- Duplicate Project after the old Project is inactive
- NULL Body for POST
- Bidding with lesser amount
- Bidding after the Max Bid Time is past
- Bidding amount more than the Max budget
- Auto Bid with same amount
- Auto Bid with same amount by same user
- Auto Bid with different amount by existing user
- Invalid Method

Submittion:
------------
Exercise Difficulty: Moderate
Feel about the exercise: 10
Feel about coding an exercise: 10
Change in the Exercise: Some utility to run the Basic Test cases and option to add custom test cases

Process is very impressive as it is more realistic way of evaluating.


