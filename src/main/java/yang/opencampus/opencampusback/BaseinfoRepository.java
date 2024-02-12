package yang.opencampus.opencampusback;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseinfoRepository extends MongoRepository<Baseinfo,String>{
    public Baseinfo getBaseinfoByTeacherID(int TeacherID);
}
