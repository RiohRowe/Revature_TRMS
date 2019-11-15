package p1.webap.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;

public class Request 
{
	private int requestId;
	private Date creationDate;
	private String cDate;
	private Time creationTime;
	private String eventDescription;
	private String workJustification;
	private float projected_reimbursement;
	private float reimbursement;
	private boolean isUrgent;
	private Boolean isApproved;
	private String denialReason;
	private String refundIncreaseJustification;
	private int employeeId;
	private Event event;
	private OptionalRequestInfo oRI;
	private ApprovalChain[] approvalChain;
	private AdditionalInfo[] additionalInfo;
	
	public Request() 
	{
		super();
	}

	public Request(int requestId, Date creationDate, Time creationTime, String eventDescription,
			String workJustification, float projected_reimbursement, float reimbursement, boolean isUrgent,
			boolean isApproved, String denialReason, String refundIncreaseJustification, int employeeId, Event event,
			OptionalRequestInfo oRI, ApprovalChain[] approvalChain, AdditionalInfo[] additionalInfo) 
	{
		super();
		this.requestId = requestId;
		this.creationDate = creationDate;
		cDate = creationDate.toString();
		this.creationTime = creationTime;
		this.eventDescription = eventDescription;
		this.workJustification = workJustification;
		this.projected_reimbursement = projected_reimbursement;
		this.reimbursement = reimbursement;
		this.isUrgent = isUrgent;
		this.isApproved = isApproved;
		this.denialReason = denialReason;
		this.refundIncreaseJustification = refundIncreaseJustification;
		this.employeeId = employeeId;
		this.event = event;
		this.oRI = oRI;
		this.approvalChain = approvalChain;
		this.additionalInfo = additionalInfo;
	}
	
	public Request(int requestId, Date creationDate, Time creationTime, String eventDescription,
			String workJustification, float projected_reimbursement, float reimbursement, Boolean isUrgent,
			Boolean isApproved, String denialReason, String refundIncreaseJustification, int employeeId) 
	{
		super();
		this.requestId = requestId;
		this.creationDate = creationDate;
		cDate = creationDate.toString();
		this.creationTime = creationTime;
		this.eventDescription = eventDescription;
		this.workJustification = workJustification;
		this.projected_reimbursement = projected_reimbursement;
		this.reimbursement = reimbursement;
		this.isUrgent = isUrgent;
		this.isApproved = isApproved;
		this.denialReason = denialReason;
		this.refundIncreaseJustification = refundIncreaseJustification;
		this.employeeId = employeeId;
		this.event = null;
		this.oRI = null;
		this.approvalChain = null;
		this.additionalInfo = null;
	}

	public int getRequestId() 
	{
		return requestId;
	}

	public void setRequestId(int requestId) 
	{
		this.requestId = requestId;
	}

	public Date getCreationDate() 
	{
		return creationDate;
	}

	public void setCreationDate(Date d) 
	{
		creationDate = d;
		cDate = creationDate.toString();
	}

	public Time getCreationTime() 
	{
		return creationTime;
	}

	public void setCreationTime(Time creationTime) 
	{
		this.creationTime = creationTime;
	}

	public String getEventDescription() 
	{
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) 
	{
		this.eventDescription = eventDescription;
	}

	public String getWorkJustification() 
	{
		return workJustification;
	}

	public void setWorkJustification(String workJustification) 
	{
		this.workJustification = workJustification;
	}

	public float getProjected_reimbursement() 
	{
		return projected_reimbursement;
	}

	public void setProjected_reimbursement(float projected_reimbursement) 
	{
		this.projected_reimbursement = projected_reimbursement;
	}

	public float getReimbursement() 
	{
		return reimbursement;
	}

	public void setReimbursement(float reimbursement) 
	{
		this.reimbursement = reimbursement;
	}

	public boolean isUrgent() 
	{
		return isUrgent;
	}

	public void setUrgent(boolean isUrgent) 
	{
		this.isUrgent = isUrgent;
	}

	public Boolean isApproved() 
	{
		return isApproved;
	}

	public void setApproved(boolean isApproved) 
	{
		this.isApproved = isApproved;
	}

	public String getDenialReason() 
	{
		return denialReason;
	}

	public void setDenialReason(String denialReason) 
	{
		this.denialReason = denialReason;
	}

	public String getRefundIncreaseJustification() 
	{
		return refundIncreaseJustification;
	}

	public void setRefundIncreaseJustification(String refundIncreaseJustification) 
	{
		this.refundIncreaseJustification = refundIncreaseJustification;
	}

	public int getEmployeeId() 
	{
		return employeeId;
	}

	public void setEmployeeId(int employeeId) 
	{
		this.employeeId = employeeId;
	}

	public Event getEvent() 
	{
		return event;
	}

	public void setEvent(Event event) 
	{
		this.event = event;
	}

	public OptionalRequestInfo getoRI() 
	{
		return oRI;
	}

	public void setoRI(OptionalRequestInfo oRI) 
	{
		this.oRI = oRI;
	}

	public ApprovalChain[] getApprovalChain() 
	{
		return approvalChain;
	}

