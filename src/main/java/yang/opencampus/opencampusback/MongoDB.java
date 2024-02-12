package yang.opencampus.opencampusback;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


@Repository
public class MongoDB {
    @Autowired
    private BaseinfoRepository baseinfoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Baseinfo getBaseinfoByTeacherID(int id){
        return baseinfoRepository.getBaseinfoByTeacherID(id);
    }
    public List<Comment> findCommentsByAgreeNum(int teacherID,int n,int m){
        AggregationOperation match = Aggregation.match(Criteria.where("teacherID").is(teacherID));
        AggregationOperation sort = Aggregation.sort(Direction.DESC, "agreenum");
        AggregationOperation skip = Aggregation.skip(n - 1);
        AggregationOperation limit = Aggregation.limit(m - n + 1);

        Aggregation aggregation = Aggregation.newAggregation(match, sort, skip, limit);
        return mongoTemplate.aggregate(aggregation, "comment", Comment.class).getMappedResults();
    }
    public void updateComment(boolean isAgree,String commentID){
        Query query = new Query(Criteria.where("commentID").is(commentID));
        
        // 构建更新操作
        Update update = new Update();
        if (isAgree) {
            // 如果 isAgree 是 true，则对 agreenum 字段进行 +1 操作
            update.inc("agreenum", 1);
        } else {
            // 如果 isAgree 是 false，则对 agreenum 字段进行 -1 操作
            update.inc("agreenum", -1);
        }
        
        // 执行更新操作
        mongoTemplate.updateFirst(query, update, Comment.class);
    }
    
}
