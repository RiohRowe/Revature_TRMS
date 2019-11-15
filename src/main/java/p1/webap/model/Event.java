package p1.webap.model;

import java.sql.Date;
import java.util.Arrays;

public class Event 
{
	private int eventId;
	private String title;
	private Date startDate;
	private String sDate;
	private int lengthInDays;
	private String location;
	private int eventType;
	private int passingGrade;
	private float cost;
	
	public Event() 
	{
		super();
	}
	public Event(int eventId, String title, Date startDate, int lengthInDays, String location, int eventType,
			int passingGrade, float cost) 
	{
		super();
		this.eventId = eventId;
		this.title = title;
		this.startDate = startDate;
		sDate=startDate.toString();
		this.lengthInDays = lengthInDays;
		this.location = location;
		this.eventType = eventType;
		this.passingGrade = passingGrade;
		this.cost = cost;
	}
	public int getEventId() 
	{
		return eventId;
	}
	public void setEventId(int eventId) 
	{
		this.eventId = eventId;
	}
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public Date getStartDate() 
	{
		return startDate;
	}
	public void setStartDate(Date d) 
	{
		startDate = d;
		sDate = startDate.toString();
	}
	public int getLengthInDays() 
	{
		return lengthInDays;
	}
	public void setLengthInDays(int lengthInDays) 
	{
		this.lengthInDays = lengthInDays;
	}
	public String getLocation() 
	{
		return location;
	}
	public void setLocation(String location) 
	{
		this.location = location;
	}
	public int getEventType() 
	{
		return eventType;
	}
	public void setEventType(int eventType) 
	{
		this.eventType = eventType;
	}
	public int getPassingGrade() 
	{
		return passingGrade;
	}
	public void setPassingGrade(int passingGrade) 
	{
		this.passingGrade = passingGrade;
	}
	public float getCost() 
	{
		return cost;
	}
	public void setCost(float cost) 
	{
		this.cost = cost;
	}
	public String getsDate() 
	{
		return sDate;
	}
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(cost);
		result = prime * result + eventId;
		result = prime * result + eventType;
		result = prime * result + lengthInDays;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + passingGrade;
		result = prime * result + ((sDate == null) ? 0 : sDate.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Event other = (Event) obj;
		if (Float.floatToIntBits(cost) != Float.floatToIntBits(other.cost))
			return false;
		if (eventId != other.eventId)
			return false;
		if (eventType != other.eventType)
			return false;
		if (lengthInDays != other.lengthInDays)
			return false;
		if (location == null) 
		{
			if (other.location != null)
				return false;
		} 
		else if (!location.equals(other.location))
			return false;
		if (passingGrade != other.passingGrade)
			return false;
		if (sDate == null) 
		{
			if (other.sDate != null)
				return false;
		} 
		else if (!sDate.equals(other.sDate))
			return false;
		if (startDate == null) 
		{
			if (other.startDate != null)
				return false;
		} 
		else if (!startDate.equals(other.startDate))
			return false;
		if (title == null) 
		{
			if (other.title != null)
				return false;
		} 
		else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() 
	{
		return "Event [eventId=" + eventId + ", title=" + title + ", startDate=" + startDate + ", sDate=" + sDate
				+ ", lengthInDays=" + lengthInDays + ", location=" + location + ", eventType=" + eventType
				+ ", passingGrade=" + passingGrade + ", cost=" + cost + "]";
	}
	
	
}
