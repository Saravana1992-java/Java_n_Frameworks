package com.learn.free.studios.gbs.model;

import java.util.HashMap;

public class Orfil {
	private String id;
	private String ApplNo;
	private String LoanNumber;
	private String InTime;
	private String OpsDisbursetime;
	private String AutoManual;
	private String IncomeNoIncome;
	private String CustomerName;
	private String DOB;
	private String CustomerMobile;
	private String CustomerProfile;
	private String CustomerProfileinDetail;
	private int age;
	private double LoanAmt;
	private double Tenure;
	private double Emi;
	private double IncomeLevel;
	private double CrifScore;
	private String HouseType;
	private double ActualLtv;
	private String CategoryVehicle;
	private String ApprovalType;
	private String state;
	private double distance;
	private String GuarantorAvail;
	private String FiResidenceOwnership;
	private String AppCreditApproverComments;
	private HashMap<Integer, Question> questionnaire;

	public String getApplNo() {
		return ApplNo;
	}

	public void setApplNo(String applNo) {
		ApplNo = applNo;
	}

	public String getLoanNumber() {
		return LoanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		LoanNumber = loanNumber;
	}

	public String getInTime() {
		return InTime;
	}

	public void setInTime(String inTime) {
		InTime = inTime;
	}

	public String getOpsDisbursetime() {
		return OpsDisbursetime;
	}

	public void setOpsDisbursetime(String opsDisbursetime) {
		OpsDisbursetime = opsDisbursetime;
	}

	public String getAutoManual() {
		return AutoManual;
	}

	public void setAutoManual(String autoManual) {
		AutoManual = autoManual;
	}

	public String getIncomeNoIncome() {
		return IncomeNoIncome;
	}

	public void setIncomeNoIncome(String incomeNoIncome) {
		IncomeNoIncome = incomeNoIncome;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getCustomerMobile() {
		return CustomerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		CustomerMobile = customerMobile;
	}

	public String getCustomerProfile() {
		return CustomerProfile;
	}

	public void setCustomerProfile(String customerProfile) {
		CustomerProfile = customerProfile;
	}

	public String getCustomerProfileinDetail() {
		return CustomerProfileinDetail;
	}

	public void setCustomerProfileinDetail(String customerProfileinDetail) {
		CustomerProfileinDetail = customerProfileinDetail;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getLoanAmt() {
		return LoanAmt;
	}

	public void setLoanAmt(double loanAmt) {
		LoanAmt = loanAmt;
	}

	public double getTenure() {
		return Tenure;
	}

	public void setTenure(double tenure) {
		Tenure = tenure;
	}

	public double getEmi() {
		return Emi;
	}

	public void setEmi(double emi) {
		Emi = emi;
	}

	public double getIncomeLevel() {
		return IncomeLevel;
	}

	public void setIncomeLevel(double incomeLevel) {
		IncomeLevel = incomeLevel;
	}

	public double getCrifScore() {
		return CrifScore;
	}

	public void setCrifScore(double crifScore) {
		CrifScore = crifScore;
	}

	public String getHouseType() {
		return HouseType;
	}

	public void setHouseType(String houseType) {
		HouseType = houseType;
	}

	public double getActualLtv() {
		return ActualLtv;
	}

	public void setActualLtv(double actualLtv) {
		ActualLtv = actualLtv;
	}

	public String getCategoryVehicle() {
		return CategoryVehicle;
	}

	public void setCategoryVehicle(String categoryVehicle) {
		CategoryVehicle = categoryVehicle;
	}

	public String getApprovalType() {
		return ApprovalType;
	}

	public void setApprovalType(String approvalType) {
		ApprovalType = approvalType;
	}

	public HashMap<Integer, Question> getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(HashMap<Integer, Question> questionnaire) {
		this.questionnaire = questionnaire;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGuarantorAvail() {
		return GuarantorAvail;
	}

	public void setGuarantorAvail(String guarantorAvail) {
		GuarantorAvail = guarantorAvail;
	}

	public String getFiResidenceOwnership() {
		return FiResidenceOwnership;
	}

	public void setFiResidenceOwnership(String fiResidenceOwnership) {
		FiResidenceOwnership = fiResidenceOwnership;
	}

	public String getAppCreditApproverComments() {
		return AppCreditApproverComments;
	}

	public void setAppCreditApproverComments(String appCreditApproverComments) {
		AppCreditApproverComments = appCreditApproverComments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ApplNo == null) ? 0 : ApplNo.hashCode());
		result = prime * result + ((CustomerMobile == null) ? 0 : CustomerMobile.hashCode());
		result = prime * result + ((CustomerName == null) ? 0 : CustomerName.hashCode());
		result = prime * result + ((DOB == null) ? 0 : DOB.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orfil other = (Orfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ApplNo == null) {
			if (other.ApplNo != null)
				return false;
		} else if (!ApplNo.equals(other.ApplNo))
			return false;
		if (CustomerMobile == null) {
			if (other.CustomerMobile != null)
				return false;
		} else if (!CustomerMobile.equals(other.CustomerMobile))
			return false;
		if (CustomerName == null) {
			if (other.CustomerName != null)
				return false;
		} else if (!CustomerName.equals(other.CustomerName))
			return false;
		if (DOB == null) {
			if (other.DOB != null)
				return false;
		} else if (!DOB.equals(other.DOB))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Orfil [id=" + id + ", ApplNo=" + ApplNo + ", LoanNumber=" + LoanNumber + ", InTime=" + InTime
				+ ", OpsDisbursetime=" + OpsDisbursetime + ", AutoManual=" + AutoManual + ", IncomeNoIncome="
				+ IncomeNoIncome + ", CustomerName=" + CustomerName + ", DOB=" + DOB + ", CustomerMobile="
				+ CustomerMobile + ", CustomerProfile=" + CustomerProfile + ", CustomerProfileinDetail="
				+ CustomerProfileinDetail + ", age=" + age + ", LoanAmt=" + LoanAmt + ", Tenure=" + Tenure + ", Emi="
				+ Emi + ", IncomeLevel=" + IncomeLevel + ", CrifScore=" + CrifScore + ", HouseType=" + HouseType
				+ ", ActualLtv=" + ActualLtv + ", CategoryVehicle=" + CategoryVehicle + ", ApprovalType=" + ApprovalType
				+ ", state=" + state + ", distance=" + distance + ", GuarantorAvail=" + GuarantorAvail
				+ ", FiResidenceOwnership=" + FiResidenceOwnership + ", AppCreditApproverComments="
				+ AppCreditApproverComments + ", questionnaire=" + questionnaire + "]";
	}
	
}
