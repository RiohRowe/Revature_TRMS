package p1.webap.model;

import java.sql.Timestamp;

public class ApprovalChain 
{
	private int request_id;
	private int employee_id;
	private int weight;
	private Boolean approved;
	private Timestamp timeoutTime;
	private String toTime;
	private boolean hasTimedOut;

	public ApprovalChain() 
	{
		super();
	}
	
	public ApprovalChain(int request_id, int employee_id, int weight, Boolean approved, Timestamp timeoutTime,
			boolean hasTimedOut) 
	{
		super();
		this.request_id = request_id;
		this.employee_id = employee_id;
		this.weight = weight;
		this.approved = approved;
		this.timeoutTime = timeoutTime;
		toTime = timeoutTime.toString();
		this.hasTimedOut = hasTimedOut;
	}

	public int getRequest_id() 
	{
		return request_id;
	}
	public void setRequest_id(int request_id) 
	{
		this.request_id = request_id;
	}
	public int getEmployee_id() 
	{
		return employee_id;
	}
	public void setEmployee_id(int employee_id) 
	{
		this.employee_id = employee_id;
	}
	public int getWeight() 
	{
		return weight;
	}
	public void setWeight(int weight) 
	{
		this.weight = weight;
	}
	public Boolean isApproved() 
	{
		return approved;
	}
	public void setApproved(boolean approved) 
	{
		this.approved = approved;
	}
	public boolean isHasTimedOut() 
	{
		return hasTimedOut;
	}
	public void setHasTimedOut(boolean hasTimedOut) 
	{
		this.hasTimedOut = hasTimedOut;
	}
	public Timestamp getTimeoutTime() 
	{
		return timeoutTime;
	}

	public String getToTime() 
	{
		return toTime;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approved == null) ? 0 : approved.hashCode());
		result = prime * result + employee_id;
		result = prime * result + (hasTimedOut ? 1231 : 1237);
		result = prime * result + request_id;
		result = prime * result + ((timeoutTime == null) ? 0 : timeoutTime.hashCode());
		result = prime * result + ((toTime == null) ? 0 : toTime.hashCode());
		result = prime * result + weight;
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
		ApprovalChain other = (ApprovalChain) obj;
		if (approved == null) 
		{
			if (other.approved != null)
				return false;
		} else if (!approved.equals(other.approved))
			return false;
		if (employee_id != other.employee_id)
			return false;
		if (hasTimedOut != other.hasTimedOut)
			return false;
		if (request_id != other.request_id)
			return false;
		if (timeoutTime == null) 
		{
			if (other.timeoutTime != null)
				return false;
		} 
		else if (!timeoutTime.equals(other.timeoutTime))
			return false;
		if (toTime == null) 
		{
			if (other.toTime != null)
				return false;
		} 
		else if (!toTime.equals(other.toTime))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "ApprovalChain [request_id=" + request_id + ", employee_id=" + employee_id + ", weight=" + weight
				+ ", approved=" + approved + ", timeoutTime=" + timeoutTime + ", toTime=" + toTime + ", hasTimedOut="
				+ hasTimedOut + "]";
	}


	
}
