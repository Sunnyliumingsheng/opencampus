package yang.opencampus.opencampusback.entity;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Document(collection = "baseinfo")
@Data
public class Baseinfo {
    @Id
    private int teacherID;
    private String name;
    private String dept;
    private String base;
    private ArrayList<String> teach;
    public Baseinfo(int teacherID, String name,String dept, String base, ArrayList<String> teach) {
        this.teacherID = teacherID;
        this.name = name;
        this.base = base;
        this.teach = teach;
        this.dept=dept;
    }
    public Baseinfo() {
    } 
    
}
