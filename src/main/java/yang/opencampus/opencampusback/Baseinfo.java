package yang.opencampus.opencampusback;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "baseinfo")
@Data
public class Baseinfo {
    private int teacherID;
    private String name;
    private String base;
    private ArrayList<String> teach;
    public Baseinfo(int teacherID, String name, String base, ArrayList<String> teach) {
        this.teacherID = teacherID;
        this.name = name;
        this.base = base;
        this.teach = teach;
    }
    public Baseinfo() {
    } 
    
}
