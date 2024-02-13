package yang.opencampus.opencampusback.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import yang.opencampus.opencampusback.entity.Comment;

public interface CommentRepository extends MongoRepository<Comment,String>{
    
}
