package p1.webap.model;

import java.sql.Date;

public class Employee 
{
	private int employeeId;
	private String username;
	private String password;
	private String fullName;
	private float totalReimbursment;
	private Date reimbursementResetDate;
	private int managerId;
	private int departmentHeadId;
	private int levelOfLeadership;
	
	public Employee() 
	{
		super();
	}

	public Employee(int employeeId, String username, String password, String fullName, float totalReimbursment,
			Date reimbursementResetDate, int managerId, int departmentHeadId, int levelOfLeadership) 
	{
		super();
		this.employeeId = employeeId;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.totalReimbursment = totalReimbursment;
		this.reimbursementResetDate = reimbursementResetDate;
		this.managerId = managerId;
		this.departmentHeadId = departmentHeadId;
		this.levelOfLeadership = levelOfLeadership;
	}

	public int getEmployeeId() 
	{
		return employeeId;
	}

	public void setEmployeeId(int employeeId) 
	{
		this.employeeId = employeeId;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getFullName() 
	{
		return fullName;
	}

	public void setFullName(String fullName) 
	{
		this.fullName = fullName;
	}

	public float getTotalReimbursment() 
	{
		return totalReimbursment;
	}

	public void setTotalReimbursment(float totalReimbursment) 
	{
		this.totalReimbursment = totalReimbursment;
	}

	public Date getReimbursementResetDate() 
	{
		return reimbursementResetDate;
	}

	public void setReimbursementResetDate(Date d) 
	{
		reimbursementResetDate = d;
	}

	public int getManagerId() 
	{
		return managerId;
	}

	public void setManagerId(int managerId) 
	{
		this.managerId = managerId;
	}

	public int getDepartmentHeadId() 
	{
		return departmentHeadId;
	}

	public void setDepartmentHeadId(int departmentHeadId) 
	{
		this.departmentHeadId = departmentHeadId;
	}

	public int getLevelOfLeadership() 
	{
		return levelOfLeadership;
	}
	
	public String getLeadershipRole()
	{
		switch(levelOfLeadership)
		{
			case 0:
			{
				return "Basic Employee";
			}
			case 1:
			{
				return "Supervisor";
			}
			case 2:
			{
				return "Department Head";
			}
			case 3:
			{
				return "Benefits Coordinator";
			}
			default:
			{
				return "<"+levelOfLeadership+"> Is not a valid Level of leadership.";
			}
		}
	}

	public void setLevelOfLeadership(int levelOfLeadership) 
	{
		this.levelOfLeadership = levelOfLeadership;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + departmentHeadId;
		result = prime * result + employeeId;
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + levelOfLeadership;
		result = prime * result + managerId;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((reimbursementResetDate == null) ? 0 : reimbursementResetDate.hashCode());
		result = prime * result + Float.floatToIntBits(totalReimbursment);
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Employee other = (Employee) obj;
		if (departmentHeadId != other.departmentHeadId)
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (fullName == null) 
		{
			if (other.fullName != null)
				return false;
		} 
		else if (!fullName.equals(other.fullName))
			return false;
		if (levelOfLeadership != other.levelOfLeadership)
			return false;
		if (managerId != other.managerId)
			return false;
		if (password == null) 
		{
			if (other.password != null)
				return false;
		} 
		else if (!password.equals(other.password))
			return false;
		if (reimbursementResetDate == null) 
		{
			if (other.reimbursementResetDate != null)
				return false;
		} 
		else if (!reimbursementResetDate.equals(other.reimbursementResetDate))
			return false;
		if (Float.floatToIntBits(totalReimbursment) != Float.floatToIntBits(other.totalReimbursment))
			return false;
		if (username == null) 
		{
			if (other.username != null)
				return false;
		} 
		else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Employee [employeeId=" + employeeId + ", username=" + username + ", password=" + password
				+ ", fullName=" + fullName + ", totalReimbursment=" + totalReimbursment + ", reimbursementResetDate="
				+ reimbursementResetDate + ", managerId=" + managerId + ", departmentHeadId=" + departmentHeadId
				+ ", levelOfLeadership=" + levelOfLeadership + "]";
	}

	
}
