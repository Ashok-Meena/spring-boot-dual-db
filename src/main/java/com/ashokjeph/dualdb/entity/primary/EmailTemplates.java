package com.ashokjeph.dualdb.entity.primary;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmailTemplates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;

    @Column(nullable = false)
    String activity;
    String event;

    @Column(length = 500)
    String subject;

    @Lob
    String body;

    @Column(length = 5000)
    String adminEmailsTo;

    @Column(length = 5000)
    String adminEmailsCC;

    @Column(length = 20)
    String status;

    @CreationTimestamp
    Date creationTimestamp;

    @Transient
    String page;
    @Transient
    String size;
    @Transient
    String sort;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAdminEmailsTo() {
		return adminEmailsTo;
	}

	public void setAdminEmailsTo(String adminEmailsTo) {
		this.adminEmailsTo = adminEmailsTo;
	}

	public String getAdminEmailsCC() {
		return adminEmailsCC;
	}

	public void setAdminEmailsCC(String adminEmailsCC) {
		this.adminEmailsCC = adminEmailsCC;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public EmailTemplates( Map<String, Object> notificationEntry  )
    {
     activity=(String) notificationEntry.get("activity");
     event=(String) notificationEntry.get("event");
     subject=(String) notificationEntry.get("subject");
     body=(String) notificationEntry.get("body");
     status=(String) notificationEntry.get("status");
     creationTimestamp=Calendar.getInstance().getTime();
    }

   public void  updateJson(EmailTemplates notiTemplate)
    {
	if ( notiTemplate != null && notiTemplate.getId() > 0  && notiTemplate.getId()== this.getId())
        {
            this.activity = (String)(notiTemplate.getActivity() == null ? this.activity: notiTemplate.getActivity());
            this.event = (String)(notiTemplate.getEvent() == null ? this.event: notiTemplate.getEvent());
            this.subject = (String)(notiTemplate.getSubject() == null ? this.subject: notiTemplate.getSubject());
            this.body = (String)(notiTemplate.getBody() == null ? this.body: notiTemplate.getBody());
            this.status = (String)(notiTemplate.getStatus() == null ? this.status: notiTemplate.getStatus());
        }
    }
}
