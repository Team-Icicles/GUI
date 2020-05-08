/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author webst
 */
@Entity
@Table(name="errors_fixed")
public class FixedErrors {
    private Integer id;
    private String precinctID;
    private String comment;
    private String errorType;
    private Timestamp commentTime;
    private String relevantInfo;
    
    public FixedErrors(){
        super();
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name="precinctid")
    public String getPrecinctID() {
        return precinctID;
    }

    @Column(name="comment")
    public String getComment() {
        return comment;
    }

    @Column(name="error_type")
    public String getErrorType() {
        return errorType;
    }

    @Column(name="comment_time")
    public Timestamp getCommentTime() {
        return commentTime;
    }

    @Lob
    @Column(name="relevant_info")
    public String getRelevantInfo() {
        return relevantInfo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrecinctID(String precinctID) {
        this.precinctID = precinctID;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    public void setRelevantInfo(String relevantInfo) {
        this.relevantInfo = relevantInfo;
    }
    
    
}