	public void setApprovalChain(ApprovalChain[] approvalChain) 
	{
		this.approvalChain = approvalChain;
	}

	public AdditionalInfo[] getAdditionalInfo() 
	{
		return additionalInfo;
	}

	public void setAdditionalInfo(AdditionalInfo[] additionalInfo) 
	{
		this.additionalInfo = additionalInfo;
	}
	public ApprovalChain getTopApprovalChain()
	{
		ApprovalChain apch = null;
		int maxweight = -1;
		for(ApprovalChain ac : approvalChain)
		{
			if(ac.getWeight()> maxweight)
			{
				apch = ac;
				maxweight = ac.getWeight();
			}
		}
		return apch;
	}
	
	public String getcDate() {
		return cDate;
	}

	public ApprovalChain getbottomApprovalChain()
	{
		ApprovalChain apch = null;
		int minweight = 3;
		for(ApprovalChain ac : approvalChain)
		{
			if(ac.getWeight()< minweight)
			{
				apch = ac;
				minweight = ac.getWeight();
			}
		}
		return apch;
	}
	
	public ApprovalChain getApprovalChainByEmployeeID(int eID)
	{
		ApprovalChain apch = null;
		for(ApprovalChain ac : approvalChain)
		{
			if(ac.getEmployee_id() == eID)
			{
				apch = ac;
			}
		}
		return apch;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(additionalInfo);
		result = prime * result + Arrays.hashCode(approvalChain);
		result = prime * result + ((cDate == null) ? 0 : cDate.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
		result = prime * result + ((denialReason == null) ? 0 : denialReason.hashCode());
		result = prime * result + employeeId;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((eventDescription == null) ? 0 : eventDescription.hashCode());
		result = prime * result + ((isApproved == null) ? 0 : isApproved.hashCode());
		result = prime * result + (isUrgent ? 1231 : 1237);
		result = prime * result + ((oRI == null) ? 0 : oRI.hashCode());
		result = prime * result + Float.floatToIntBits(projected_reimbursement);
		result = prime * result + ((refundIncreaseJustification == null) ? 0 : refundIncreaseJustification.hashCode());
		result = prime * result + Float.floatToIntBits(reimbursement);
		result = prime * result + requestId;
		result = prime * result + ((workJustification == null) ? 0 : workJustification.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (!Arrays.equals(additionalInfo, other.additionalInfo))
			return false;
		if (!Arrays.equals(approvalChain, other.approvalChain))
			return false;
		if (cDate == null) 
		{
			if (other.cDate != null)
				return false;
		} 
		else if (!cDate.equals(other.cDate))
			return false;
		if (creationDate == null) 
		{
			if (other.creationDate != null)
				return false;
		} 
		else if (!creationDate.equals(other.creationDate))
			return false;
		if (creationTime == null) 
		{
			if (other.creationTime != null)
				return false;
		} 
		else if (!creationTime.equals(other.creationTime))
			return false;
		if (denialReason == null) 
		{
			if (other.denialReason != null)
				return false;
		} 
		else if (!denialReason.equals(other.denialReason))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (event == null) 
		{
			if (other.event != null)
				return false;
		} 
		else if (!event.equals(other.event))
			return false;
		if (eventDescription == null) 
		{
			if (other.eventDescription != null)
				return false;
		} 
		else if (!eventDescription.equals(other.eventDescription))
			return false;
		if (isApproved == null) 
		{
			if (other.isApproved != null)
				return false;
		} 
		else if (!isApproved.equals(other.isApproved))
			return false;
		if (isUrgent != other.isUrgent)
			return false;
		if (oRI == null) 
		{
			if (other.oRI != null)
				return false;
		} 
		else if (!oRI.equals(other.oRI))
			return false;
		if (Float.floatToIntBits(projected_reimbursement) != Float.floatToIntBits(other.projected_reimbursement))
			return false;
		if (refundIncreaseJustification == null) 
		{
			if (other.refundIncreaseJustification != null)
				return false;
		} 
		else if (!refundIncreaseJustification.equals(other.refundIncreaseJustification))
			return false;
		if (Float.floatToIntBits(reimbursement) != Float.floatToIntBits(other.reimbursement))
			return false;
		if (requestId != other.requestId)
			return false;
		if (workJustification == null) 
		{
			if (other.workJustification != null)
				return false;
		} 
		else if (!workJustification.equals(other.workJustification))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Request [requestId=" + requestId + ", creationDate=" + creationDate + ", cDate=" + cDate
				+ ", creationTime=" + creationTime + ", eventDescription=" + eventDescription + ", workJustification="
				+ workJustification + ", projected_reimbursement=" + projected_reimbursement + ", reimbursement="
				+ reimbursement + ", isUrgent=" + isUrgent + ", isApproved=" + isApproved + ", denialReason="
				+ denialReason + ", refundIncreaseJustification=" + refundIncreaseJustification + ", employeeId="
				+ employeeId + ", event=" + event + ", oRI=" + oRI + ", approvalChain=" + Arrays.toString(approvalChain)
				+ ", additionalInfo=" + Arrays.toString(additionalInfo) + "]";
	}

	
}
