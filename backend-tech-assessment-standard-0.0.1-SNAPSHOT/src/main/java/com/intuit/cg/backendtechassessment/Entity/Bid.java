package com.intuit.cg.backendtechassessment.Entity;

public class Bid {
    private long projId;
    private String userName;
    private double bidAmt;

    public Bid () {
    }

    public Bid(long projId, String userName, double bidAmt) {
        this.bidAmt = bidAmt;
        this.projId = projId;
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public double getBidAmt() {
        return bidAmt;
    }

    public long getProjId() {
        return projId;
    }

    public void setBidAmt(double bidAmt) {
        this.bidAmt = bidAmt;
    }

    public void setProjId(long projId) {
        this.projId = projId;
    }

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
        Bid bid = (Bid) object;
        if (projId != bid.projId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Bidding Details [ Id: " + projId + ", UserName: " +userName
                + ", Bid Amount: " + bidAmt + " ]";
    }
}
