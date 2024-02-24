package yang.opencampus.opencampusback.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;

@Configuration
public class MongoConfig {
    
    public MongoConfig(MappingMongoConverter mappingMongoConverter) {
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null)); // 禁用类型映射
    }
}