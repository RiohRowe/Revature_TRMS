package p1.webap.model;

public class AdditionalInfo 
{
	private int additionalInfoId;
	private String text_request;
	private String text_info;
	private String file_info;
	private boolean isanswered;
	private int requestId;
	private int senderId;
	private int recipientId;
	
	public AdditionalInfo() 
	{
		super();
	}

	public AdditionalInfo(int additionalInfoId, String text_request, int requestId, int senderId, int recipientId) 
	{
		super();
		this.additionalInfoId = additionalInfoId;
		this.text_request = text_request;
		this.text_info = null;
		this.file_info = null;
		this.isanswered = false;
		this.requestId = requestId;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}
	
	

	public AdditionalInfo(int additionalInfoId, String text_request, String text_info, String file_info,
			boolean isanswered, int requestId, int senderId, int recipientId) 
	{
		super();
		this.additionalInfoId = additionalInfoId;
		this.text_request = text_request;
		this.text_info = text_info;
		this.file_info = file_info;
		this.isanswered = isanswered;
		this.requestId = requestId;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}

	public int getAdditionalInfoId() 
	{
		return additionalInfoId;
	}

	public void setAdditionalInfoId(int additionalInfoId) 
	{
		this.additionalInfoId = additionalInfoId;
	}

	public String getText_request() 
	{
		return text_request;
	}

	public void setText_request(String text_request) 
	{
		this.text_request = text_request;
	}

	public String getText_info() 
	{
		return text_info;
	}

	public void setText_info(String text_info) 
	{
		this.text_info = text_info;
	}

	public String getFile_info() 
	{
		return file_info;
	}

	public void setFile_info(String file_info) 
	{
		this.file_info = file_info;
	}

	public boolean isIsanswered() 
	{
		return isanswered;
	}

	public void setIsanswered(boolean isanswered) 
	{
		this.isanswered = isanswered;
	}

	public int getRequestId() 
	{
		return requestId;
	}

	public int getSenderId() 
	{
		return senderId;
	}

	public int getRecipientId() 
	{
		return recipientId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + additionalInfoId;
		result = prime * result + ((file_info == null) ? 0 : file_info.hashCode());
		result = prime * result + (isanswered ? 1231 : 1237);
		result = prime * result + recipientId;
		result = prime * result + requestId;
		result = prime * result + senderId;
		result = prime * result + ((text_info == null) ? 0 : text_info.hashCode());
		result = prime * result + ((text_request == null) ? 0 : text_request.hashCode());
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
		AdditionalInfo other = (AdditionalInfo) obj;
		if (additionalInfoId != other.additionalInfoId)
			return false;
		if (file_info == null) 
		{
			if (other.file_info != null)
				return false;
		} 
		else if (!file_info.equals(other.file_info))
			return false;
		if (isanswered != other.isanswered)
			return false;
		if (recipientId != other.recipientId)
			return false;
		if (requestId != other.requestId)
			return false;
		if (senderId != other.senderId)
			return false;
		if (text_info == null) 
		{
			if (other.text_info != null)
				return false;
		} 
		else if (!text_info.equals(other.text_info))
			return false;
		if (text_request == null) 
		{
			if (other.text_request != null)
				return false;
		} 
		else if (!text_request.equals(other.text_request))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "AdditionalInfo [additionalInfoId=" + additionalInfoId + ", text_request=" + text_request
				+ ", text_info=" + text_info + ", file_info=" + file_info + ", isanswered=" + isanswered
				+ ", requestId=" + requestId + ", senderId=" + senderId + ", recipientId=" + recipientId + "]";
	}
}
