package com.intuit.cg.backendtechassessment.Entity;

public class AutoBid {
    private boolean autoBidFlag;
    private long projId;
    private String userName;
    private double lowestBidAmt;
    private long reduceRange;
    private double secondLowBidAmt;

    public AutoBid() {
    }

    public AutoBid(long projId, String userName
            , double lowestBidAmt, long reduceRange, boolean autoBidFlag) {
       this.lowestBidAmt = lowestBidAmt;
       this.reduceRange = reduceRange;
       this.userName = userName;
       this.projId = projId;
       this.autoBidFlag = autoBidFlag;
    }

    public boolean isAutoBidFlag() {
        return autoBidFlag;
    }

    public void setAutoBidFlag(boolean autoBidFlag) {
        this.autoBidFlag = autoBidFlag;
    }

    public void setProjId(long projId) {
        this.projId = projId;
    }

    public long getProjId() {
        return projId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLowestBidAmt(double lowestBidAmt) {
        this.lowestBidAmt = lowestBidAmt;
    }

    public double getLowestBidAmt() {
        return lowestBidAmt;
    }

    public long getReduceRange() {
        return reduceRange;
    }

    public void setReduceRange(long reduceRange) {
        this.reduceRange = reduceRange;
    }

    public double getSecondLowBidAmt() {
        return secondLowBidAmt;
    }

    public void setSecondLowBidAmt(double secondLowBidAmt) {
        this.secondLowBidAmt = secondLowBidAmt;
    }

    @Override
    public int hashCode() {
        final int prime=31;
        int result = 1;
        result = prime * result + (int) (Integer.parseInt(userName) ^ (Integer.parseInt(userName) >> 32));
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
        AutoBid autoBid = (AutoBid) object;
        if ( userName != autoBid.userName)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Auto Bidding Details [ User Name: " + userName
                + ", Lowest Bidding Amount: " +lowestBidAmt
                + ", Reduce Range: " + reduceRange
                + ", Second Lowest Bid Amt: " + secondLowBidAmt
                + ", Project Id: " + projId
                + ", Audit Bid Flag:" + autoBidFlag + " ]";
    }
}
