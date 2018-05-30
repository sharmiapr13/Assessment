package com.intuit.cg.backendtechassessment.Entity;

import java.time.OffsetDateTime;

public class Project {
    private long projId;
    private String projName;
    private String projDesc;
    private String sellerId;
    private String buyerId;
    private double maxBudget;
    private OffsetDateTime maxBidDateTime;
    private double lowestBidAmt;
    private double lowestAutoBidAmt;
    private String autoBidUser;

    public Project() {
        projId = 0;
    }

    public Project(long projId, String projName, String projDesc
            , String sellerId, long maxBudget, OffsetDateTime maxBidDateTime) {
        this.projId = projId;
        this.projName = projName;
        this.projDesc = projDesc;
        this.sellerId = sellerId;
        this.maxBidDateTime = maxBidDateTime;
        this.maxBudget = maxBudget;
    }

    public double getLowestAutoBidAmt() {
        return lowestAutoBidAmt;
    }

    public String getAutoBidUser() {
        return autoBidUser;
    }

    public void setLowestAutoBidAmt(double lowestAutoBidAmt) {
        this.lowestAutoBidAmt = lowestAutoBidAmt;
    }

    public void setAutoBidUser(String autoBidUser) {
        this.autoBidUser = autoBidUser;
    }

    public long getProjId() {
        return projId;
    }

    public void setProjId(long projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjDesc() {
        return projDesc;
    }

    public void setProjDesc(String projDesc) {
        this.projDesc = projDesc;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(double maxBudget) {
        this.maxBudget = maxBudget;
    }

    public OffsetDateTime getMaxBidDateTime() {
        return maxBidDateTime;
    }

    public void setMaxBidDateTime(OffsetDateTime maxBidDateTime) {
        this.maxBidDateTime = maxBidDateTime;
    }

    public double getLowestBidAmt() {
        return lowestBidAmt;
    }

    public void setLowestBidAmt(double lowestBidAmt) { this.lowestBidAmt = lowestBidAmt; }

    public String getBuyerId() { return buyerId; }

    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }

    @Override
    public int hashCode() {
        final int prime=31;
        int result = 1;
        result = prime * result + (int) (projId ^ (projId >> 32));
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        Project proj = (Project) object;
        if (projId != proj.projId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Project [ Id: " + projId + ", Name: " +projName
                + ", Desc: " + projDesc + ", Seller: " +sellerId
                + ", Max Budget: " +maxBudget + ", Bid Until: " +maxBidDateTime
                + ", Lowest Bid Amt: " +lowestBidAmt + ", Buyer: " +buyerId +" ]";
    }
}

