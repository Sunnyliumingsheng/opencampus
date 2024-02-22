package yang.opencampus.opencampusback.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import yang.opencampus.opencampusback.entity.Baseinfo;
import yang.opencampus.opencampusback.entity.Comment;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


@Repository
public class MongoDBRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Comment> findCommentsByAgreeNum(int teacherID,int n,int m){
        System.out.println("I am try to find comments");
        AggregationOperation match = Aggregation.match(Criteria.where("teacherID").is(teacherID));
        AggregationOperation sort = Aggregation.sort(Direction.DESC, "agreenum");
        AggregationOperation skip = Aggregation.skip(n - 1);
        AggregationOperation limit = Aggregation.limit(m - n + 1);

        Aggregation aggregation = Aggregation.newAggregation(match, sort, skip, limit);
        return mongoTemplate.aggregate(aggregation, "comments", Comment.class).getMappedResults();
    }
    public void updateComment(boolean isAgree,String commentID){
        Query query = new Query(Criteria.where("_id").is(commentID));
        
        // 构建更新操作
        Update update = new Update();
        if (isAgree) {
            // 如果 isAgree 是 true，则对 agreenum 字段进行 +1 操作
            update.inc("agreenum", 1);
            System.out.println("I do it");
        } else {
            // 如果 isAgree 是 false，则对 agreenum 字段进行 -1 操作
            update.inc("agreenum", -1);
            System.out.println("I do it");
        }
        System.out.println(commentID);
        // 执行更新操作
        mongoTemplate.updateFirst(query, update, "comments");
    }
    public List<Baseinfo> findByNameContaining(String teacherName) {
        // 构建模糊查询条件，这里使用正则表达式来匹配包含关键字的文档
        Criteria criteria = Criteria.where("name").regex(teacherName);
        Query query = new Query(criteria);

        // 执行查询并返回结果
        List<Baseinfo> results = mongoTemplate.find(query, Baseinfo.class);
        return results;
    }
    public List<Baseinfo> findByNameContainingAndDept(String dept,String teacherName){
        Criteria criteria = new Criteria().andOperator(
        Criteria.where("dept").is(dept),
        Criteria.where("name").regex(teacherName)
        );
        Query query = new Query(criteria);
        // 执行查询并返回结果
        List<Baseinfo> results = mongoTemplate.find(query, Baseinfo.class);
        return results;
    }
    
}
