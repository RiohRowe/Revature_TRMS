package p1.webap.model;

public class OptionalRequestInfo 
{
	private int requestId;
	private String eventRelatedFile;
	private String preapprovalFile;
	private int preapprovalLevel;
	
	
	public OptionalRequestInfo() 
	{
		super();
	}
	
	public OptionalRequestInfo(int requestId, String eventRelatedFile, String preapprovalFile, int preapprovalLevel) 
	{
		super();
		this.requestId = requestId;
		this.eventRelatedFile = eventRelatedFile;
		this.preapprovalFile = preapprovalFile;
		this.preapprovalLevel = preapprovalLevel;
	}	
	
	
	public int getRequestId() 
	{
		return requestId;
	}
	public void setRequestId(int requestId) 
	{
		this.requestId = requestId;
	}
	public String getEventRelatedFile() 
	{
		return eventRelatedFile;
	}
	public void setEventRelatedFile(String eventRelatedFile) 
	{
		this.eventRelatedFile = eventRelatedFile;
	}
	public String getPreapprovalFile() 
	{
		return preapprovalFile;
	}
	public void setPreapprovalFile(String preapprovalFile) 
	{
		this.preapprovalFile = preapprovalFile;
	}
	public int getPreapprovalLevel() 
	{
		return preapprovalLevel;
	}
	public void setPreapprovalLevel(int preapprovalLevel) 
	{
		this.preapprovalLevel = preapprovalLevel;
	}

	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventRelatedFile == null) ? 0 : eventRelatedFile.hashCode());
		result = prime * result + ((preapprovalFile == null) ? 0 : preapprovalFile.hashCode());
		result = prime * result + preapprovalLevel;
		result = prime * result + requestId;
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
		OptionalRequestInfo other = (OptionalRequestInfo) obj;
		if (eventRelatedFile == null) 
		{
			if (other.eventRelatedFile != null)
				return false;
		}
		else if (!eventRelatedFile.equals(other.eventRelatedFile))
			return false;
		if (preapprovalFile == null) 
		{
			if (other.preapprovalFile != null)
				return false;
		} 
		else if (!preapprovalFile.equals(other.preapprovalFile))
			return false;
		if (preapprovalLevel != other.preapprovalLevel)
			return false;
		if (requestId != other.requestId)
			return false;
		return true;
	}
	
	@Override
	public String toString() 
	{
		return "optionalRequestInfo [requestId=" + requestId + ", eventRelatedFile=" + eventRelatedFile
				+ ", preapprovalFile=" + preapprovalFile + ", preapprovalLevel=" + preapprovalLevel + "]";
	}	
}
