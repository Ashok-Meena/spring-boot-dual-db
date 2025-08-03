package com.ashokjeph.dualdb.entity.primary;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Entity
@Data
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


    public EmailTemplates( Map<String, Object> notificationEntry  )
    {
     activity=(String) notificationEntry.get("activity");
     event=(String) notificationEntry.get("event");
     subject=(String) notificationEntry.get("subject");
     body=(String) notificationEntry.get("body");
     status=(String) notificationEntry.get("status");
     creationTimestamp=Calendar.getInstance().getTime();
    }

//   public void  updateJson(EmailTemplates notiTemplate)
//    {
//	if ( notiTemplate != null && notiTemplate.getId() > 0  && notiTemplate.getId()== this.getId())
//        {
//            this.activity = (String)(notiTemplate.getActivity() == null ? this.activity: notiTemplate.getActivity());
//            this.event = (String)(notiTemplate.getEvent() == null ? this.event: notiTemplate.getEvent());
//            this.subject = (String)(notiTemplate.getSubject() == null ? this.subject: notiTemplate.getSubject());
//            this.body = (String)(notiTemplate.getBody() == null ? this.body: notiTemplate.getBody());
//            this.status = (String)(notiTemplate.getStatus() == null ? this.status: notiTemplate.getStatus());
//        }
//    }
}
