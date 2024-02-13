package yang.opencampus.opencampusback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import yang.opencampus.opencampusback.entity.Baseinfo;

public interface BaseinfoRepository extends MongoRepository<Baseinfo,Integer>{
    public Baseinfo getBaseinfoByTeacherID(int TeacherID);
}
