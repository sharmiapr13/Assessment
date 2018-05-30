package com.intuit.cg.backendtechassessment.controller;

public class RequestMappings {
    // Create, Update, Delete and GET Project details
    public static final String PROJECTS = "/projects";
    // Create, Update, Delete and GET User details
    public static final String SELLERS = "/sellers";
    public static final String BUYERS = "/buyers";

    // Add the Bid amount provided by seller
    public static final String BIDS = "/bids";

    // Base application URI
    public static final String MARKETPLACE = "/marketplace";

    //Add and the Auto Bid lowest amount
    public static final String AUTOBID = "/autobid";

    private RequestMappings() {
    }
}
